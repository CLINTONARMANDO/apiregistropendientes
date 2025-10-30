package com.clindevstudio.apiregistropendientes.modules.historial.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialResponse {

    private Long id;
    private Long pendienteId;
    private Long empleadoId;
    private String empleadoNombre;
    private EstadoPendiente estado;
    private String detalles;
    private LocalDateTime fechaCreacion;  // tomado de BaseEntity (createdAt)
}