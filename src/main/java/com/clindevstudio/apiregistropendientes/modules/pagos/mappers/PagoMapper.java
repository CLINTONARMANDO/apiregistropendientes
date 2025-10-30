package com.clindevstudio.apiregistropendientes.modules.pagos.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.entities.Pago;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoRequest;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoResponse;

public class PagoMapper {

    // 🔹 Convierte entidad -> Response DTO
    public static PagoResponse toResponse(Pago pago) {
        if (pago == null) return null;

        return PagoResponse.builder()
                .id(pago.getId())
                .empleadoId(pago.getEmpleado() != null ? pago.getEmpleado().getId() : null)
                .clienteId(pago.getCliente() != null ? pago.getCliente().getId() : null)
                .empleadoNombre(pago.getEmpleado() != null ? pago.getEmpleado().getNombre() : null)
                .clienteNombre(pago.getCliente() != null ? pago.getCliente().getNombre() : null)
                .pendienteId(pago.getPendiente() != null ? pago.getPendiente().getId() : null)
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .metodoPago(pago.getMetodoPago())
                .referencia(pago.getReferencia())
                .build();
    }

    // 🔹 Convierte Request -> Entidad (cuando ya tienes las relaciones)
    public static Pago toEntity(PagoRequest request, Empleado empleado, Cliente cliente, Pendiente pendiente) {
        if (request == null) return null;

        return Pago.builder()
                .empleado(empleado)
                .cliente(cliente)
                .pendiente(pendiente)
                .monto(request.getMonto())
                .fechaPago(request.getFechaPago())
                .metodoPago(request.getMetodoPago())
                .referencia(request.getReferencia())
                .build();
    }

    // 🔹 Actualiza una entidad existente con nuevos datos del request
    public static void updateEntity(Pago pago, PagoRequest request, Empleado empleado, Cliente cliente, Pendiente pendiente) {
        if (pago == null || request == null) return;

        pago.setEmpleado(empleado);
        pago.setCliente(cliente);
        pago.setPendiente(pendiente);
        pago.setMonto(request.getMonto());
        pago.setFechaPago(request.getFechaPago());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setReferencia(request.getReferencia());
    }
}
