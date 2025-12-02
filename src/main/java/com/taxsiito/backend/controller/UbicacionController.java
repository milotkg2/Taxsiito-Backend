package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.ComunaDTO;
import com.taxsiito.backend.dto.RegionDTO;
import com.taxsiito.backend.service.UbicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Regiones y Comunas.
 */
@RestController
@RequestMapping("/api/ubicacion")
@RequiredArgsConstructor
@Tag(name = "Ubicacion", description = "Endpoints para regiones y comunas de Chile")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    /**
     * Obtiene todas las regiones.
     */
    @GetMapping("/regiones")
    @Operation(summary = "Listar regiones", description = "Obtiene todas las regiones de Chile")
    public ResponseEntity<List<RegionDTO>> obtenerRegiones() {
        return ResponseEntity.ok(ubicacionService.obtenerRegiones());
    }

    /**
     * Obtiene todas las regiones con sus comunas.
     */
    @GetMapping("/regiones/completas")
    @Operation(summary = "Listar regiones con comunas", description = "Obtiene todas las regiones con sus comunas")
    public ResponseEntity<List<RegionDTO>> obtenerRegionesConComunas() {
        return ResponseEntity.ok(ubicacionService.obtenerRegionesConComunas());
    }

    /**
     * Obtiene una region por ID con sus comunas.
     */
    @GetMapping("/regiones/{id}")
    @Operation(summary = "Obtener region", description = "Obtiene una region por ID con sus comunas")
    public ResponseEntity<RegionDTO> obtenerRegion(@PathVariable Long id) {
        return ResponseEntity.ok(ubicacionService.obtenerRegionPorId(id));
    }

    /**
     * Obtiene las comunas de una region.
     */
    @GetMapping("/regiones/{regionId}/comunas")
    @Operation(summary = "Listar comunas por region", description = "Obtiene las comunas de una region especifica")
    public ResponseEntity<List<ComunaDTO>> obtenerComunasPorRegion(@PathVariable Long regionId) {
        return ResponseEntity.ok(ubicacionService.obtenerComunasPorRegion(regionId));
    }

    /**
     * Obtiene todas las comunas.
     */
    @GetMapping("/comunas")
    @Operation(summary = "Listar todas las comunas", description = "Obtiene todas las comunas de Chile")
    public ResponseEntity<List<ComunaDTO>> obtenerComunas() {
        return ResponseEntity.ok(ubicacionService.obtenerTodasLasComunas());
    }
}

