package com.clindevstudio.apiregistropendientes.modules.productos.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoResponse;

public class ProductoMapper {

    // ✅ Convierte de Request a Entidad
    public static Producto toEntity(ProductoRequest request) {
        if (request == null) return null;

        return Producto.builder()
                .codigoBarra(request.getCodigoBarra())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .costoUnitario(request.getCostoUnitario())
                .build();
    }

    // ✅ Convierte de Entidad a Response
    public static ProductoResponse toResponse(Producto producto) {
        if (producto == null) return null;

        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setCodigoBarra(producto.getCodigoBarra());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setCostoUnitario(producto.getCostoUnitario());
        return response;
    }

    // ✅ Actualiza una entidad existente con datos del request
    public static void updateEntity(Producto producto, ProductoRequest request) {
        if (producto == null || request == null) return;

        producto.setCodigoBarra(request.getCodigoBarra());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCostoUnitario(request.getCostoUnitario());
    }
}
