package com.caito.waletservice.configs.restemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for RestTemplate bean.
 * This class provides a RestTemplate instance for making HTTP requests.
 *
 * @author caito
 *
 */
@Configuration
public class RestemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
