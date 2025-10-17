package com.clindevstudio.apiregistropendientes.modules.pendientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoDispositivoRecojo;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteRecojoDispositivoRequest {
    private Long pendienteId;
    private TipoDispositivoRecojo dispositivo;
    private String motivo;
}