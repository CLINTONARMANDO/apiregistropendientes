package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import java.math.BigDecimal;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteInstalacionInternetRequest {
    private Long pendienteId;
    private String velocidadSolicitada;
}