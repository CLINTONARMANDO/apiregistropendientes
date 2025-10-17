package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteAveriaRequest {
    private Long pendienteId;
    private String descripcionFalla;
}