package com.clindevstudio.apiregistropendientes.modules.notas.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteNota;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteNotaImagen;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.PendienteNotaImagenRequest;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.PendienteNotaImagenResponse;

public class PendienteNotaImagenMapper {

    public static PendienteNotaImagen toEntity(PendienteNotaImagenRequest request, PendienteNota nota) {
        if (request == null) return null;

        return PendienteNotaImagen.builder()
                .nota(nota)
                .url(request.getUrl())
                .build();
    }

    public static PendienteNotaImagenResponse toResponse(PendienteNotaImagen entity) {
        if (entity == null) return null;

        return PendienteNotaImagenResponse.builder()
                .id(entity.getId())
                .notaId(entity.getNota().getId())
                .url(entity.getUrl())
                .build();
    }
}
