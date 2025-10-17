package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.PrioridadPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FiltroPendienteRequest {
    private Long clienteId;
    private Long usuarioId;
    private Long asignadoAId;
    private Long registradoPorId;
    private List<EstadoPendiente> estados;
    private LocalDateTime fechaPendiente;
    private TipoPendiente tipo;
    private PrioridadPendiente prioridad;
    private LugarPendiente lugar;
    private String titulo; // búsqueda parcial opcional
}
