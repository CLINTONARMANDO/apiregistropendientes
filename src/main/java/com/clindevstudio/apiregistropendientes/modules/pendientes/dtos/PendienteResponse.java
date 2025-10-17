
package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.PrioridadPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendienteResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaPendiente;
    private String registradoPor;  // nombre del asesor
    private String asignadoA;      // nombre del técnico
    private EstadoPendiente estado;
    private LugarPendiente lugar;
    private PrioridadPendiente prioridad;
    private TipoPendiente tipo;
    private String direccion;
    private String cliente;        // nombre o razón social del cliente
}
