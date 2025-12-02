package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.CategoriaDTO;
import com.taxsiito.backend.model.Categoria;
import com.taxsiito.backend.service.CategoriaService;
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
 * Controlador REST para gestión de categorías.
 */
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "CRUD de categorías de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Obtiene todas las categorías activas.
     */
    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías activas")
    public ResponseEntity<List<CategoriaDTO>> obtenerActivas() {
        return ResponseEntity.ok(categoriaService.obtenerActivas());
    }

    /**
     * Obtiene todas las categorías (incluye inactivas).
     */
    @GetMapping("/todas")
    @Operation(summary = "Todas las categorías", description = "Obtiene todas las categorías")
    public ResponseEntity<List<CategoriaDTO>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    /**
     * Obtiene una categoría por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría", description = "Obtiene una categoría por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva categoría.
     */
    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría")
    public ResponseEntity<?> crear(@Valid @RequestBody Categoria categoria) {
        try {
            CategoriaDTO creada = categoriaService.crear(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualiza una categoría.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            CategoriaDTO actualizada = categoriaService.actualizar(id, categoria);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina una categoría.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Categoría eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
