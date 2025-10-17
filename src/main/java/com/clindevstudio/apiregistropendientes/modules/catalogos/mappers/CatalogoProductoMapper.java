package com.clindevstudio.apiregistropendientes.modules.catalogos.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Catalogo;
import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.database.entities.CatalogoProducto;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoResponse;

public class CatalogoProductoMapper {

    // 🔹 Convierte de entidad a DTO de respuesta
    public static CatalogoProductoResponse toResponse(CatalogoProducto entity) {
        if (entity == null) return null;

        return CatalogoProductoResponse.builder()
                .id(entity.getId())
                .catalogoId(entity.getCatalogo() != null ? entity.getCatalogo().getId() : null)
                .catalogoNombre(entity.getCatalogo() != null ? entity.getCatalogo().getNombre() : null)
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .cantidad(entity.getCantidad())
                .build();
    }

    // 🔹 Convierte de DTO de request a entidad nueva
    public static CatalogoProducto toEntity(CatalogoProductoRequest dto, Catalogo catalogo, Producto producto) {
        if (dto == null) return null;

        return CatalogoProducto.builder()
                .catalogo(catalogo)
                .producto(producto)
                .cantidad(dto.getCantidad())
                .build();
    }

    // 🔹 Actualiza una entidad existente con los datos del DTO
    public static void updateEntity(CatalogoProducto entity, CatalogoProductoRequest dto, Catalogo catalogo, Producto producto) {
        if (dto == null || entity == null) return;

        if (catalogo != null) entity.setCatalogo(catalogo);
        if (producto != null) entity.setProducto(producto);
        if (dto.getCantidad() != null) entity.setCantidad(dto.getCantidad());
    }
}

