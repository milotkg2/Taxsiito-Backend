package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.ComunaDTO;
import com.taxsiito.backend.dto.RegionDTO;
import com.taxsiito.backend.model.Region;
import com.taxsiito.backend.repository.ComunaRepository;
import com.taxsiito.backend.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones de Regiones y Comunas.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UbicacionService {

    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;

    /**
     * Obtiene todas las regiones activas.
     */
    public List<RegionDTO> obtenerRegiones() {
        return regionRepository.findByActivoTrue().stream()
                .map(RegionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las regiones con sus comunas.
     */
    public List<RegionDTO> obtenerRegionesConComunas() {
        return regionRepository.findByActivoTrue().stream()
                .map(RegionDTO::fromEntityWithComunas)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una region por ID.
     */
    public RegionDTO obtenerRegionPorId(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada"));
        return RegionDTO.fromEntityWithComunas(region);
    }

    /**
     * Obtiene las comunas de una region.
     */
    public List<ComunaDTO> obtenerComunasPorRegion(Long regionId) {
        return comunaRepository.findByRegionIdAndActivoTrue(regionId).stream()
                .map(ComunaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las comunas.
     */
    public List<ComunaDTO> obtenerTodasLasComunas() {
        return comunaRepository.findByActivoTrue().stream()
                .map(ComunaDTO::fromEntity)
                .collect(Collectors.toList());
    }
}

