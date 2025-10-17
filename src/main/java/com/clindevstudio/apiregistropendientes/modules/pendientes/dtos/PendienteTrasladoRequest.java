package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoServicio;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteTrasladoRequest {
    private Long pendienteId;
    private LugarPendiente lugarDestino;
    private String direccionDestino;
    private TipoServicio tipoServicio;
}