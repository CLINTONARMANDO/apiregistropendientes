package com.clindevstudio.apiregistropendientes.modules.productos.dtos;

import java.math.BigDecimal;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteItemRequest {
    private Long pendienteId;
    private Long productoId;     // ID del producto asociado
    private String descripcion;
    private Integer cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
}