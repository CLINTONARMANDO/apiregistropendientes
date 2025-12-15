package com.clindevstudio.apiregistropendientes.modules.clientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteRequest;
import com.clindevstudio.apiregistropendientes.modules.clientes.dtos.ClienteResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {

    // ==========================================================
    // 🏗️ De Request → Entity (crear nuevo)
    // ==========================================================
    public static Cliente toEntity(ClienteRequest request) {
        if (request == null) return null;

        return Cliente.builder()
                .tipoCliente(request.getTipoCliente())
                .nombre(request.getNombre())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .codigoCliente(request.getCodigoCliente())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contactoRepresentante(request.getContactoRepresentante())
                .build();
    }

    // ==========================================================
    // 🔄 Actualizar entidad existente con Request
    // ==========================================================
    public static void updateEntity(Cliente entity, ClienteRequest request) {
        if (entity == null || request == null) return;

        entity.setTipoCliente(request.getTipoCliente());
        entity.setNombre(request.getNombre());
        entity.setTipoDocumento(request.getTipoDocumento());
        entity.setNumeroDocumento(request.getNumeroDocumento());
        entity.setCodigoCliente(request.getCodigoCliente());
        entity.setDireccion(request.getDireccion());
        entity.setTelefono(request.getTelefono());
        entity.setEmail(request.getEmail());
        entity.setContactoRepresentante(request.getContactoRepresentante());
    }

    // ==========================================================
    // 📤 De Entity → Response
    // ==========================================================
    public static ClienteResponse toResponse(Cliente entity) {
        if (entity == null) return null;

        return ClienteResponse.builder()
                .id(entity.getId())
                .tipoCliente(entity.getTipoCliente())
                .nombre(entity.getNombre())
                .tipoDocumento(entity.getTipoDocumento())
                .numeroDocumento(entity.getNumeroDocumento())
                .codigoCliente(entity.getCodigoCliente())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .contactoRepresentante(entity.getContactoRepresentante())
                .build();
    }

    // ==========================================================
    // 📦 Lista de Entities → Lista de Responses
    // ==========================================================
    public static List<ClienteResponse> toResponseList(List<Cliente> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());
    }
}
