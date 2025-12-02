package com.taxsiito.backend.service;

import com.taxsiito.backend.dto.UsuarioDTO;
import com.taxsiito.backend.model.Usuario;
import com.taxsiito.backend.model.enums.Rol;
import com.taxsiito.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Usuarios.
 * Contraseñas en texto plano (sin encriptación).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los usuarios.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por ID.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioDTO::fromEntity);
    }

    /**
     * Obtiene un usuario por correo.
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    /**
     * Crea un nuevo usuario.
     */
    public UsuarioDTO crear(Usuario usuario) {
        // Verificar que el correo no exista
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        // Verificar que el RUN no exista (si se proporciona)
        if (usuario.getRun() != null && !usuario.getRun().isEmpty()) {
            if (usuarioRepository.existsByRun(usuario.getRun())) {
                throw new RuntimeException("Ya existe un usuario con ese RUN");
            }
        }

        // Asignar rol por defecto si no tiene
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }

        // Guardar usuario (contraseña en texto plano)
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioDTO.fromEntity(guardado);
    }

    /**
     * Actualiza un usuario existente.
     */
    public UsuarioDTO actualizar(Long id, Usuario usuarioActualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos
        if (usuarioActualizado.getNombres() != null) {
            existente.setNombres(usuarioActualizado.getNombres());
        }
        if (usuarioActualizado.getApellidos() != null) {
            existente.setApellidos(usuarioActualizado.getApellidos());
        }
        if (usuarioActualizado.getDireccion() != null) {
            existente.setDireccion(usuarioActualizado.getDireccion());
        }
        if (usuarioActualizado.getRegion() != null) {
            existente.setRegion(usuarioActualizado.getRegion());
        }
        if (usuarioActualizado.getComuna() != null) {
            existente.setComuna(usuarioActualizado.getComuna());
        }
        if (usuarioActualizado.getTelefono() != null) {
            existente.setTelefono(usuarioActualizado.getTelefono());
        }
        if (usuarioActualizado.getRol() != null) {
            existente.setRol(usuarioActualizado.getRol());
        }
        if (usuarioActualizado.getActivo() != null) {
            existente.setActivo(usuarioActualizado.getActivo());
        }

        // Actualizar contraseña si se proporciona (texto plano)
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            existente.setPassword(usuarioActualizado.getPassword());
        }

        Usuario guardado = usuarioRepository.save(existente);
        return UsuarioDTO.fromEntity(guardado);
    }

    /**
     * Elimina un usuario por ID.
     */
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Desactiva un usuario (soft delete).
     */
    public UsuarioDTO desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        return UsuarioDTO.fromEntity(usuarioRepository.save(usuario));
    }

    /**
     * Obtiene usuarios por rol.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuarios por nombre o apellido.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscar(String texto) {
        return usuarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(texto, texto)
                .stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
