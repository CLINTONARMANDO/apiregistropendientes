package com.clindevstudio.apiregistropendientes.modules.pagos.dtos;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.enums.MetodoPago;
import jakarta.persistence.*;
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

    private Long clienteId;          // ID del cliente asociado
    private Long pendienteId;        // ID del pendiente asociado
    private BigDecimal monto;        // Monto del pago
    private LocalDate fechaPago; // Fecha del pago
    private MetodoPago metodoPago;   // Enum con el métdo de pago
    private String referencia;       // Código o descripción de referencia
}
