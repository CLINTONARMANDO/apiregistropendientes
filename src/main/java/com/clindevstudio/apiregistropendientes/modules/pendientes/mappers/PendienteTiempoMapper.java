package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteTiempo;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteTiempoRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteTiempoResponse;

public class PendienteTiempoMapper {

    /**
     * Actualiza o crea una entidad PendienteTiempo a partir del request.
     */
    public static void updateEntity(PendienteTiempo entity, PendienteTiempoRequest request, Pendiente pendiente, Empleado empleado) {
        entity.setPendiente(pendiente);
        entity.setEmpleado(empleado);
        entity.setHoraInicio(request.getHoraInicio());
        entity.setHoraFin(request.getHoraFin());
        entity.setDescripcion(request.getDescripcion());
    }

    /**
     * Convierte una entidad PendienteTiempo en un DTO PendienteTiempoResponse.
     */
    public static PendienteTiempoResponse toResponse(PendienteTiempo entity) {
        return PendienteTiempoResponse.builder()
                .id(entity.getId())
                .pendienteId(entity.getPendiente().getId())
                .empleadoId(entity.getEmpleado().getId())
                .empleadoNombre(entity.getEmpleado().getNombre() + " " + entity.getEmpleado().getApellidoPaterno())
                .horaInicio(entity.getHoraInicio())
                .horaFin(entity.getHoraFin())
                .descripcion(entity.getDescripcion())
                .build();
    }


    public static PendienteTiempo toEntity( PendienteTiempoRequest request, Pendiente pendiente, Empleado empleado) {
        PendienteTiempo entity = new PendienteTiempo();
        entity.setPendiente(pendiente);
        entity.setEmpleado(empleado);
        entity.setHoraInicio(request.getHoraInicio());
        entity.setHoraFin(request.getHoraFin());
        entity.setDescripcion(request.getDescripcion());
        return entity;
    }
}
