package com.example.ebookstorebackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


// HINT: 由于SameSite属性的影响(不是https开不了None),这里真跨域默认只有lax模式, 此模式下只有get能跨域
// HINT: 故前端本地调试使用localhost而不是127.0.0.1(会被认为是跨域)
@Configuration
public class Config {
    // HINT: allow empty beans(serialization feature), gen {}
    // HINT: simplify lazy loading
    public static class JacksonConfig {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper;
        }
    }

    @Configuration
    public static class GlobalCorsConfig {
        @Bean
        public CorsFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            // 设置你要允许的网站域名
            config.addAllowedOrigin("http://localhost:5173"); // vite
            //允许跨域发送cookie
            config.setAllowCredentials(true);
            //放行全部原始头信息
            config.addAllowedHeader("*");
            //允许所有请求方法跨域调用
            config.addAllowedMethod("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
    }
}


