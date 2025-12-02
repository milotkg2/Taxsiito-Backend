package com.taxsiito.backend.controller;

import com.taxsiito.backend.dto.UsuarioDTO;
import com.taxsiito.backend.dto.auth.LoginRequest;
import com.taxsiito.backend.dto.auth.RegisterRequest;
import com.taxsiito.backend.model.Comuna;
import com.taxsiito.backend.model.Region;
import com.taxsiito.backend.model.Usuario;
import com.taxsiito.backend.model.enums.Rol;
import com.taxsiito.backend.repository.ComunaRepository;
import com.taxsiito.backend.repository.RegionRepository;
import com.taxsiito.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para autenticacion y registro de usuarios.
 * Contrasenas en texto plano (sin encriptacion).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Endpoints para login y registro")
public class AuthController {

    private final UsuarioService usuarioService;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;

    /**
     * Login de usuario.
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Autentica un usuario con correo y contrasena")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Buscar usuario por correo
            Usuario usuario = usuarioService.obtenerPorCorreo(request.getCorreo())
                    .orElse(null);

            // Verificar usuario y contrasena (texto plano)
            if (usuario == null || !usuario.getPassword().equals(request.getPassword())) {
                response.put("error", "Usuario o contrasena invalidos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Verificar que el usuario esta activo
            if (!usuario.getActivo()) {
                response.put("error", "Usuario desactivado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Respuesta exitosa
            UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuarioDTO);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Error en el servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Registro de nuevo usuario.
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea una nueva cuenta de usuario")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Buscar Region por ID o por nombre
            Region region = null;
            if (request.getRegionId() != null) {
                region = regionRepository.findById(request.getRegionId()).orElse(null);
            } else if (request.getRegion() != null && !request.getRegion().isEmpty()) {
                region = regionRepository.findByNombre(request.getRegion()).orElse(null);
            }

            // Buscar Comuna por ID o por nombre
            Comuna comuna = null;
            if (request.getComunaId() != null) {
                comuna = comunaRepository.findById(request.getComunaId()).orElse(null);
                // Si encontramos la comuna, asegurar que la region coincida
                if (comuna != null && region == null) {
                    region = comuna.getRegion();
                }
            } else if (request.getComuna() != null && !request.getComuna().isEmpty() && region != null) {
                comuna = comunaRepository.findByNombreAndRegion(request.getComuna(), region).orElse(null);
            }

            // Crear usuario
            Usuario nuevoUsuario = Usuario.builder()
                    .run(request.getRun())
                    .nombres(request.getNombres())
                    .apellidos(request.getApellidos())
                    .correo(request.getCorreo())
                    .password(request.getPassword())
                    .direccion(request.getDireccion())
                    .region(region)
                    .comuna(comuna)
                    .telefono(request.getTelefono())
                    .rol(Rol.CLIENTE)
                    .build();

            UsuarioDTO usuarioCreado = usuarioService.crear(nuevoUsuario);

            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("usuario", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Error en el servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene un usuario por ID.
     */
    @GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener usuario", description = "Obtiene los datos de un usuario por ID")
    public ResponseEntity<Map<String, Object>> getUsuario(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        return usuarioService.obtenerPorId(id)
                .map(usuario -> {
                    response.put("usuario", usuario);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("error", "Usuario no encontrado");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }
}
