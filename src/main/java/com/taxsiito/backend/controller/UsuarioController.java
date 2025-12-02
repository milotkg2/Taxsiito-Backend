package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.UsuarioDTO;
import com.taxsiito.backend.model.Comuna;
import com.taxsiito.backend.model.Region;
import com.taxsiito.backend.model.Usuario;
import com.taxsiito.backend.model.enums.Rol;
import com.taxsiito.backend.repository.ComunaRepository;
import com.taxsiito.backend.repository.RegionRepository;
import com.taxsiito.backend.repository.UsuarioRepository;
import com.taxsiito.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "CRUD de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;

    /**
     * Obtiene todos los usuarios.
     */
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    /**
     * Obtiene un usuario por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Obtiene un usuario por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario.
     */
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> request) {
        try {
            // Buscar Region por ID
            Region region = null;
            if (request.get("regionId") != null) {
                Long regionId = Long.valueOf(request.get("regionId").toString());
                region = regionRepository.findById(regionId).orElse(null);
            }

            // Buscar Comuna por ID
            Comuna comuna = null;
            if (request.get("comunaId") != null) {
                Long comunaId = Long.valueOf(request.get("comunaId").toString());
                comuna = comunaRepository.findById(comunaId).orElse(null);
                // Si encontramos la comuna, asegurar que la region coincida
                if (comuna != null && region == null) {
                    region = comuna.getRegion();
                }
            }

            // Crear usuario
            Usuario nuevoUsuario = Usuario.builder()
                    .run((String) request.get("run"))
                    .nombres((String) request.get("nombres"))
                    .apellidos((String) request.get("apellidos"))
                    .correo((String) request.get("correo"))
                    .password(request.get("password") != null ? (String) request.get("password") : "1234")
                    .direccion((String) request.get("direccion"))
                    .region(region)
                    .comuna(comuna)
                    .telefono(request.get("telefono") != null ? (String) request.get("telefono") : null)
                    .rol(request.get("rol") != null ? Rol.valueOf(request.get("rol").toString()) : Rol.CLIENTE)
                    .build();

            UsuarioDTO creado = usuarioService.crear(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    /**
     * Actualiza un usuario.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            // Obtener usuario existente desde el repositorio
            Usuario existente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Buscar Region por ID si se proporciona
            if (request.get("regionId") != null) {
                Long regionId = Long.valueOf(request.get("regionId").toString());
                Region region = regionRepository.findById(regionId).orElse(null);
                existente.setRegion(region);
            }

            // Buscar Comuna por ID si se proporciona
            if (request.get("comunaId") != null) {
                Long comunaId = Long.valueOf(request.get("comunaId").toString());
                Comuna comuna = comunaRepository.findById(comunaId).orElse(null);
                existente.setComuna(comuna);
            }

            // Actualizar otros campos
            if (request.get("run") != null) existente.setRun((String) request.get("run"));
            if (request.get("nombres") != null) existente.setNombres((String) request.get("nombres"));
            if (request.get("apellidos") != null) existente.setApellidos((String) request.get("apellidos"));
            if (request.get("correo") != null) existente.setCorreo((String) request.get("correo"));
            if (request.get("direccion") != null) existente.setDireccion((String) request.get("direccion"));
            if (request.get("telefono") != null) existente.setTelefono((String) request.get("telefono"));
            if (request.get("rol") != null) existente.setRol(Rol.valueOf(request.get("rol").toString()));

            UsuarioDTO actualizado = usuarioService.actualizar(id, existente);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    /**
     * Elimina un usuario.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario permanentemente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Desactiva un usuario (soft delete).
     */
    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario sin eliminarlo")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            UsuarioDTO desactivado = usuarioService.desactivar(id);
            return ResponseEntity.ok(desactivado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene usuarios por rol.
     */
    @GetMapping("/rol/{rol}")
    @Operation(summary = "Usuarios por rol", description = "Obtiene usuarios por su rol")
    public ResponseEntity<List<UsuarioDTO>> obtenerPorRol(@PathVariable String rol) {
        try {
            Rol rolEnum = Rol.valueOf(rol.toUpperCase());
            return ResponseEntity.ok(usuarioService.obtenerPorRol(rolEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca usuarios por nombre o apellido.
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuarios", description = "Busca usuarios por nombre o apellido")
    public ResponseEntity<List<UsuarioDTO>> buscar(@RequestParam String texto) {
        return ResponseEntity.ok(usuarioService.buscar(texto));
    }
}
