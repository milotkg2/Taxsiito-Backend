package com.taxsiito.backend.dto;

import com.taxsiito.backend.model.PreguntaFrecuente;
import lombok.*;

/**
 * DTO para transferir datos de PreguntaFrecuente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaFrecuenteDTO {
    private Long id;
    private String pregunta;
    private String respuesta;
    private String categoriaPregunta;
    private Integer ordenVisualizacion;
    private Boolean activa;

    /**
     * Convierte una entidad PreguntaFrecuente a DTO.
     */
    public static PreguntaFrecuenteDTO fromEntity(PreguntaFrecuente faq) {
        if (faq == null) return null;
        return PreguntaFrecuenteDTO.builder()
                .id(faq.getId())
                .pregunta(faq.getPregunta())
                .respuesta(faq.getRespuesta())
                .categoriaPregunta(faq.getCategoriaPregunta())
                .ordenVisualizacion(faq.getOrdenVisualizacion())
                .activa(faq.getActiva())
                .build();
    }
}

