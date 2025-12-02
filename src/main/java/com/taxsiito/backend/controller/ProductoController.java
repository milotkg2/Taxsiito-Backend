package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.ProductoDTO;
import com.taxsiito.backend.model.Producto;
import com.taxsiito.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de productos.
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "CRUD de productos de la tienda")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Obtiene todos los productos activos.
     */
    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos activos")
    public ResponseEntity<List<ProductoDTO>> obtenerActivos() {
        return ResponseEntity.ok(productoService.obtenerActivos());
    }

    /**
     * Obtiene todos los productos (incluye inactivos).
     */
    @GetMapping("/todos")
    @Operation(summary = "Todos los productos", description = "Obtiene todos los productos")
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    /**
     * Obtiene un producto por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Obtiene un producto por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene un producto por código.
     */
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Producto por código", description = "Obtiene un producto por su código")
    public ResponseEntity<?> obtenerPorCodigo(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto.
     */
    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoRequest request) {
        try {
            Producto producto = Producto.builder()
                    .codigo(request.getCodigo())
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .precio(request.getPrecio())
                    .stock(request.getStock())
                    .stockCritico(request.getStockCritico())
                    .imagen(request.getImagen())
                    .build();

            ProductoDTO creado = productoService.crear(producto, request.getCategoriaId());
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualiza un producto.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoRequest request) {
        try {
            Producto producto = Producto.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .precio(request.getPrecio())
                    .stock(request.getStock())
                    .stockCritico(request.getStockCritico())
                    .imagen(request.getImagen())
                    .activo(request.getActivo())
                    .build();

            ProductoDTO actualizado = productoService.actualizar(id, producto, request.getCategoriaId());
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina un producto.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto permanentemente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Desactiva un producto (soft delete).
     */
    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar producto", description = "Desactiva un producto sin eliminarlo")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            ProductoDTO desactivado = productoService.desactivar(id);
            return ResponseEntity.ok(desactivado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene productos por categoría.
     */
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Productos por categoría", description = "Obtiene productos de una categoría")
    public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoriaId));
    }

    /**
     * Busca productos por nombre.
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos", description = "Busca productos por nombre")
    public ResponseEntity<List<ProductoDTO>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscar(nombre));
    }

    /**
     * Obtiene productos con stock bajo.
     */
    @GetMapping("/stock-bajo")
    @Operation(summary = "Stock bajo", description = "Obtiene productos con stock bajo")
    public ResponseEntity<List<ProductoDTO>> obtenerConStockBajo() {
        return ResponseEntity.ok(productoService.obtenerConStockBajo());
    }

    /**
     * Actualiza el stock de un producto.
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock", description = "Modifica el stock de un producto")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            ProductoDTO actualizado = productoService.actualizarStock(id, cantidad);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Clase interna para recibir datos del producto.
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ProductoRequest {
        private String codigo;
        private String nombre;
        private String descripcion;
        private java.math.BigDecimal precio;
        private Integer stock;
        private Integer stockCritico;
        private String imagen;
        private Long categoriaId;
        private Boolean activo;
    }
}
