package com.taxsiito.backend.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para la solicitud de login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Correo electrónico inválido")
    private String correo;

    @NotBlank(message = "La contraseña es requerida")
    private String password;
}

