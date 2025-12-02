package com.taxsiito.backend.model.enums;

/**
 * Enum que define los roles de usuario en el sistema.
 * Se usa para controlar el acceso a diferentes funcionalidades.
 */
public enum Rol {
    ADMIN,      // Administrador con acceso total
    VENDEDOR,   // Vendedor con acceso al panel de ventas
    CLIENTE     // Cliente regular
}

