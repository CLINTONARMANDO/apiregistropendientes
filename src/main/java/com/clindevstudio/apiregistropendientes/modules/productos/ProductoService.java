package com.clindevstudio.apiregistropendientes.modules.productos;


import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteItem;
import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.database.repositories.PendienteItemRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.PendienteNotaRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.PendienteRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.ProductoRepository;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemResponse;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.ProductoResponse;
import com.clindevstudio.apiregistropendientes.modules.productos.mappers.PendienteItemMapper;
import com.clindevstudio.apiregistropendientes.modules.productos.mappers.ProductoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final PendienteItemRepository pendienteItemRepository;
    private final PendienteRepository pendienteRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            PendienteItemRepository pendienteItemRepository,
            PendienteRepository pendienteRepository
    ) {
        this.productoRepository = productoRepository;
        this.pendienteItemRepository = pendienteItemRepository;
        this.pendienteRepository = pendienteRepository;
    }

    // ✅ Crear producto
    public ProductoResponse crearProducto(ProductoRequest request) {
        Producto producto = ProductoMapper.toEntity(request);
        return ProductoMapper.toResponse(productoRepository.save(producto));
    }

    // ✅ Listar productos vigentes
    public Page<ProductoResponse> listarProductosVigentes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoRepository.findByVigenteTrue(pageable)
                .map(ProductoMapper::toResponse);
    }

    // ✅ Actualizar producto
    @Transactional
    public ProductoResponse actualizarProducto(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductoMapper.updateEntity(producto, request);
        return ProductoMapper.toResponse(productoRepository.save(producto));
    }

    // ✅ Eliminar (Soft Delete)
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setVigente(false);
        productoRepository.save(producto);
    }


    // ✅ Listar productos vigentes
    public ProductoResponse obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductoMapper.toResponse(producto);
    }

    // ==========================================================
// 🔹 PENDIENTE ITEMS
// ==========================================================
    public List<PendienteItemResponse> findItemsByPendiente(Long pendienteId) {
        List<PendienteItem> items = pendienteItemRepository.findByPendienteIdAndVigenteTrue(pendienteId);
        if (items.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron ítems para el pendienteId: " + pendienteId);
        }
        return items.stream()
                .map(PendienteItemMapper::toResponse)
                .toList();
    }

    public PendienteItemResponse createPendienteItem(PendienteItemRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + request.getProductoId()));
        PendienteItem entity = PendienteItemMapper.toEntity(request, pendiente, producto);
        return PendienteItemMapper.toResponse(pendienteItemRepository.save(entity));
    }

    public PendienteItemResponse updatePendienteItem(Long id, PendienteItemRequest request) {
        PendienteItem entity = pendienteItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ítem no encontrado con id: " + id));

        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + request.getProductoId()));

        PendienteItemMapper.updateEntity(entity, request, pendiente, producto);
        return PendienteItemMapper.toResponse(pendienteItemRepository.save(entity));
    }

    public void deletePendienteItem(Long id) {
        PendienteItem entity = pendienteItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ítem no encontrado con id: " + id));
        entity.setVigente(false);
        pendienteItemRepository.save(entity);
    }
}