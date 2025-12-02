package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.ProductoDTO;
import com.taxsiito.backend.model.Categoria;
import com.taxsiito.backend.model.Producto;
import com.taxsiito.backend.repository.CategoriaRepository;
import com.taxsiito.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Productos.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Obtiene todos los productos.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene solo los productos activos.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoDTO::fromEntity);
    }

    /**
     * Obtiene un producto por código.
     */
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> obtenerPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .map(ProductoDTO::fromEntity);
    }

    /**
     * Crea un nuevo producto.
     */
    public ProductoDTO crear(Producto producto, Long categoriaId) {
        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }

        // Asignar categoría si se proporciona
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        Producto guardado = productoRepository.save(producto);
        return ProductoDTO.fromEntity(guardado);
    }

    /**
     * Actualiza un producto existente.
     */
    public ProductoDTO actualizar(Long id, Producto productoActualizado, Long categoriaId) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (productoActualizado.getNombre() != null) {
            existente.setNombre(productoActualizado.getNombre());
        }
        if (productoActualizado.getDescripcion() != null) {
            existente.setDescripcion(productoActualizado.getDescripcion());
        }
        if (productoActualizado.getPrecio() != null) {
            existente.setPrecio(productoActualizado.getPrecio());
        }
        if (productoActualizado.getStock() != null) {
            existente.setStock(productoActualizado.getStock());
        }
        if (productoActualizado.getStockCritico() != null) {
            existente.setStockCritico(productoActualizado.getStockCritico());
        }
        if (productoActualizado.getImagen() != null) {
            existente.setImagen(productoActualizado.getImagen());
        }
        if (productoActualizado.getActivo() != null) {
            existente.setActivo(productoActualizado.getActivo());
        }

        // Actualizar categoría si se proporciona
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        }

        Producto guardado = productoRepository.save(existente);
        return ProductoDTO.fromEntity(guardado);
    }

    /**
     * Elimina un producto.
     */
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    /**
     * Desactiva un producto (soft delete).
     */
    public ProductoDTO desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        return ProductoDTO.fromEntity(productoRepository.save(producto));
    }

    /**
     * Obtiene productos por categoría.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId).stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca productos por nombre.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene productos con stock bajo.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerConStockBajo() {
        return productoRepository.findProductosConStockBajo().stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza el stock de un producto.
     */
    public ProductoDTO actualizarStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        int nuevoStock = producto.getStock() + cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente");
        }
        
        producto.setStock(nuevoStock);
        return ProductoDTO.fromEntity(productoRepository.save(producto));
    }
}

