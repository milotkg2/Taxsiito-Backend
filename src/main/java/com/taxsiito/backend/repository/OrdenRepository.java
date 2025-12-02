package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Orden;
import com.taxsiito.backend.model.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Orden.
 */
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    /**
     * Busca una orden por número de orden.
     */
    Optional<Orden> findByNumeroOrden(String numeroOrden);

    /**
     * Obtiene todas las órdenes de un usuario.
     */
    List<Orden> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);

    /**
     * Obtiene órdenes por estado.
     */
    List<Orden> findByEstadoOrderByFechaCreacionDesc(EstadoOrden estado);

    /**
     * Obtiene órdenes por usuario y estado.
     */
    List<Orden> findByUsuarioIdAndEstado(Long usuarioId, EstadoOrden estado);

    /**
     * Obtiene órdenes creadas entre dos fechas.
     */
    List<Orden> findByFechaCreacionBetweenOrderByFechaCreacionDesc(LocalDateTime inicio, LocalDateTime fin);

    /**
     * Cuenta órdenes por estado.
     */
    Long countByEstado(EstadoOrden estado);

    /**
     * Suma el total de ventas en un rango de fechas.
     */
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Orden o WHERE o.estado = :estado AND o.fechaCreacion BETWEEN :inicio AND :fin")
    java.math.BigDecimal sumarVentasPorEstadoYFecha(@Param("estado") EstadoOrden estado, 
                                                     @Param("inicio") LocalDateTime inicio, 
                                                     @Param("fin") LocalDateTime fin);

    /**
     * Obtiene las últimas N órdenes.
     */
    List<Orden> findTop10ByOrderByFechaCreacionDesc();
}

