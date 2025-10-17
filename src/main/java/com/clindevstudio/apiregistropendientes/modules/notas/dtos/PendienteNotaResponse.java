package com.clindevstudio.apiregistropendientes.modules.notas.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendienteNotaResponse {
    private Long id;
    private Long pendienteId;
    private Long empleadoId;
    private String empleadoNombre; // opcional para UI
    private String nota;
    private EstadoPendiente estadoPendiente;
    private LocalDateTime fechaCreacion;
    private List<PendienteNotaImagenResponse> imagenes; // Lista de imágenes asociadas
}