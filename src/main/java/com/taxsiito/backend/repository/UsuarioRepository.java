package com.taxsiito.backend.repository;

import com.taxsiito.backend.model.Usuario;
import com.taxsiito.backend.model.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Busca un usuario por su RUN.
     */
    Optional<Usuario> findByRun(String run);

    /**
     * Verifica si existe un usuario con el correo dado.
     */
    boolean existsByCorreo(String correo);

    /**
     * Verifica si existe un usuario con el RUN dado.
     */
    boolean existsByRun(String run);

    /**
     * Obtiene todos los usuarios por rol.
     */
    List<Usuario> findByRol(Rol rol);

    /**
     * Obtiene todos los usuarios activos.
     */
    List<Usuario> findByActivoTrue();

    /**
     * Busca usuarios cuyo nombre o apellido contenga el texto dado.
     */
    List<Usuario> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);
}

