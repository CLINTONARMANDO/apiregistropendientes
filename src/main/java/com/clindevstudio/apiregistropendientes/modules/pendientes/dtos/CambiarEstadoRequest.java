package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import lombok.Data;

@Data
public class CambiarEstadoRequest {
    private Long pendienteId;
    private EstadoPendiente nuevoEstado;

}
