package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.PreguntaFrecuenteDTO;
import com.taxsiito.backend.model.PreguntaFrecuente;
import com.taxsiito.backend.service.PreguntaFrecuenteService;
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
 * Controlador REST para preguntas frecuentes (ChatSIIto).
 */
@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
@Tag(name = "Preguntas Frecuentes", description = "FAQs para el ChatSIIto")
public class PreguntaFrecuenteController {

    private final PreguntaFrecuenteService preguntaService;

    /**
     * Obtiene todas las preguntas frecuentes activas.
     */
    @GetMapping
    @Operation(summary = "Listar FAQs", description = "Obtiene todas las preguntas frecuentes activas")
    public ResponseEntity<List<PreguntaFrecuenteDTO>> obtenerActivas() {
        return ResponseEntity.ok(preguntaService.obtenerActivas());
    }

    /**
     * Obtiene todas las preguntas (incluye inactivas).
     */
    @GetMapping("/todas")
    @Operation(summary = "Todas las FAQs", description = "Obtiene todas las preguntas")
    public ResponseEntity<List<PreguntaFrecuenteDTO>> obtenerTodas() {
        return ResponseEntity.ok(preguntaService.obtenerTodas());
    }

    /**
     * Obtiene una pregunta por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener FAQ", description = "Obtiene una pregunta por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return preguntaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene preguntas por categoría.
     */
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "FAQs por categoría", description = "Obtiene preguntas de una categoría")
    public ResponseEntity<List<PreguntaFrecuenteDTO>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(preguntaService.obtenerPorCategoria(categoria));
    }

    /**
     * Busca preguntas por texto.
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar FAQs", description = "Busca preguntas que contengan el texto")
    public ResponseEntity<List<PreguntaFrecuenteDTO>> buscar(@RequestParam String texto) {
        return ResponseEntity.ok(preguntaService.buscar(texto));
    }

    /**
     * Obtiene las categorías disponibles.
     */
    @GetMapping("/categorias")
    @Operation(summary = "Categorías de FAQs", description = "Obtiene las categorías de preguntas")
    public ResponseEntity<List<String>> obtenerCategorias() {
        return ResponseEntity.ok(preguntaService.obtenerCategorias());
    }

    /**
     * Crea una nueva pregunta frecuente.
     */
    @PostMapping
    @Operation(summary = "Crear FAQ", description = "Crea una nueva pregunta frecuente")
    public ResponseEntity<?> crear(@Valid @RequestBody PreguntaFrecuente pregunta) {
        try {
            PreguntaFrecuenteDTO creada = preguntaService.crear(pregunta);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualiza una pregunta frecuente.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar FAQ", description = "Actualiza una pregunta frecuente")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PreguntaFrecuente pregunta) {
        try {
            PreguntaFrecuenteDTO actualizada = preguntaService.actualizar(id, pregunta);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina una pregunta frecuente.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar FAQ", description = "Elimina una pregunta frecuente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            preguntaService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Pregunta eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
