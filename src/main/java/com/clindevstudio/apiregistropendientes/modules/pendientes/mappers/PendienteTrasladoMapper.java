package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteTraslado;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteTrasladoRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteTrasladoResponse;

public class PendienteTrasladoMapper {

    // ✅ Convierte de Request a Entity (para crear un nuevo registro)
    public static PendienteTraslado toEntity(PendienteTrasladoRequest request, Pendiente pendiente) {
        if (request == null) return null;

        return PendienteTraslado.builder()
                .pendiente(pendiente)
                .direccionDestino(request.getDireccionDestino())
                .tipoServicio(request.getTipoServicio())
                .lugarDestino(request.getLugarDestino())
                .build();
    }

    // ✅ Actualiza una entidad existente con datos del request
    public static void updateEntity(PendienteTraslado entity, PendienteTrasladoRequest request, Pendiente pendiente) {
        if (entity == null || request == null) return;

        entity.setPendiente(pendiente);
        entity.setDireccionDestino(request.getDireccionDestino());
        entity.setTipoServicio(request.getTipoServicio());
        entity.setLugarDestino(request.getLugarDestino());
    }

    // ✅ Convierte de Entity a Response (para enviar al cliente)
    public static PendienteTrasladoResponse toResponse(PendienteTraslado entity) {
        if (entity == null) return null;

        return PendienteTrasladoResponse.builder()
                .id(entity.getPendienteId())
                .pendienteId(entity.getPendiente() != null ? entity.getPendiente().getId() : null)
                .direccionDestino(entity.getDireccionDestino())
                .tipoServicio(entity.getTipoServicio())
                .lugarDestino(entity.getLugarDestino())
                .build();
    }
}
