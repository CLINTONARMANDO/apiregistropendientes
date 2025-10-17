package com.clindevstudio.apiregistropendientes.modules.notificaciones.mappers;


import com.clindevstudio.apiregistropendientes.database.entities.Notificacion;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionRequest;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NotificacionMapper {

    // 🔹 Convierte de Request a Entidad
    public static Notificacion toEntity(NotificacionRequest request) {
        if (request == null) {
            return null;
        }

        return Notificacion.builder()
                .titulo(request.getTitulo())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .estado(request.getEstado())
                .usuarioId(request.getUsuarioId())
                .fechaCreacion(LocalDateTime.now()) // fecha actual al crear
                .build();
    }

    // 🔹 Convierte de Entidad a Response
    public static NotificacionResponse toResponse(Notificacion entity) {
        if (entity == null) {
            return null;
        }

        return NotificacionResponse.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .mensaje(entity.getMensaje())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .fechaCreacion(entity.getFechaCreacion())
                .usuarioId(entity.getUsuarioId())
                .build();
    }

    // 🔹 Convierte una lista de entidades a responses
    public static List<NotificacionResponse> toResponseList(List<Notificacion> entities) {
        return entities.stream()
                .map(NotificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Actualiza los datos de una entidad existente
    public static void updateEntity(Notificacion entity, NotificacionRequest request) {
        entity.setTitulo(request.getTitulo());
        entity.setMensaje(request.getMensaje());
        entity.setTipo(request.getTipo());
        entity.setEstado(request.getEstado());
        entity.setUsuarioId(request.getUsuarioId());
    }
}
