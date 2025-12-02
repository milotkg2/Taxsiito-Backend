package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Comuna;
import com.taxsiito.backend.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Comuna.
 */
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

    List<Comuna> findByRegion(Region region);

    List<Comuna> findByRegionId(Long regionId);

    List<Comuna> findByRegionIdAndActivoTrue(Long regionId);

    Optional<Comuna> findByNombreAndRegion(String nombre, Region region);

    List<Comuna> findByActivoTrue();
}

