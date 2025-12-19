package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteAveriaResponse {
    private Long id;
    private Long pendienteId;
    private String descripcionFalla;
    private String vlan;
    private String ppoe;
    private String ppoePassword;
}