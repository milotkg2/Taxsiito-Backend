package com.taxsiito.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API.
 * 
 * Acceso a Swagger UI: http://localhost:8080/swagger-ui.html
 * Acceso a OpenAPI JSON: http://localhost:8080/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TaxSIIto API")
                        .version("1.0.0")
                        .description("API REST para TaxSIIto - Sistema de asistencia tributaria con tienda online.\n\n" +
                                "Esta API proporciona endpoints para:\n" +
                                "- Autenticación y registro de usuarios\n" +
                                "- Gestión de productos y categorías\n" +
                                "- Preguntas frecuentes para el ChatSIIto\n" +
                                "- Gestión de órdenes de compra\n" +
                                "- Ubicaciones (regiones y comunas de Chile)\n\n" +
                                "**Nota**: Actualmente la API no implementa autenticación JWT. " +
                                "Todos los endpoints están disponibles sin autenticación.")
                        .contact(new Contact()
                                .name("TaxSIIto Team")
                                .email("taxsiito@taxsiito.cl")
                                .url("https://taxsiito.cl"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.taxsiito.cl")
                                .description("Servidor de producción")
                ));
    }
}
