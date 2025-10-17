package com.clindevstudio.apiregistropendientes.modules.notas.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendienteNotaImagenRequest {
    private Long notaId;
    private String url;
}