package com.taxsiito.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Entidad ItemOrden: representa un producto dentro de una orden.
 */
@Entity
@Table(name = "items_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Guardamos el nombre y precio al momento de la compra
    // por si el producto cambia después
    @NotBlank(message = "El nombre del producto es requerido")
    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;

    @NotNull(message = "El precio unitario es requerido")
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 0)
    private BigDecimal precioUnitario;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal subtotal;

    /**
     * Calcula el subtotal antes de persistir.
     */
    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (precioUnitario != null && cantidad != null) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }

    /**
     * Obtiene el subtotal calculado.
     */
    public BigDecimal getSubtotal() {
        if (subtotal == null && precioUnitario != null && cantidad != null) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return subtotal != null ? subtotal : BigDecimal.ZERO;
    }
}

