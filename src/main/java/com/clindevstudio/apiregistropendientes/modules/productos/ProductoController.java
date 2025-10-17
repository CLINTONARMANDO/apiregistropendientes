package com.clindevstudio.apiregistropendientes.modules.productos;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemResponse;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public TransactionResponse<Page<ProductoResponse>> obtenerProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductoResponse> productos = productoService.listarProductosVigentes( page, size);
        return TransactionResponseFactory.success(
                productos,
                "Productos obtenidos correctamente"
        );
    }

    @GetMapping("/{id}")
    public TransactionResponse<ProductoResponse> obtenerProductoPorId(@PathVariable Long id) {
        ProductoResponse producto = productoService.obtenerProductoPorId(id);
        return TransactionResponseFactory.success(
                producto,
                "Producto obtenido correctamente"
        );
    }

    @PostMapping
    public TransactionResponse<ProductoResponse> registrarProducto(@RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.crearProducto(request);
        return TransactionResponseFactory.success(
                producto,
                "Producto registrado correctamente"
        );
    }

    @PutMapping("/{id}")
    public TransactionResponse<ProductoResponse> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.actualizarProducto(id, request);
        return TransactionResponseFactory.success(
                producto,
                "Producto actualizado correctamente"
        );
    }

    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return TransactionResponseFactory.success(
                null,
                "Producto eliminado correctamente (soft delete aplicado)"
        );
    }


    // ==========================================================
    // 🔹 PENDIENTE ITEMS
    // ==========================================================

    // 🟢 Listar ítems por pendiente
    @GetMapping("/pendientes/{pendienteId}/items")
    public TransactionResponse<List<PendienteItemResponse>> listarItemsPorPendiente(@PathVariable Long pendienteId) {
        List<PendienteItemResponse> items = productoService.findItemsByPendiente(pendienteId);
        return TransactionResponseFactory.success(
                items,
                "Ítems del pendiente obtenidos correctamente"
        );
    }

    // 🟢 Crear ítem de pendiente
    @PostMapping("/pendientes/items")
    public TransactionResponse<PendienteItemResponse> crearItemPendiente(@RequestBody PendienteItemRequest request) {
        PendienteItemResponse item = productoService.createPendienteItem(request);
        return TransactionResponseFactory.success(
                item,
                "Ítem del pendiente creado correctamente"
        );
    }

    // 🟢 Actualizar ítem de pendiente
    @PutMapping("/pendientes/items/{id}")
    public TransactionResponse<PendienteItemResponse> actualizarItemPendiente(
            @PathVariable Long id,
            @RequestBody PendienteItemRequest request
    ) {
        PendienteItemResponse item = productoService.updatePendienteItem(id, request);
        return TransactionResponseFactory.success(
                item,
                "Ítem del pendiente actualizado correctamente"
        );
    }

    // 🟢 Eliminar (soft delete) ítem de pendiente
    @DeleteMapping("/pendientes/items/{id}")
    public TransactionResponse<Void> eliminarItemPendiente(@PathVariable Long id) {
        productoService.deletePendienteItem(id);
        return TransactionResponseFactory.success(
                null,
                "Ítem del pendiente eliminado correctamente (soft delete aplicado)"
        );
    }
}