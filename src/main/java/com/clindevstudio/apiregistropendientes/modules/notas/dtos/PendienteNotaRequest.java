package com.clindevstudio.apiregistropendientes.modules.notas.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendienteNotaRequest {
    private Long pendienteId;
    private Long empleadoId;
    private String nota;
    private EstadoPendiente estadoPendiente;
    private List<PendienteNotaImagenRequest> imagenes; // Lista de imágenes asociadas
}

