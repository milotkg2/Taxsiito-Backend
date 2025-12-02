package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.PreguntaFrecuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de PreguntaFrecuente.
 */
@Repository
public interface PreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuente, Long> {

    /**
     * Obtiene todas las preguntas activas ordenadas.
     */
    List<PreguntaFrecuente> findByActivaTrueOrderByOrdenVisualizacionAsc();

    /**
     * Obtiene preguntas por categoría.
     */
    List<PreguntaFrecuente> findByCategoriaPreguntaAndActivaTrueOrderByOrdenVisualizacionAsc(String categoria);

    /**
     * Busca preguntas que contengan el texto dado.
     */
    List<PreguntaFrecuente> findByPreguntaContainingIgnoreCaseAndActivaTrue(String texto);

    /**
     * Obtiene las categorías de preguntas disponibles.
     */
    List<String> findDistinctCategoriaPreguntaByActivaTrue();
}

