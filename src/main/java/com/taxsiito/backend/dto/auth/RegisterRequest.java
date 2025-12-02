package com.taxsiito.backend.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para la solicitud de registro.
 * Acepta region/comuna por ID o por nombre.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Pattern(regexp = "^[0-9]{7,8}[0-9Kk]$", message = "RUN invalido")
    private String run;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido es requerido")
    @Size(max = 50, message = "El apellido no puede superar 50 caracteres")
    private String apellidos;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Correo electronico invalido")
    @Size(max = 100, message = "El correo no puede superar 100 caracteres")
    private String correo;

    @NotBlank(message = "La contrasena es requerida")
    @Size(min = 4, max = 10, message = "La contrasena debe tener entre 4 y 10 caracteres")
    private String password;

    @Size(max = 200, message = "La direccion no puede superar 200 caracteres")
    private String direccion;

    // Region - puede ser ID o nombre
    private Long regionId;
    private String region; // nombre de la region (para compatibilidad)

    // Comuna - puede ser ID o nombre
    private Long comunaId;
    private String comuna; // nombre de la comuna (para compatibilidad)

    @Size(max = 20, message = "El telefono no puede superar 20 caracteres")
    private String telefono;
}
