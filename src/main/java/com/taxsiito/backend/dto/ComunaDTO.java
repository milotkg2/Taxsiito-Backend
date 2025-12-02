package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.Comuna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de Comuna.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunaDTO {

    private Long id;
    private String nombre;
    private Long regionId;
    private String regionNombre;

    /**
     * Convierte una entidad Comuna a DTO.
     */
    public static ComunaDTO fromEntity(Comuna comuna) {
        return ComunaDTO.builder()
                .id(comuna.getId())
                .nombre(comuna.getNombre())
                .regionId(comuna.getRegion() != null ? comuna.getRegion().getId() : null)
                .regionNombre(comuna.getRegion() != null ? comuna.getRegion().getNombre() : null)
                .build();
    }
}

