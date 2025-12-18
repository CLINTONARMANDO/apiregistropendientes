package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoInstalacion;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteInstalacionInternetResponse {
    private Long id;
    private Long pendienteId;
    private String velocidadSolicitada;
    private String costoFijo;
    private TipoInstalacion tipoInstalacion;
    private String vlan;
    private String ppoe;
    private String ppoePassword;
}