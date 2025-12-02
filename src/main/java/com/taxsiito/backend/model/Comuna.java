package com.taxsiito.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad que representa una Comuna de Chile.
 */
@Entity
@Table(name = "comunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonIgnore
    private Region region;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}

