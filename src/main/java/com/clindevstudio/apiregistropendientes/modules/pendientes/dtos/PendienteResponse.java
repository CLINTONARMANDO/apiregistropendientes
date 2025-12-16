
package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendienteResponse {
    private Long id;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaPendiente;
    private Long idEmpleadoRegistro;
    private String registradoPor;  // nombre del asesor
    private Long idEmpleadoAsignado;
    private String asignadoA;      // nombre del técnico
    private EstadoPendiente estado;
    private EmpresaPendiente empresa;
    private LugarPendiente lugar;
    private PrioridadPendiente prioridad;
    private TipoPendiente tipo;
    private String direccion;
    private Long idCliente;
    private String cliente;        // nombre o razón social del cliente
}
