package com.clindevstudio.apiregistropendientes.modules.catalogos;

import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoResponse;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    // 🔹 Crear un catálogo
    @PostMapping
    public TransactionResponse<CatalogoResponse> crearCatalogo(@RequestBody CatalogoRequest request) {
        CatalogoResponse response = catalogoService.crearCatalogo(request);
        return TransactionResponseFactory.success(response, "Catálogo creado correctamente");
    }

    // 🔹 Editar un catálogo
    @PutMapping("/{id}")
    public TransactionResponse<CatalogoResponse> editarCatalogo(
            @PathVariable Long id,
            @RequestBody CatalogoRequest request
    ) {
        CatalogoResponse response = catalogoService.editarCatalogo(id, request);
        return TransactionResponseFactory.success(response, "Catálogo actualizado correctamente");
    }

    // 🔹 Eliminar lógicamente un catálogo
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarCatalogo(@PathVariable Long id) {
        catalogoService.eliminarCatalogo(id);
        return TransactionResponseFactory.success(null, "Catálogo eliminado correctamente");
    }

    // 🔹 Listar todos los catálogos activos
    @GetMapping
    public TransactionResponse<List<CatalogoResponse>> listarCatalogos() {
        List<CatalogoResponse> catalogos = catalogoService.listarCatalogos();
        return TransactionResponseFactory.success(catalogos, "Catálogos obtenidos correctamente");
    }

    // 🔹 Listar productos por catálogo
    @GetMapping("/{catalogoId}/productos")
    public TransactionResponse<List<CatalogoProductoResponse>> listarProductosPorCatalogo(
            @PathVariable Long catalogoId
    ) {
        List<CatalogoProductoResponse> productos = catalogoService.listarProductosPorCatalogo(catalogoId);
        return TransactionResponseFactory.success(productos, "Productos del catálogo obtenidos correctamente");
    }

    // 🔹 Agregar producto a catálogo
    @PostMapping("/productos")
    public TransactionResponse<CatalogoProductoResponse> agregarProductoACatalogo(
            @RequestBody CatalogoProductoRequest request
    ) {
        CatalogoProductoResponse response = catalogoService.agregarProductoACatalogo(request);
        return TransactionResponseFactory.success(response, "Producto agregado al catálogo correctamente");
    }

    // 🔹 Editar producto de catálogo
    @PutMapping("/productos/{id}")
    public TransactionResponse<CatalogoProductoResponse> editarProductoDeCatalogo(
            @PathVariable Long id,
            @RequestBody CatalogoProductoRequest request
    ) {
        CatalogoProductoResponse response = catalogoService.editarProductoDeCatalogo(id, request);
        return TransactionResponseFactory.success(response, "Producto del catálogo actualizado correctamente");
    }

    // 🔹 Eliminar lógicamente producto de catálogo
    @DeleteMapping("/productos/{id}")
    public TransactionResponse<Void> eliminarProductoDeCatalogo(@PathVariable Long id) {
        catalogoService.eliminarProductoDeCatalogo(id);
        return TransactionResponseFactory.success(null, "Producto eliminado del catálogo correctamente");
    }
}
