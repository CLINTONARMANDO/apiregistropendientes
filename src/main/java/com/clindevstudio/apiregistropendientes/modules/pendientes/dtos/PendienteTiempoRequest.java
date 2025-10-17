package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteTiempoRequest {
    private Long pendienteId;
    private Long empleadoId;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String descripcion;
}