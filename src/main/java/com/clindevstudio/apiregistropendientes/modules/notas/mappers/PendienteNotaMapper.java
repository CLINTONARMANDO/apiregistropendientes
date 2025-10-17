package com.clindevstudio.apiregistropendientes.modules.notas.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.*;

import java.util.List;
import java.util.stream.Collectors;

public class PendienteNotaMapper {

    public static PendienteNota toEntity(
            PendienteNotaRequest request,
            Pendiente pendiente,
            Empleado empleado
    ) {
        if (request == null) return null;

        PendienteNota entity = PendienteNota.builder()
                .pendiente(pendiente)
                .empleado(empleado)
                .nota(request.getNota())
                .estadoPendiente(request.getEstadoPendiente())
                .build();

        if (request.getImagenes() != null) {
            entity.setImagenes(
                    request.getImagenes().stream()
                            .map(imgReq -> PendienteNotaImagenMapper.toEntity(imgReq, entity))
                            .collect(Collectors.toList())
            );
        }

        return entity;
    }

    public static PendienteNotaResponse toResponse(PendienteNota entity) {
        if (entity == null) return null;

        return PendienteNotaResponse.builder()
                .id(entity.getId())
                .pendienteId(entity.getPendiente().getId())
                .empleadoId(entity.getEmpleado().getId())
                .empleadoNombre(entity.getEmpleado().getNombre() + " " + entity.getEmpleado().getApellidoPaterno())
                .nota(entity.getNota())
                .estadoPendiente(entity.getEstadoPendiente())
                .fechaCreacion(entity.getFechaCreacion())
                .imagenes(entity.getImagenes() != null
                        ? entity.getImagenes().stream()
                        .map(PendienteNotaImagenMapper::toResponse)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static void updateEntity(PendienteNota entity, PendienteNotaRequest request, Empleado empleado) {
        if (entity == null || request == null) return;

        entity.setEmpleado(empleado);
        entity.setNota(request.getNota());
        entity.setEstadoPendiente(request.getEstadoPendiente());

        if (request.getImagenes() != null) {
            entity.setImagenes(
                    request.getImagenes().stream()
                            .map(imgReq -> PendienteNotaImagenMapper.toEntity(imgReq, entity))
                            .collect(Collectors.toList())
            );
        }
    }

    public static List<PendienteNotaResponse> toResponseList(List<PendienteNota> entities) {
        return entities == null ? List.of() :
                entities.stream().map(PendienteNotaMapper::toResponse).collect(Collectors.toList());
    }
}
