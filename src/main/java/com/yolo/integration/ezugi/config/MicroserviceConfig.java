package com.yolo.integration.ezugi.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.poseitech.integration.commons.MicroServiceBean;
import com.poseitech.integration.commons.ObjectMapperFactory;
import com.poseitech.integration.commons.ResponseJsonMapper;

/**
 * 
 * @author ${user}
 * 
 *         ${tags}
 */
@Configuration
@ComponentScan({ "com.yolo.integration.ezugi.service" })
@Import({ MvcConfig.class, HttpConfig.class })
public class MicroserviceConfig {

   private static final Logger log = LoggerFactory.getLogger(MicroserviceConfig.class);

   @Value("${spring.application.name}")
   private String applicationName;

   @Value("${spring.profiles.active}")
   private String[] profiles;

   @Autowired
   private ApplicationContext applicationContext;

   @PostConstruct
   public void init() {
      log.debug("== Application Name: " + applicationName + ", Profiles: " + StringUtils.join(profiles, ", ") + " ==");
   }

   @Bean
   public MicroServiceBean microserviceBean() {
      return new MicroServiceBean(applicationContext);
   }

   /**
    * Jackson2 JSON mapper with Hibernate4 module.
    * 
    * @return
    */
   @Bean
   public ResponseJsonMapper jsonMapper() {
      return new ResponseJsonMapper(ObjectMapperFactory.getMapper());
   }

}
