package com.clindevstudio.apiregistropendientes.modules.historial.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.entities.Historial;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialRequest;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialResponse;

public class HistorialMapper {

    // 🔹 Convierte entidad -> Response DTO
    public static HistorialResponse toResponse(Historial historial) {
        if (historial == null) return null;

        return HistorialResponse.builder()
                .id(historial.getId())
                .pendienteId(historial.getPendiente() != null ? historial.getPendiente().getId() : null)
                .empleadoId(historial.getEmpleado() != null ? historial.getEmpleado().getId() : null)
                .empleadoNombre(historial.getEmpleado() != null ? historial.getEmpleado().getNombre() : null)
                .estado(historial.getEstado())
                .detalles(historial.getDetalles())
                .fechaCreacion(historial.getFechaCreacion()) // viene del BaseEntity
                .build();
    }

    // 🔹 Convierte Request -> Entidad
    public static Historial toEntity(HistorialRequest request, Pendiente pendiente, Empleado empleado) {
        if (request == null) return null;

        return Historial.builder()
                .pendiente(pendiente)
                .empleado(empleado)
                .estado(request.getEstado())
                .detalles(request.getDetalles())
                .build();
    }

    // 🔹 Actualiza un historial existente (poco común, pero útil si se permite editar)
    public static void updateEntity(Historial historial, HistorialRequest request, Pendiente pendiente, Empleado empleado) {
        if (historial == null || request == null) return;

        historial.setPendiente(pendiente);
        historial.setEmpleado(empleado);
        historial.setEstado(request.getEstado());
        historial.setDetalles(request.getDetalles());
    }
}
