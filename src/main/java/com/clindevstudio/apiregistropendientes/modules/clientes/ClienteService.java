package com.clindevstudio.apiregistropendientes.modules.clientes;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.repositories.ClienteRepository;
import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteRequest;
import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteResponse;
import com.clindevstudio.apiregistropendientes.modules.clientes.mappers.ClienteMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ==========================================================
    // 📋 Obtener todos los clientes vigentes
    // ==========================================================
    public Page<ClienteResponse> obtenerClientes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientes = clienteRepository.findByVigenteTrue(pageable);
        return clientes.map(ClienteMapper::toResponse);
    }

    // ==========================================================
    // 🔹 Obtener cliente por ID
    // ==========================================================
    public ClienteResponse obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        return ClienteMapper.toResponse(cliente);
    }

    // ==========================================================
    // ✏️ Crear cliente
    // ==========================================================
    public ClienteResponse crearCliente(ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponse(saved);
    }

    // ==========================================================
    // 🔄 Actualizar cliente
    // ==========================================================
    public ClienteResponse actualizarCliente(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        ClienteMapper.updateEntity(cliente, request);
        Cliente updated = clienteRepository.save(cliente);
        return ClienteMapper.toResponse(updated);
    }

    // ==========================================================
    // ❌ Eliminación lógica
    // ==========================================================
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        cliente.setVigente(false);
        clienteRepository.save(cliente);
    }
}
