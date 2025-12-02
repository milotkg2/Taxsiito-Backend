package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.OrdenDTO;
import com.taxsiito.backend.model.*;
import com.taxsiito.backend.model.enums.EstadoOrden;
import com.taxsiito.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Órdenes.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    /**
     * Obtiene todas las órdenes.
     */
    @Transactional(readOnly = true)
    public List<OrdenDTO> obtenerTodas() {
        return ordenRepository.findAll().stream()
                .map(OrdenDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una orden por ID.
     */
    @Transactional(readOnly = true)
    public Optional<OrdenDTO> obtenerPorId(Long id) {
        return ordenRepository.findById(id)
                .map(OrdenDTO::fromEntity);
    }

    /**
     * Obtiene una orden por número de orden.
     */
    @Transactional(readOnly = true)
    public Optional<OrdenDTO> obtenerPorNumero(String numeroOrden) {
        return ordenRepository.findByNumeroOrden(numeroOrden)
                .map(OrdenDTO::fromEntity);
    }

    /**
     * Obtiene las órdenes de un usuario.
     */
    @Transactional(readOnly = true)
    public List<OrdenDTO> obtenerPorUsuario(Long usuarioId) {
        return ordenRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId).stream()
                .map(OrdenDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene órdenes por estado.
     */
    @Transactional(readOnly = true)
    public List<OrdenDTO> obtenerPorEstado(EstadoOrden estado) {
        return ordenRepository.findByEstadoOrderByFechaCreacionDesc(estado).stream()
                .map(OrdenDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva orden.
     */
    public OrdenDTO crear(Long usuarioId, List<ItemOrdenRequest> items, String direccionEnvio, 
                          String regionEnvio, String comunaEnvio, String notas) {
        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear orden
        Orden orden = Orden.builder()
                .usuario(usuario)
                .direccionEnvio(direccionEnvio)
                .regionEnvio(regionEnvio)
                .comunaEnvio(comunaEnvio)
                .notas(notas)
                .estado(EstadoOrden.PENDIENTE)
                .subtotal(BigDecimal.ZERO)
                .descuento(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();

        // Agregar items
        for (ItemOrdenRequest itemReq : items) {
            Producto producto = productoRepository.findById(itemReq.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemReq.getProductoId()));

            // Verificar stock
            if (producto.getStock() < itemReq.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Crear item
            ItemOrden item = ItemOrden.builder()
                    .producto(producto)
                    .nombreProducto(producto.getNombre())
                    .precioUnitario(producto.getPrecio())
                    .cantidad(itemReq.getCantidad())
                    .build();
            item.calcularSubtotal();

            orden.agregarItem(item);

            // Descontar stock
            producto.setStock(producto.getStock() - itemReq.getCantidad());
            productoRepository.save(producto);
        }

        // Calcular totales
        orden.calcularTotales();

        Orden guardada = ordenRepository.save(orden);
        return OrdenDTO.fromEntity(guardada);
    }

    /**
     * Actualiza el estado de una orden.
     */
    public OrdenDTO actualizarEstado(Long id, EstadoOrden nuevoEstado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Validar transición de estado
        validarTransicionEstado(orden.getEstado(), nuevoEstado);

        // Si se cancela, devolver stock
        if (nuevoEstado == EstadoOrden.CANCELADA && orden.getEstado() != EstadoOrden.CANCELADA) {
            for (ItemOrden item : orden.getItems()) {
                Producto producto = item.getProducto();
                if (producto != null) {
                    producto.setStock(producto.getStock() + item.getCantidad());
                    productoRepository.save(producto);
                }
            }
        }

        orden.setEstado(nuevoEstado);
        Orden guardada = ordenRepository.save(orden);
        return OrdenDTO.fromEntity(guardada);
    }

    /**
     * Valida que la transición de estado sea válida.
     */
    private void validarTransicionEstado(EstadoOrden actual, EstadoOrden nuevo) {
        // Una orden cancelada o entregada no puede cambiar
        if (actual == EstadoOrden.CANCELADA || actual == EstadoOrden.ENTREGADA) {
            throw new RuntimeException("No se puede modificar una orden " + actual.name().toLowerCase());
        }
    }

    /**
     * Obtiene las últimas órdenes.
     */
    @Transactional(readOnly = true)
    public List<OrdenDTO> obtenerUltimas() {
        return ordenRepository.findTop10ByOrderByFechaCreacionDesc().stream()
                .map(OrdenDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Cuenta órdenes por estado.
     */
    @Transactional(readOnly = true)
    public Long contarPorEstado(EstadoOrden estado) {
        return ordenRepository.countByEstado(estado);
    }

    /**
     * Suma ventas en un rango de fechas.
     */
    @Transactional(readOnly = true)
    public BigDecimal sumarVentas(LocalDateTime inicio, LocalDateTime fin) {
        return ordenRepository.sumarVentasPorEstadoYFecha(EstadoOrden.PAGADA, inicio, fin);
    }

    /**
     * Clase auxiliar para recibir items en la creación de orden.
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ItemOrdenRequest {
        private Long productoId;
        private Integer cantidad;
    }
}

