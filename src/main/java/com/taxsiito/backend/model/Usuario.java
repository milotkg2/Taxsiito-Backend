package com.taxsiito.backend.model;

import com.taxsiito.backend.model.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Usuario: representa a los usuarios del sistema.
 * Incluye clientes, vendedores y administradores.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 12)
    @Pattern(regexp = "^[0-9]{7,8}[0-9Kk]$", message = "RUN invalido")
    private String run;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombres;

    @NotBlank(message = "El apellido es requerido")
    @Size(max = 50, message = "El apellido no puede superar 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellidos;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Correo electronico invalido")
    @Size(max = 100, message = "El correo no puede superar 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @NotBlank(message = "La contrasena es requerida")
    @Size(min = 4, message = "La contrasena debe tener al menos 4 caracteres")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Rol rol = Rol.CLIENTE;

    @Size(max = 200, message = "La direccion no puede superar 200 caracteres")
    @Column(length = 200)
    private String direccion;

    // Relacion con Region
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    // Relacion con Comuna
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    @Column(length = 20)
    private String telefono;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    // Relacion con ordenes
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Orden> ordenes = new ArrayList<>();

    /**
     * Devuelve el nombre completo del usuario.
     */
    public String getNombreCompleto() {
        return (nombres + " " + apellidos).trim();
    }

    /**
     * Obtiene el nombre de la region.
     */
    public String getRegionNombre() {
        return region != null ? region.getNombre() : null;
    }

    /**
     * Obtiene el nombre de la comuna.
     */
    public String getComunaNombre() {
        return comuna != null ? comuna.getNombre() : null;
    }
}
