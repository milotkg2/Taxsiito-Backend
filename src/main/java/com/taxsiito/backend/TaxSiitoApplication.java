package com.taxsiito.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación TaxSIIto Backend.
 * Esta clase inicia el servidor Spring Boot.
 */
@SpringBootApplication
public class TaxSiitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxSiitoApplication.class, args);
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║ TaxSIIto Backend iniciado correctamente           ║");
        System.out.println("║                                                   ║");
        System.out.println("║ API:     http://localhost:8080/api                ║");
        System.out.println("║ Swagger: http://localhost:8080/swagger-ui.html    ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }
}

