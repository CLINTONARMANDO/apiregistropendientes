package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteRecojoDispositivo;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteRecojoDispositivoRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteRecojoDispositivoResponse;

public class PendienteRecojoDispositivoMapper {

    // ✅ Convierte de Request a Entity (para crear un nuevo registro)
    public static PendienteRecojoDispositivo toEntity(PendienteRecojoDispositivoRequest request, Pendiente pendiente) {
        if (request == null) return null;

        return PendienteRecojoDispositivo.builder()
                .pendiente(pendiente)
                .dispositivo(request.getDispositivo())
                .motivo(request.getMotivo())
                .build();
    }

    // ✅ Actualiza una entidad existente con datos del request
    public static void updateEntity(PendienteRecojoDispositivo entity, PendienteRecojoDispositivoRequest request, Pendiente pendiente) {
        if (entity == null || request == null) return;

        entity.setPendiente(pendiente);
        entity.setDispositivo(request.getDispositivo());
        entity.setMotivo(request.getMotivo());
    }

    // ✅ Convierte de Entity a Response (para enviar al cliente)
    public static PendienteRecojoDispositivoResponse toResponse(PendienteRecojoDispositivo entity) {
        if (entity == null) return null;

        return PendienteRecojoDispositivoResponse.builder()
                .id(entity.getPendienteId())
                .pendienteId(entity.getPendiente() != null ? entity.getPendiente().getId() : null)
                .dispositivo(entity.getDispositivo())
                .motivo(entity.getMotivo())
                .build();
    }
}
