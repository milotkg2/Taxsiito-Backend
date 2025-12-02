package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Orden;
import com.taxsiito.backend.model.enums.EstadoOrden;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para transferir datos de Orden.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDTO {
    private Long id;
    private String numeroOrden;
    private Long usuarioId;
    private String usuarioNombre;
    private String usuarioCorreo;
    private List<ItemOrdenDTO> items;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private EstadoOrden estado;
    private String direccionEnvio;
    private String regionEnvio;
    private String comunaEnvio;
    private String notas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Convierte una entidad Orden a DTO.
     */
    public static OrdenDTO fromEntity(Orden orden) {
        if (orden == null) return null;
        return OrdenDTO.builder()
                .id(orden.getId())
                .numeroOrden(orden.getNumeroOrden())
                .usuarioId(orden.getUsuario() != null ? orden.getUsuario().getId() : null)
                .usuarioNombre(orden.getUsuario() != null ? orden.getUsuario().getNombreCompleto() : null)
                .usuarioCorreo(orden.getUsuario() != null ? orden.getUsuario().getCorreo() : null)
                .items(orden.getItems() != null ? 
                       orden.getItems().stream().map(ItemOrdenDTO::fromEntity).collect(Collectors.toList()) : 
                       null)
                .subtotal(orden.getSubtotal())
                .descuento(orden.getDescuento())
                .total(orden.getTotal())
                .estado(orden.getEstado())
                .direccionEnvio(orden.getDireccionEnvio())
                .regionEnvio(orden.getRegionEnvio())
                .comunaEnvio(orden.getComunaEnvio())
                .notas(orden.getNotas())
                .fechaCreacion(orden.getFechaCreacion())
                .fechaActualizacion(orden.getFechaActualizacion())
                .build();
    }
}

