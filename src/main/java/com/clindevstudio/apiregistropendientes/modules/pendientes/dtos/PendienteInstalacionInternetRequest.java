package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoInstalacion;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteInstalacionInternetRequest {
    private Long pendienteId;
    private String velocidadSolicitada;
    private String costoFijo;
    private TipoInstalacion tipoInstalacion;
}