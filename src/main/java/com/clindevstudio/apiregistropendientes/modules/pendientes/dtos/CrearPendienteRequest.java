package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import lombok.Data;
import java.time.LocalDateTime;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.PrioridadPendiente;

@Data
public class CrearPendienteRequest {
    private Long registradoPorId;
    private Long asignadoAId;
    private LocalDateTime fechaPendiente;
    private EstadoPendiente estado;
    private LugarPendiente lugar;
    private PrioridadPendiente prioridad;
    private TipoPendiente tipo;
    private String direccion;
    private Long clienteId;
}
