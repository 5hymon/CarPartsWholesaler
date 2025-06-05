package com.wholesaler.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Zezwól wyłącznie na żądania z frontendu działającego pod http://localhost:4200
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        // Zezwól na żądania typu GET, POST, PUT, DELETE, OPTIONS
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Zezwól na wszystkie nagłówki (np. Content-Type, Authorization itp.)
        config.setAllowedHeaders(List.of("*"));
        // Jeśli używasz ciasteczek/autentykacji opartej na sesji, ustaw allowCredentials na true
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Zastosuj tę konfigurację do wszystkich endpointów
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}