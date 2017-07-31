package com.yolo.integration.ezugi.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring MVC Config.
 * 
 * @author peter.hsu
 * 
 * @version 2017/01/10 peter.hsu V0. Initial revision.
 * 
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.yolo.integration.ezugi.controller" })
public class MvcConfig extends WebMvcConfigurerAdapter {

   private static Logger log = LoggerFactory.getLogger(MvcConfig.class);

   @Autowired
   private Environment env;

   @Value("${spring.application.name}")
   private String applicationName;

   @Value("${spring.profiles.active}")
   private String[] profiles;

   @PostConstruct
   public void init() {
      log.debug("***** Mvc Active Profiles : " + StringUtils.join(env.getActiveProfiles()) + " *****");
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(Include.NON_NULL);
      converter.setObjectMapper(mapper);
      converters.add(converter);
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      if (profiles != null) {
         for (String str : profiles) {
            if ("dev".equalsIgnoreCase(str)) {
               registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
               registry.addResourceHandler("/webjars/**")
                     .addResourceLocations("classpath:/META-INF/resources/webjars/");
               break;
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#
    * configureViewResolvers(org.springframework.web.servlet.config.annotation.
    * ViewResolverRegistry)
    */
   @Override
   public void configureViewResolvers(ViewResolverRegistry pRegistry) {
      pRegistry.jsp("/WEB-INF/views/", ".jsp");
   }
}
