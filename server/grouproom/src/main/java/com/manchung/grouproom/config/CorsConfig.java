package com.manchung.grouproom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);  // ✅ 인증 정보 포함 허용
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addAllowedOriginPattern("*");  // ✅ allowedOriginPatterns 사용
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
