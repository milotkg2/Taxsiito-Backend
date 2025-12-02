package com.taxsiito.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Producto: representa los productos de la tienda.
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es requerido")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal precio;

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @Min(value = 0, message = "El stock crítico no puede ser negativo")
    @Column(name = "stock_critico")
    @Builder.Default
    private Integer stockCritico = 5;

    @Size(max = 255, message = "La URL de imagen no puede superar 255 caracteres")
    @Column(length = 255)
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    /**
     * Verifica si el producto tiene stock bajo (por debajo del crítico).
     */
    public boolean tieneStockBajo() {
        return stock <= stockCritico;
    }

    /**
     * Verifica si hay stock disponible.
     */
    public boolean hayStock() {
        return stock > 0;
    }
}

