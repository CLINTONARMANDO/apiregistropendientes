package com.clindevstudio.apiregistropendientes.modules.pagos.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponse {

    private Long id;
    private Long empleadoId;         // ID del empleado que registra el pago
    private Long clienteId;          // ID del cliente asociado
    private String empleadoNombre;   // Nombre del empleado que registró el pago
    private String clienteNombre;    // Nombre del cliente
    private Long pendienteId;        // ID del pendiente
    private BigDecimal monto;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private String referencia;
}
