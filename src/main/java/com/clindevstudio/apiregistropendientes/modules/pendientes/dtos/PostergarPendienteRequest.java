package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostergarPendienteRequest {

    private LocalDateTime nuevaFecha;

    private Long empleadoId; // ID del empleado que posterga (opcional)
}