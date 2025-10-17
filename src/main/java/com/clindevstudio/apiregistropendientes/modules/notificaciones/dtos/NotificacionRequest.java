package com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.NotificationEstado;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationTipo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequest {
    private String titulo;
    private String mensaje;
    private NotificationTipo tipo;
    private NotificationEstado estado;
    private Long usuarioId;
}