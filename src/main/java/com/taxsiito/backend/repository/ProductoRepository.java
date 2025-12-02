package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Producto.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca un producto por código.
     */
    Optional<Producto> findByCodigo(String codigo);

    /**
     * Verifica si existe un producto con el código dado.
     */
    boolean existsByCodigo(String codigo);

    /**
     * Obtiene todos los productos activos.
     */
    List<Producto> findByActivoTrue();

    /**
     * Obtiene productos por categoría.
     */
    List<Producto> findByCategoriaId(Long categoriaId);

    /**
     * Obtiene productos activos por categoría.
     */
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);

    /**
     * Busca productos cuyo nombre contenga el texto dado.
     */
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

    /**
     * Obtiene productos con stock bajo.
     */
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockCritico AND p.activo = true")
    List<Producto> findProductosConStockBajo();

    /**
     * Obtiene productos sin stock.
     */
    List<Producto> findByStockLessThanEqualAndActivoTrue(int stock);

    /**
     * Cuenta productos por categoría.
     */
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :categoriaId AND p.activo = true")
    Long countByCategoriaId(@Param("categoriaId") Long categoriaId);
}

