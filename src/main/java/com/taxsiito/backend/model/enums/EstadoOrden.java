package com.taxsiito.backend.model.enums;

/**
 * Enum que define los estados posibles de una orden.
 */
public enum EstadoOrden {
    PENDIENTE,      // Orden recién creada
    PAGADA,         // Pago confirmado
    EN_PREPARACION, // Se está preparando el pedido
    ENVIADA,        // Pedido enviado
    ENTREGADA,      // Pedido entregado
    CANCELADA       // Orden cancelada
}

