package com.wholesaler.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) Włącz obsługę CORS, przekazując nasz CorsConfigurationSource
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // 2) Wyłącz CSRF (REST API zwykle nie potrzebuje CSRF, zwłaszcza przy tokenach)
                .csrf(csrf -> csrf.disable())

                // 3) Autoryzacja – zezwól (permitAll) na Swagger i publiczne endpointy:
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cars/**").permitAll()
                        // specyfikacja OpenAPI w formacie JSON (lub YAML)
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        // route do Swagger UI (statyczne zasoby)
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                        // ewentualnie inne publiczne API endpointy:
                        .requestMatchers("/cars/all").permitAll()
                        // wszystkie inne endpointy wymagają zalogowania:
                        .anyRequest().authenticated()
                )

                // 4) Włącz HTTP Basic (lub możesz tu podstawić inny Customizer dla JWT itd.)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

