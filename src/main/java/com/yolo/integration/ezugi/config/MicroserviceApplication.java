/** */
package com.yolo.integration.ezugi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

//@EnableEurekaClient
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@Import({ MicroserviceConfig.class })
@EnableSwagger2
@RefreshScope
/**
 * 
 * @author ${user}
 * 
 *         ${tags}
 */
public class MicroserviceApplication {

   static final Logger log = LoggerFactory.getLogger(MicroserviceApplication.class);

   public static void main(String[] args) {
      SpringApplication.run(MicroserviceApplication.class, args);
   }

   @Bean
   public Docket newsApi() {
      return new Docket(DocumentationType.SWAGGER_2).select().paths(regex("/.*")).build();
   }
}
