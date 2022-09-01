package com.sekarre.helpcenternotification.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Value("${allowed.header}")
    private String allowedHeader;

    @Value("${allowed.method}")
    private String allowedMethod;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader(allowedHeader);
        config.addAllowedMethod(allowedMethod);
        config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}