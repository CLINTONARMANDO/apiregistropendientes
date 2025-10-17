package com.clindevstudio.apiregistropendientes.modules.productos.dtos;

import java.math.BigDecimal;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteItemResponse {
    private Long id;
    private Long pendienteId;
    private Long productoId;
    private String productoNombre; // opcional: útil para mostrar en UI
    private Integer cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
}