package com.niklas.playground.hal.web;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.HypermediaWebClientConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

//@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@Configuration
public class HalWebClient {
    /*
    * Example of a simple HAL Web Client.
    *  Constants are defined in this class for simplicity.
    *  In addition, the Playground project is used for different purposes -> no need to define it in application.yml
    */
    private static final String BASE_URL = "http://localhost:8080";
    private static final String ACCEPT_HEADER = "application/hal+json";
    private static final String CONTENT_TYPE_HEADER = "application/hal+json";

    @Bean
    WebClientCustomizer hypermediaWebClientCustomizer(HypermediaWebClientConfigurer configurer) {
        return configurer::registerHypermediaTypes;
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", ACCEPT_HEADER)
                .defaultHeader("Content-Type", CONTENT_TYPE_HEADER)
                .build();
    }
}
