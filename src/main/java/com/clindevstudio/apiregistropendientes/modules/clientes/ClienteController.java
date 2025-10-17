package com.clindevstudio.apiregistropendientes.modules.clientes;

import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteRequest;
import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ==========================================================
    // 📋 Obtener todos los clientes
    // ==========================================================
    @GetMapping
    public TransactionResponse<Page<ClienteResponse>> obtenerClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ClienteResponse> clientes = clienteService.obtenerClientes(
                page, size
        );
        return TransactionResponseFactory.success(clientes, "Clientes obtenidos correctamente");
    }

    // ==========================================================
    // 🔹 Obtener cliente por ID
    // ==========================================================
    @GetMapping("/{id}")
    public TransactionResponse<ClienteResponse> obtenerClientePorId(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.obtenerClientePorId(id);
        return TransactionResponseFactory.success(cliente, "Cliente obtenido correctamente");
    }

    // ==========================================================
    // ✏️ Crear cliente
    // ==========================================================
    @PostMapping
    public TransactionResponse<ClienteResponse> crearCliente(@RequestBody ClienteRequest request) {
        ClienteResponse cliente = clienteService.crearCliente(request);
        return TransactionResponseFactory.success(cliente, "Cliente creado correctamente");
    }

    // ==========================================================
    // 🔄 Actualizar cliente
    // ==========================================================
    @PutMapping("/{id}")
    public TransactionResponse<ClienteResponse> actualizarCliente(
            @PathVariable Long id,
            @RequestBody ClienteRequest request
    ) {
        ClienteResponse cliente = clienteService.actualizarCliente(id, request);
        return TransactionResponseFactory.success(cliente, "Cliente actualizado correctamente");
    }

    // ==========================================================
    // ❌ Eliminación lógica
    // ==========================================================
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return TransactionResponseFactory.success(null, "Cliente eliminado correctamente");
    }
}
