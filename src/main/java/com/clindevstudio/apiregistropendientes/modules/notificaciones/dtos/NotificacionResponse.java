package com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clindevstudio.apiregistropendientes.database.enums.NotificationEstado;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationTipo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponse {
    private Long id;
    private String titulo;
    private String mensaje;
    private NotificationTipo tipo;
    private NotificationEstado estado;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
}
