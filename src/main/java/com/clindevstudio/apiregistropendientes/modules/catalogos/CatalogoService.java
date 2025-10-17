package com.clindevstudio.apiregistropendientes.modules.catalogos;

import com.clindevstudio.apiregistropendientes.database.entities.Catalogo;
import com.clindevstudio.apiregistropendientes.database.entities.CatalogoProducto;
import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.database.repositories.CatalogoProductoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.CatalogoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.ProductoRepository;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoProductoResponse;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoRequest;
import com.clindevstudio.apiregistropendientes.modules.catalogos.dtos.CatalogoResponse;
import com.clindevstudio.apiregistropendientes.modules.catalogos.mappers.CatalogoMapper;
import com.clindevstudio.apiregistropendientes.modules.catalogos.mappers.CatalogoProductoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogoService {

    private final CatalogoRepository catalogoRepository;
    private final ProductoRepository productoRepository;
    private final CatalogoProductoRepository catalogoProductoRepository;

    // 🔹 Crear un catálogo
    public CatalogoResponse crearCatalogo(CatalogoRequest request) {
        Catalogo catalogo = CatalogoMapper.toEntity(request);
        Catalogo saved = catalogoRepository.save(catalogo);
        return CatalogoMapper.toResponse(saved);
    }

    // 🔹 Editar un catálogo
    public CatalogoResponse editarCatalogo(Long id, CatalogoRequest request) {
        Catalogo catalogo = catalogoRepository.findByIdAndVigenteTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Catálogo no encontrado"));

        CatalogoMapper.updateEntity(catalogo, request);
        Catalogo updated = catalogoRepository.save(catalogo);
        return CatalogoMapper.toResponse(updated);
    }

    // 🔹 Eliminar lógicamente un catálogo
    public void eliminarCatalogo(Long id) {
        Catalogo catalogo = catalogoRepository.findByIdAndVigenteTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Catálogo no encontrado"));
        catalogo.setVigente(false);
        catalogoRepository.save(catalogo);

        // También desactivar sus productos
        List<CatalogoProducto> productos = catalogoProductoRepository.findByCatalogoIdAndVigenteTrue(id);
        productos.forEach(p -> p.setVigente(false));
        catalogoProductoRepository.saveAll(productos);
    }

    // 🔹 Listar todos los catálogos activos
    @Transactional(readOnly = true)
    public List<CatalogoResponse> listarCatalogos() {
        return catalogoRepository.findAllByVigenteTrue().stream()
                .map(CatalogoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Listar productos por catálogo
    @Transactional(readOnly = true)
    public List<CatalogoProductoResponse> listarProductosPorCatalogo(Long catalogoId) {
        return catalogoProductoRepository.findByCatalogoIdAndVigenteTrue(catalogoId).stream()
                .map(CatalogoProductoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Agregar producto a catálogo
    public CatalogoProductoResponse agregarProductoACatalogo(CatalogoProductoRequest request) {
        Catalogo catalogo = catalogoRepository.findByIdAndVigenteTrue(request.getCatalogoId())
                .orElseThrow(() -> new EntityNotFoundException("Catálogo no encontrado"));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        CatalogoProducto catalogoProducto = CatalogoProductoMapper.toEntity(request, catalogo, producto);

        CatalogoProducto saved = catalogoProductoRepository.save(catalogoProducto);
        return CatalogoProductoMapper.toResponse(saved);
    }

    // 🔹 Editar producto de catálogo
    public CatalogoProductoResponse editarProductoDeCatalogo(Long id, CatalogoProductoRequest request) {
        CatalogoProducto existente = catalogoProductoRepository.findByIdAndVigenteTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Catálogo-Producto no encontrado"));

        Catalogo catalogo = catalogoRepository.findByIdAndVigenteTrue(request.getCatalogoId())
                .orElseThrow(() -> new EntityNotFoundException("Catalogo no encontrado"));
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        CatalogoProductoMapper.updateEntity(existente, request, catalogo, producto);
        CatalogoProducto updated = catalogoProductoRepository.save(existente);

        return CatalogoProductoMapper.toResponse(updated);
    }

    // 🔹 Eliminar lógicamente un producto de catálogo
    public void eliminarProductoDeCatalogo(Long id) {
        CatalogoProducto catalogoProducto = catalogoProductoRepository.findByIdAndVigenteTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Catálogo-Producto no encontrado"));
        catalogoProducto.setVigente(false);
        catalogoProductoRepository.save(catalogoProducto);
    }
}
