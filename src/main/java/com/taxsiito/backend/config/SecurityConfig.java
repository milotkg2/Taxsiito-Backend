package com.taxsiito.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para permitir peticiones del frontend.
 * Sin Spring Security - API completamente abierta.
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * Configuración de CORS para permitir peticiones del frontend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",    // Vite dev server
                        "http://localhost:3000",    // Alternativo
                        "http://127.0.0.1:5173"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Content-Type");
    }
}
