package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Region.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByNombre(String nombre);

    List<Region> findByActivoTrue();

    boolean existsByNombre(String nombre);
}

