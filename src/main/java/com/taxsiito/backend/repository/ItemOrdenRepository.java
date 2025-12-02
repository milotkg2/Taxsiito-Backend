package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.ItemOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de ItemOrden.
 */
@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

    /**
     * Obtiene todos los items de una orden.
     */
    List<ItemOrden> findByOrdenId(Long ordenId);

    /**
     * Obtiene items que contienen un producto específico.
     */
    List<ItemOrden> findByProductoId(Long productoId);

    /**
     * Cuenta cuántas veces se ha vendido un producto.
     */
    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM ItemOrden i WHERE i.producto.id = :productoId")
    Long contarVentasPorProducto(@Param("productoId") Long productoId);
}

