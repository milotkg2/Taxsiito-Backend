package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.OrdenDTO;
import com.taxsiito.backend.model.enums.EstadoOrden;
import com.taxsiito.backend.service.OrdenService;
import com.taxsiito.backend.service.OrdenService.ItemOrdenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de órdenes.
 */
@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@Tag(name = "Órdenes", description = "Gestión de pedidos y órdenes de compra")
public class OrdenController {

    private final OrdenService ordenService;

    /**
     * Obtiene todas las órdenes.
     */
    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Obtiene todas las órdenes")
    public ResponseEntity<List<OrdenDTO>> obtenerTodas() {
        return ResponseEntity.ok(ordenService.obtenerTodas());
    }

    /**
     * Obtiene una orden por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden", description = "Obtiene una orden por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return ordenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene una orden por número de orden.
     */
    @GetMapping("/numero/{numeroOrden}")
    @Operation(summary = "Orden por número", description = "Obtiene una orden por su número")
    public ResponseEntity<?> obtenerPorNumero(@PathVariable String numeroOrden) {
        return ordenService.obtenerPorNumero(numeroOrden)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene las órdenes de un usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Órdenes por usuario", description = "Obtiene las órdenes de un usuario")
    public ResponseEntity<List<OrdenDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuario(usuarioId));
    }

    /**
     * Obtiene órdenes por estado.
     */
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Órdenes por estado", description = "Obtiene órdenes filtradas por estado")
    public ResponseEntity<List<OrdenDTO>> obtenerPorEstado(@PathVariable String estado) {
        try {
            EstadoOrden estadoEnum = EstadoOrden.valueOf(estado.toUpperCase());
            return ResponseEntity.ok(ordenService.obtenerPorEstado(estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Crea una nueva orden.
     */
    @PostMapping
    @Operation(summary = "Crear orden", description = "Crea una nueva orden de compra")
    public ResponseEntity<?> crear(@RequestBody CrearOrdenRequest request) {
        try {
            OrdenDTO creada = ordenService.crear(
                    request.getUsuarioId(),
                    request.getItems(),
                    request.getDireccionEnvio(),
                    request.getRegionEnvio(),
                    request.getComunaEnvio(),
                    request.getNotas()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualiza el estado de una orden.
     */
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado", description = "Cambia el estado de una orden")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            EstadoOrden estadoEnum = EstadoOrden.valueOf(estado.toUpperCase());
            OrdenDTO actualizada = ordenService.actualizarEstado(id, estadoEnum);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado inválido"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene las últimas órdenes.
     */
    @GetMapping("/ultimas")
    @Operation(summary = "Últimas órdenes", description = "Obtiene las 10 últimas órdenes")
    public ResponseEntity<List<OrdenDTO>> obtenerUltimas() {
        return ResponseEntity.ok(ordenService.obtenerUltimas());
    }

    /**
     * Estadísticas de órdenes.
     */
    @GetMapping("/estadisticas")
    @Operation(summary = "Estadísticas", description = "Obtiene estadísticas de órdenes")
    public ResponseEntity<?> obtenerEstadisticas() {
        Map<String, Long> estadisticas = Map.of(
                "pendientes", ordenService.contarPorEstado(EstadoOrden.PENDIENTE),
                "pagadas", ordenService.contarPorEstado(EstadoOrden.PAGADA),
                "enPreparacion", ordenService.contarPorEstado(EstadoOrden.EN_PREPARACION),
                "enviadas", ordenService.contarPorEstado(EstadoOrden.ENVIADA),
                "entregadas", ordenService.contarPorEstado(EstadoOrden.ENTREGADA),
                "canceladas", ordenService.contarPorEstado(EstadoOrden.CANCELADA)
        );
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Clase interna para recibir datos de creación de orden.
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CrearOrdenRequest {
        private Long usuarioId;
        private List<ItemOrdenRequest> items;
        private String direccionEnvio;
        private String regionEnvio;
        private String comunaEnvio;
        private String notas;
    }
}
