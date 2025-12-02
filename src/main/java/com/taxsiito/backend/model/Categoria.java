package com.taxsiito.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Categoria: agrupa productos por tipo.
 */
@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es requerido")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    @Column(length = 200)
    private String descripcion;

    @Column(name = "activa")
    @Builder.Default
    private Boolean activa = true;

    // Relación con productos
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Producto> productos = new ArrayList<>();
}

