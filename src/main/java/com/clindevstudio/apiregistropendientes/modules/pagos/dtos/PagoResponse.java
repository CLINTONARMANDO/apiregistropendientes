package com.clindevstudio.apiregistropendientes.modules.pagos.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponse {

    private Long id;
    private String clienteNombre;     // Nombre del cliente (opcional, más legible)
    private String pendienteId;   // Título o descripción del pendiente
    private BigDecimal monto;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private String referencia;
}