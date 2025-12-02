package com.taxsiito.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una Region de Chile.
 */
@Entity
@Table(name = "regiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 10)
    private String codigo;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comuna> comunas = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}

