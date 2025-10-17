package com.clindevstudio.apiregistropendientes.modules.catalogos.mappers;


import com.clindevstudio.apiregistropendientes.database.entities.Catalogo;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoResponse;

public class CatalogoMapper {

    // ✅ Convierte una entidad a DTO de respuesta
    public static CatalogoResponse toResponse(Catalogo entity) {
        if (entity == null) return null;

        return CatalogoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .tipo(entity.getTipo())
                .build();
    }

    // ✅ Convierte un DTO de request a una nueva entidad
    public static Catalogo toEntity(CatalogoRequest dto) {
        if (dto == null) return null;

        return Catalogo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .tipo(dto.getTipo())
                .build();
    }

    // ✅ Actualiza una entidad existente con los valores del DTO (solo si no son nulos)
    public static void updateEntity(Catalogo entity, CatalogoRequest dto) {
        if (entity == null || dto == null) return;

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getTipo() != null) entity.setTipo(dto.getTipo());
    }
}
