package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.CategoriaDTO;
import com.taxsiito.backend.model.Categoria;
import com.taxsiito.backend.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Categorías.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    /**
     * Obtiene todas las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene solo las categorías activas.
     */
    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerActivas() {
        return categoriaRepository.findByActivaTrue().stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una categoría por ID.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaDTO::fromEntity);
    }

    /**
     * Crea una nueva categoría.
     */
    public CategoriaDTO crear(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Categoria guardada = categoriaRepository.save(categoria);
        return CategoriaDTO.fromEntity(guardada);
    }

    /**
     * Actualiza una categoría existente.
     */
    public CategoriaDTO actualizar(Long id, Categoria categoriaActualizada) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (categoriaActualizada.getNombre() != null) {
            // Verificar que no exista otra con el mismo nombre
            Optional<Categoria> otra = categoriaRepository.findByNombre(categoriaActualizada.getNombre());
            if (otra.isPresent() && !otra.get().getId().equals(id)) {
                throw new RuntimeException("Ya existe otra categoría con ese nombre");
            }
            existente.setNombre(categoriaActualizada.getNombre());
        }
        if (categoriaActualizada.getDescripcion() != null) {
            existente.setDescripcion(categoriaActualizada.getDescripcion());
        }
        if (categoriaActualizada.getActiva() != null) {
            existente.setActiva(categoriaActualizada.getActiva());
        }

        Categoria guardada = categoriaRepository.save(existente);
        return CategoriaDTO.fromEntity(guardada);
    }

    /**
     * Elimina una categoría.
     */
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        // Verificar que no tenga productos asociados
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar: la categoría tiene productos asociados");
        }
        
        categoriaRepository.deleteById(id);
    }
}

