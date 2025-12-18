package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteInstalacionInternet;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteInstalacionInternetRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteInstalacionInternetResponse;

public class PendienteInstalacionInternetMapper {

    // 🔹 Crear una nueva entidad desde el request
    public static PendienteInstalacionInternet toEntity(PendienteInstalacionInternetRequest request, Pendiente pendiente) {
        return PendienteInstalacionInternet.builder()
                .pendiente(pendiente)
                .velocidadSolicitada(request.getVelocidadSolicitada())
                .costoFijo(request.getCostoFijo())
                .tipoInstalacion(request.getTipoInstalacion())
                .build();
    }

    // 🔹 Actualizar una entidad existente
    public static void updateEntity(PendienteInstalacionInternet entity, PendienteInstalacionInternetRequest request, Pendiente pendiente) {
        entity.setPendiente(pendiente);
        entity.setVelocidadSolicitada(request.getVelocidadSolicitada());
        entity.setCostoFijo(request.getCostoFijo());
        entity.setTipoInstalacion(request.getTipoInstalacion());
    }

    // 🔹 Convertir entidad a respuesta
    public static PendienteInstalacionInternetResponse toResponse(PendienteInstalacionInternet entity) {
        return PendienteInstalacionInternetResponse.builder()
                .id(entity.getPendienteId())
                .pendienteId(entity.getPendiente() != null ? entity.getPendiente().getId() : null)
                .velocidadSolicitada(entity.getVelocidadSolicitada())
                .costoFijo(entity.getCostoFijo())
                .tipoInstalacion(entity.getTipoInstalacion())
                .ppoe(entity.getPpoe())
                .vlan(entity.getVlan())
                .ppoePassword(entity.getPpoePassword())
                .build();
    }
}
