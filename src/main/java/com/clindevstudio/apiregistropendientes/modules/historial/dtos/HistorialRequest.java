package com.clindevstudio.apiregistropendientes.modules.historial.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialRequest {

    private Long pendienteId;              // ID del pendiente asociado
    private Long empleadoId;               // ID del empleado que realiza la acción
    private EstadoPendiente estado;        // Estado al que cambia el pendiente
    private String detalles;               // Observación o descripción del cambio
}