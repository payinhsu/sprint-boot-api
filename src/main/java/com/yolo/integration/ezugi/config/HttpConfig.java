package com.yolo.integration.ezugi.config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * @author peter.hsu
 * @version 2017/01/16 peter.hsu V0. Initial revision.
 */
@Configuration
public class HttpConfig {

   @Value("${clientHttp.maxTotalConnections}")
   private int maxTotalConnections;
   @Value("${clientHttp.maxConnectionsPerRoute}")
   private int maxConnectionsPerRoute;
   @Value("${clientHttp.connectTimeout}")
   private int connectTimeout;
   @Value("${clientHttp.connectionRequestTimeout}")
   private int connectionRequestTimeout;
   @Value("${clientHttp.socketTimeout}")
   private int socketTimeout;
   @Value("${clientHttp.maxPerRoute}")
   private int maxPerRoute;
   @Value("${clientHttp.httpRequestRetryTimes}")
   private int httpRequestRetryTimes;
   @Value("${clientHttp.hostName}")
   private String hostName;

   @Inject
   private ObjectMapper objectMapper;

   // ################################################### SYNC
   @Bean
   public ClientHttpRequestFactory httpRequestFactory() {
      return new HttpComponentsClientHttpRequestFactory(httpClient());
   }

   @Bean
   public RestTemplate restTemplate() {
      RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
      List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
      for (HttpMessageConverter<?> converter : converters) {
         if (converter instanceof MappingJackson2HttpMessageConverter) {
            MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
            jsonConverter.setObjectMapper(objectMapper);
            jsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));
         }
      }
      return restTemplate;
   }

   @Bean
   public CloseableHttpClient httpClient() {
      PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
      // 設定連線池最大連接數
      connectionManager.setMaxTotal(maxTotalConnections);
      // 設定路由的最大連接數
      connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
      // 設定目標主機最大連接數
      connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(hostName)), maxPerRoute);

      RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout) // 連接不夠用的等待時間
            .setSocketTimeout(socketTimeout) // 設定讀取數據超時時間
            .setConnectTimeout(connectTimeout) // 設定連接超時時間
            .build();

      // 設定保持連線秒數(keep-alive能提升性能，減少連接次数), override getKeepAliveDuration method
      ConnectionKeepAliveStrategy myStrategy = (HttpResponse response, HttpContext context) -> {
         // Honor 'keep-alive' header
         HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
         while (it.hasNext()) {
            HeaderElement he = it.nextElement();
            String param = he.getName();
            String value = he.getValue();
            if (value != null && param.equalsIgnoreCase("timeout")) {
               return Long.parseLong(value) * 1000;
            }
         }
         // Keep alive for 5 seconds only
         return 5 * 1000;
      };

      CloseableHttpClient defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
            .setDefaultRequestConfig(requestConfig)
            .setRetryHandler(new DefaultHttpRequestRetryHandler(httpRequestRetryTimes, true))
            .setKeepAliveStrategy(myStrategy).build();
      return defaultHttpClient;
   }

   // ################################################### ASYNC
   @Bean
   public AsyncClientHttpRequestFactory asyncHttpRequestFactory() {
      return new HttpComponentsAsyncClientHttpRequestFactory(asyncHttpClient());
   }

   @Bean
   public AsyncRestTemplate asyncRestTemplate() {
      AsyncRestTemplate restTemplate = new AsyncRestTemplate(asyncHttpRequestFactory(), restTemplate());
      return restTemplate;
   }

   @Bean
   public CloseableHttpAsyncClient asyncHttpClient() {
      try {
         PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
               new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
         // 設定連線池最大連接數
         connectionManager.setMaxTotal(maxTotalConnections);
         // 設定路由的最大連接數
         connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
         // 設定目標主機最大連接數
         connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(hostName)), maxPerRoute);
         RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout) // 連接不夠用的等待時間
               .setSocketTimeout(socketTimeout) // 設定讀取數據超時時間
               .setConnectTimeout(connectTimeout) // 設定連接超時時間
               .build();

         // 設定保持連線秒數(keep-alive能提升性能，減少連接次数), override getKeepAliveDuration
         // method
         ConnectionKeepAliveStrategy myStrategy = (HttpResponse response, HttpContext context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
               HeaderElement he = it.nextElement();
               String param = he.getName();
               String value = he.getValue();
               if (value != null && param.equalsIgnoreCase("timeout")) {
                  return Long.parseLong(value) * 1000;
               }
            }
            // Keep alive for 5 seconds only
            return 5 * 1000;
         };

         CloseableHttpAsyncClient httpclient = HttpAsyncClientBuilder.create().setConnectionManager(connectionManager)
               .setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(myStrategy).build();
         return httpclient;
      } catch (Exception e) {
         throw Throwables.propagate(e);
      }
   }
}
