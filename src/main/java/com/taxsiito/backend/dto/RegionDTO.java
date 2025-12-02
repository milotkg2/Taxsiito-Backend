package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para transferencia de datos de Region.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private List<ComunaDTO> comunas;

    /**
     * Convierte una entidad Region a DTO (sin comunas).
     */
    public static RegionDTO fromEntity(Region region) {
        return RegionDTO.builder()
                .id(region.getId())
                .nombre(region.getNombre())
                .codigo(region.getCodigo())
                .build();
    }

    /**
     * Convierte una entidad Region a DTO (con comunas).
     */
    public static RegionDTO fromEntityWithComunas(Region region) {
        return RegionDTO.builder()
                .id(region.getId())
                .nombre(region.getNombre())
                .codigo(region.getCodigo())
                .comunas(region.getComunas().stream()
                        .map(ComunaDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}

