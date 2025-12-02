package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.ItemOrden;
import lombok.*;
import java.math.BigDecimal;

/**
 * DTO para transferir datos de ItemOrden.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrdenDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;
    private String imagenProducto;

    /**
     * Convierte una entidad ItemOrden a DTO.
     */
    public static ItemOrdenDTO fromEntity(ItemOrden item) {
        if (item == null) return null;
        return ItemOrdenDTO.builder()
                .id(item.getId())
                .productoId(item.getProducto() != null ? item.getProducto().getId() : null)
                .nombreProducto(item.getNombreProducto())
                .precioUnitario(item.getPrecioUnitario())
                .cantidad(item.getCantidad())
                .subtotal(item.getSubtotal())
                .imagenProducto(item.getProducto() != null ? item.getProducto().getImagen() : null)
                .build();
    }
}

