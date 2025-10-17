package com.clindevstudio.apiregistropendientes.modules.pagos.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.entities.Pago;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoRequest;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoResponse;

public class PagoMapper {
    public static PagoResponse toResponse(Pago pago) {
        PagoResponse pagoResponse = new PagoResponse();
        pagoResponse.setId(pago.getId());
        pagoResponse.setFechaPago(pago.getFechaPago());
        pagoResponse.setMetodoPago(pago.getMetodoPago());
        pagoResponse.setReferencia(pago.getReferencia());
        pagoResponse.setMonto(pago.getMonto());
        pagoResponse.setClienteNombre(pago.getCliente().getNombre());
        pagoResponse.setPendienteId(pago.getPendiente().getId().toString());

        return pagoResponse;
    }

    public static Pago toEntity(PagoRequest pagoRequest, Cliente cliente, Pendiente pendiente) {
        Pago pago = new Pago();
        pago.setFechaPago(pagoRequest.getFechaPago());
        pago.setMetodoPago(pagoRequest.getMetodoPago());
        pago.setReferencia(pagoRequest.getReferencia());
        pago.setMonto(pagoRequest.getMonto());
        pago.setCliente(cliente);
        pago.setPendiente(pendiente);
        return pago;
    }

    public static Pago toEntity(PagoRequest pagoRequest, Pendiente pendiente) {
        Pago pago = new Pago();
        pago.setFechaPago(pagoRequest.getFechaPago());
        pago.setMetodoPago(pagoRequest.getMetodoPago());
        pago.setReferencia(pagoRequest.getReferencia());
        pago.setMonto(pagoRequest.getMonto());
        pago.setPendiente(pendiente);
        return pago;
    }

    public static void updateEntity(Pago pago, PagoRequest request, Cliente cliente, Pendiente pendiente) {
        pago.setCliente(cliente);
        pago.setPendiente(pendiente);
        pago.setMonto(request.getMonto());
        pago.setFechaPago(request.getFechaPago());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setReferencia(request.getReferencia());
    }

}
