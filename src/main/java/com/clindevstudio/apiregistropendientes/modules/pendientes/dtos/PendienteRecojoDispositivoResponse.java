package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoDispositivoRecojo;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteRecojoDispositivoResponse {
    private Long id;
    private Long pendienteId;
    private TipoDispositivoRecojo dispositivo;
    private String motivo;
}