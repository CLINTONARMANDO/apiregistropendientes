package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteInstalacionCamaras;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteInstalacionCamarasRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteInstalacionCamarasResponse;

public class PendienteInstalacionCamarasMapper {

    // 🔹 Crear una nueva entidad
    public static PendienteInstalacionCamaras toEntity(PendienteInstalacionCamarasRequest request, Pendiente pendiente) {
        return PendienteInstalacionCamaras.builder()
                .pendiente(pendiente)
                .build();
    }

    // 🔹 Actualizar una entidad existente
    public static void updateEntity(PendienteInstalacionCamaras entity, PendienteInstalacionCamarasRequest request, Pendiente pendiente) {
        entity.setPendiente(pendiente);
    }

    // 🔹 Convertir entidad a respuesta
    public static PendienteInstalacionCamarasResponse toResponse(PendienteInstalacionCamaras entity) {
        return PendienteInstalacionCamarasResponse.builder()
                .id(entity.getPendienteId())
                .pendienteId(entity.getPendiente() != null ? entity.getPendiente().getId() : null)
                .build();
    }
}
