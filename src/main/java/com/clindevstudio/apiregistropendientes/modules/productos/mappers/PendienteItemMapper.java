package com.clindevstudio.apiregistropendientes.modules.productos.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteItem;
import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PendienteItemMapper {

    // ✅ Convierte de Request a Entity (para crear un nuevo item)
    public static PendienteItem toEntity(PendienteItemRequest request, Pendiente pendiente, Producto producto) {
        if (request == null) return null;

        return PendienteItem.builder()
                .pendiente(pendiente)
                .producto(producto)
                .descripcion(request.getDescripcion())
                .cantidad(request.getCantidad())
                .costoUnitario(request.getCostoUnitario())
                .costoTotal(request.getCostoTotal())
                .build();
    }

    // ✅ Actualiza una entidad existente con datos del request
    public static void updateEntity(PendienteItem entity, PendienteItemRequest request, Pendiente pendiente, Producto producto) {
        if (entity == null || request == null) return;

        entity.setPendiente(pendiente);
        entity.setProducto(producto);
        entity.setDescripcion(request.getDescripcion());
        entity.setCantidad(request.getCantidad());
        entity.setCostoUnitario(request.getCostoUnitario());
        entity.setCostoTotal(request.getCostoTotal());
    }

    // ✅ Convierte de Entity a Response (para enviar al cliente)
    public static PendienteItemResponse toResponse(PendienteItem entity) {
        if (entity == null) return null;

        return PendienteItemResponse.builder()
                .id(entity.getId())
                .pendienteId(entity.getPendiente() != null ? entity.getPendiente().getId() : null)
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .descripcion(entity.getDescripcion())
                .cantidad(entity.getCantidad())
                .costoUnitario(entity.getCostoUnitario())
                .costoTotal(entity.getCostoTotal())
                .build();
    }

    // ✅ Convierte una lista de entidades a una lista de responses
    public static List<PendienteItemResponse> toResponseList(List<PendienteItem> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(PendienteItemMapper::toResponse)
                .collect(Collectors.toList());
    }
}