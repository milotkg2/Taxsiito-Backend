package com.taxsiito.backend.model;

import com.taxsiito.backend.model.enums.EstadoOrden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Orden: representa una orden de compra.
 */
@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_orden", nullable = false, unique = true, length = 20)
    private String numeroOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemOrden> items = new ArrayList<>();

    @NotNull(message = "El subtotal es requerido")
    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 0)
    @Builder.Default
    private BigDecimal descuento = BigDecimal.ZERO;

    @NotNull(message = "El total es requerido")
    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    // Datos de envío
    @Size(max = 200, message = "La dirección de envío no puede superar 200 caracteres")
    @Column(name = "direccion_envio", length = 200)
    private String direccionEnvio;

    @Size(max = 50, message = "La región no puede superar 50 caracteres")
    @Column(name = "region_envio", length = 50)
    private String regionEnvio;

    @Size(max = 50, message = "La comuna no puede superar 50 caracteres")
    @Column(name = "comuna_envio", length = 50)
    private String comunaEnvio;

    @Size(max = 500, message = "Las notas no pueden superar 500 caracteres")
    @Column(length = 500)
    private String notas;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Genera un número de orden único.
     */
    @PrePersist
    public void generarNumeroOrden() {
        if (numeroOrden == null) {
            this.numeroOrden = "ORD-" + System.currentTimeMillis();
        }
        if (fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Agrega un item a la orden.
     */
    public void agregarItem(ItemOrden item) {
        items.add(item);
        item.setOrden(this);
    }

    /**
     * Calcula el total de la orden sumando los items.
     */
    public void calcularTotales() {
        this.subtotal = items.stream()
                .map(ItemOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal.subtract(this.descuento != null ? this.descuento : BigDecimal.ZERO);
    }
}

