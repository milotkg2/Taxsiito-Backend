package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.PreguntaFrecuenteDTO;
import com.taxsiito.backend.model.PreguntaFrecuente;
import com.taxsiito.backend.repository.PreguntaFrecuenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Preguntas Frecuentes (ChatSIIto).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PreguntaFrecuenteService {

    private final PreguntaFrecuenteRepository preguntaRepository;

    /**
     * Obtiene todas las preguntas frecuentes activas.
     */
    @Transactional(readOnly = true)
    public List<PreguntaFrecuenteDTO> obtenerActivas() {
        return preguntaRepository.findByActivaTrueOrderByOrdenVisualizacionAsc().stream()
                .map(PreguntaFrecuenteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las preguntas frecuentes.
     */
    @Transactional(readOnly = true)
    public List<PreguntaFrecuenteDTO> obtenerTodas() {
        return preguntaRepository.findAll().stream()
                .map(PreguntaFrecuenteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una pregunta por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PreguntaFrecuenteDTO> obtenerPorId(Long id) {
        return preguntaRepository.findById(id)
                .map(PreguntaFrecuenteDTO::fromEntity);
    }

    /**
     * Obtiene preguntas por categoría.
     */
    @Transactional(readOnly = true)
    public List<PreguntaFrecuenteDTO> obtenerPorCategoria(String categoria) {
        return preguntaRepository.findByCategoriaPreguntaAndActivaTrueOrderByOrdenVisualizacionAsc(categoria)
                .stream()
                .map(PreguntaFrecuenteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca preguntas que contengan el texto dado.
     */
    @Transactional(readOnly = true)
    public List<PreguntaFrecuenteDTO> buscar(String texto) {
        return preguntaRepository.findByPreguntaContainingIgnoreCaseAndActivaTrue(texto)
                .stream()
                .map(PreguntaFrecuenteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva pregunta frecuente.
     */
    public PreguntaFrecuenteDTO crear(PreguntaFrecuente pregunta) {
        PreguntaFrecuente guardada = preguntaRepository.save(pregunta);
        return PreguntaFrecuenteDTO.fromEntity(guardada);
    }

    /**
     * Actualiza una pregunta frecuente.
     */
    public PreguntaFrecuenteDTO actualizar(Long id, PreguntaFrecuente preguntaActualizada) {
        PreguntaFrecuente existente = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        if (preguntaActualizada.getPregunta() != null) {
            existente.setPregunta(preguntaActualizada.getPregunta());
        }
        if (preguntaActualizada.getRespuesta() != null) {
            existente.setRespuesta(preguntaActualizada.getRespuesta());
        }
        if (preguntaActualizada.getCategoriaPregunta() != null) {
            existente.setCategoriaPregunta(preguntaActualizada.getCategoriaPregunta());
        }
        if (preguntaActualizada.getOrdenVisualizacion() != null) {
            existente.setOrdenVisualizacion(preguntaActualizada.getOrdenVisualizacion());
        }
        if (preguntaActualizada.getActiva() != null) {
            existente.setActiva(preguntaActualizada.getActiva());
        }

        PreguntaFrecuente guardada = preguntaRepository.save(existente);
        return PreguntaFrecuenteDTO.fromEntity(guardada);
    }

    /**
     * Elimina una pregunta frecuente.
     */
    public void eliminar(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new RuntimeException("Pregunta no encontrada");
        }
        preguntaRepository.deleteById(id);
    }

    /**
     * Obtiene las categorías disponibles.
     */
    @Transactional(readOnly = true)
    public List<String> obtenerCategorias() {
        return preguntaRepository.findDistinctCategoriaPreguntaByActivaTrue();
    }
}

