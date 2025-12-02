package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Categoria;
import lombok.*;

/**
 * DTO para transferir datos de Categoria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activa;
    private Integer cantidadProductos;

    /**
     * Convierte una entidad Categoria a DTO.
     */
    public static CategoriaDTO fromEntity(Categoria categoria) {
        if (categoria == null) return null;
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .activa(categoria.getActiva())
                .cantidadProductos(categoria.getProductos() != null ? categoria.getProductos().size() : 0)
                .build();
    }
}

