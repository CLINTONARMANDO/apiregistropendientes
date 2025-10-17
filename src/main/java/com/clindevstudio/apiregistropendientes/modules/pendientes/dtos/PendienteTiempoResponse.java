package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteTiempoResponse {
    private Long id;
    private Long pendienteId;
    private Long empleadoId;
    private String empleadoNombre; // opcional para UI
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String descripcion;
}