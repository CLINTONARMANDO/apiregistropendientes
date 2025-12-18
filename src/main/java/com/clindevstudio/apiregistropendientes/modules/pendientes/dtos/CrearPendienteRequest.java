package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CrearPendienteRequest {
    private Long registradoPorId;
    private Long asignadoAId;
    private LocalDateTime fechaPendiente;
    private EstadoPendiente estado;
    private EstadoTecnico estadoTecnico;
    private EmpresaPendiente empresa;
    private LugarPendiente lugar;
    private PrioridadPendiente prioridad;
    private TipoPendiente tipo;
    private String direccion;
    private Long clienteId;
}
