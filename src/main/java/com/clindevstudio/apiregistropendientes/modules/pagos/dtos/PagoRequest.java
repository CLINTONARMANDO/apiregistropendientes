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
public class PagoRequest {

    private Long empleadoId;         // ID del empleado que registra el pago
    private Long clienteId;          // ID del cliente asociado
    private Long pendienteId;        // ID del pendiente relacionado
    private BigDecimal monto;        // Monto del pago
    private LocalDate fechaPago;     // Fecha en que se realizó el pago
    private MetodoPago metodoPago;   // Enum con el método de pago
    private String referencia;       // Código o nota de referencia
}
