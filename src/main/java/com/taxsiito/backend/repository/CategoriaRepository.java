package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Categoria.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca una categoría por nombre.
     */
    Optional<Categoria> findByNombre(String nombre);

    /**
     * Verifica si existe una categoría con el nombre dado.
     */
    boolean existsByNombre(String nombre);

    /**
     * Obtiene todas las categorías activas.
     */
    List<Categoria> findByActivaTrue();
}

