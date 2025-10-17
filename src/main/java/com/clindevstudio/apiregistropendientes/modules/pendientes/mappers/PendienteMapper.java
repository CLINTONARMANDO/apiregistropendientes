package com.clindevstudio.apiregistropendientes.modules.pendientes.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.CrearPendienteRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.PendienteResponse;

public class PendienteMapper {

    public static Pendiente toEntity(CrearPendienteRequest crearPendienteRequest, Cliente cliente, Empleado empleadoRegistro, Empleado empleadoAsignacion) {
        Pendiente pendiente = new Pendiente();
        pendiente.setTitulo(crearPendienteRequest.getTitulo());
        pendiente.setDescripcion(crearPendienteRequest.getDescripcion());
        pendiente.setFechaPendiente(crearPendienteRequest.getFechaPendiente());
        pendiente.setEstado(crearPendienteRequest.getEstado());
        pendiente.setTipo(crearPendienteRequest.getTipo());
        pendiente.setCliente(cliente);
        pendiente.setRegistradoPor(empleadoRegistro);
        pendiente.setAsignadoA(empleadoAsignacion);

        // Nuevos campos
        pendiente.setDireccion(crearPendienteRequest.getDireccion());
        pendiente.setLugar(crearPendienteRequest.getLugar());
        pendiente.setPrioridad(crearPendienteRequest.getPrioridad());

        return pendiente;
    }

    public static Pendiente toEntity(CrearPendienteRequest crearPendienteRequest, Cliente cliente, Empleado empleadoRegistro) {
        Pendiente pendiente = new Pendiente();
        pendiente.setTitulo(crearPendienteRequest.getTitulo());
        pendiente.setDescripcion(crearPendienteRequest.getDescripcion());
        pendiente.setFechaPendiente(crearPendienteRequest.getFechaPendiente());
        pendiente.setEstado(crearPendienteRequest.getEstado());
        pendiente.setTipo(crearPendienteRequest.getTipo());
        pendiente.setCliente(cliente);
        pendiente.setRegistradoPor(empleadoRegistro);
        pendiente.setAsignadoA(null);

        // Nuevos campos
        pendiente.setDireccion(crearPendienteRequest.getDireccion());
        pendiente.setLugar(crearPendienteRequest.getLugar());
        pendiente.setPrioridad(crearPendienteRequest.getPrioridad());

        return pendiente;
    }

    public static PendienteResponse toResponse(Pendiente pendiente) {
        PendienteResponse response = new PendienteResponse();
        response.setId(pendiente.getId());
        response.setTitulo(pendiente.getTitulo());
        response.setDescripcion(pendiente.getDescripcion());
        response.setFechaCreacion(pendiente.getFechaCreacion());
        response.setFechaPendiente(pendiente.getFechaPendiente());
        response.setEstado(pendiente.getEstado());
        response.setTipo(pendiente.getTipo());
        response.setLugar(pendiente.getLugar());
        response.setPrioridad(pendiente.getPrioridad());
        response.setDireccion(pendiente.getDireccion());
        response.setCliente(pendiente.getCliente() != null ? pendiente.getCliente().getNombre() : null);

        response.setRegistradoPor(pendiente.getRegistradoPor() != null
                ? pendiente.getRegistradoPor().getNombre() + " " + pendiente.getRegistradoPor().getApellidoPaterno()
                : null);

        response.setAsignadoA(pendiente.getAsignadoA() != null
                ? pendiente.getAsignadoA().getNombre() + " " + pendiente.getAsignadoA().getApellidoPaterno()
                : null);

        return response;
    }
}
