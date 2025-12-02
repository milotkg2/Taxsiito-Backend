package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Usuario;
import com.taxsiito.backend.model.enums.Rol;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO para transferir datos de Usuario sin exponer la contrasena.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String run;
    private String nombres;
    private String apellidos;
    private String correo;
    private Rol rol;
    private String direccion;
    
    // Datos de Region
    private Long regionId;
    private String regionNombre;
    
    // Datos de Comuna
    private Long comunaId;
    private String comunaNombre;
    
    private String telefono;
    private LocalDateTime fechaRegistro;
    private Boolean activo;

    /**
     * Convierte una entidad Usuario a DTO.
     */
    public static UsuarioDTO fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .run(usuario.getRun())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .direccion(usuario.getDireccion())
                .regionId(usuario.getRegion() != null ? usuario.getRegion().getId() : null)
                .regionNombre(usuario.getRegion() != null ? usuario.getRegion().getNombre() : null)
                .comunaId(usuario.getComuna() != null ? usuario.getComuna().getId() : null)
                .comunaNombre(usuario.getComuna() != null ? usuario.getComuna().getNombre() : null)
                .telefono(usuario.getTelefono())
                .fechaRegistro(usuario.getFechaRegistro())
                .activo(usuario.getActivo())
                .build();
    }
}
