package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteAveria;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteAveriaRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteAveriaResponse;

public class PendienteAveriaMapper {

    public static void updateEntity(PendienteAveria entity, PendienteAveriaRequest request, Pendiente pendiente) {
        entity.setPendiente(pendiente);
        entity.setDescripcionFalla(request.getDescripcionFalla());
    }

    public static PendienteAveria toEntity(PendienteAveriaRequest request, Pendiente pendiente) {
        PendienteAveria entity =  new PendienteAveria();
        entity.setPendiente(pendiente);
        entity.setDescripcionFalla(request.getDescripcionFalla());
        return entity;
    }

    public static PendienteAveriaResponse toResponse(PendienteAveria entity) {
        return PendienteAveriaResponse.builder()
                .id(entity.getPendienteId())
                .pendienteId(entity.getPendiente().getId())
                .descripcionFalla(entity.getDescripcionFalla())
                .build();
    }
}
