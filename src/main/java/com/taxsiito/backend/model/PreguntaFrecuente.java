package com.taxsiito.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad PreguntaFrecuente: preguntas y respuestas para el ChatSIIto.
 */
@Entity
@Table(name = "preguntas_frecuentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaFrecuente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La pregunta es requerida")
    @Size(max = 300, message = "La pregunta no puede superar 300 caracteres")
    @Column(nullable = false, length = 300)
    private String pregunta;

    @NotBlank(message = "La respuesta es requerida")
    @Size(max = 2000, message = "La respuesta no puede superar 2000 caracteres")
    @Column(nullable = false, length = 2000)
    private String respuesta;

    @Size(max = 50, message = "La categoría no puede superar 50 caracteres")
    @Column(length = 50)
    private String categoriaPregunta;

    @Column(name = "orden_visualizacion")
    @Builder.Default
    private Integer ordenVisualizacion = 0;

    @Column(name = "activa")
    @Builder.Default
    private Boolean activa = true;
}

