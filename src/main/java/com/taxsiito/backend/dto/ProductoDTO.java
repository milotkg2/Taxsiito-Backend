package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Producto;
import lombok.*;
import java.math.BigDecimal;

/**
 * DTO para transferir datos de Producto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockCritico;
    private String imagen;
    private Long categoriaId;
    private String categoriaNombre;
    private Boolean activo;
    private Boolean stockBajo;

    /**
     * Convierte una entidad Producto a DTO.
     */
    public static ProductoDTO fromEntity(Producto producto) {
        if (producto == null) return null;
        return ProductoDTO.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .stockCritico(producto.getStockCritico())
                .imagen(producto.getImagen())
                .categoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null)
                .categoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
                .activo(producto.getActivo())
                .stockBajo(producto.tieneStockBajo())
                .build();
    }
}

