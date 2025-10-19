package com.clindevstudio.apiregistropendientes.modules.notificaciones;

import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificacionSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 🔹 Enviar notificación general
    public void enviarNotificacionGlobal(NotificacionResponse notificacion) {
        messagingTemplate.convertAndSend("/topic/notificaciones", notificacion);
    }

    // 🔹 Enviar notificación a un usuario específico
    public void enviarNotificacionUsuario(Long usuarioId, NotificacionResponse notificacion) {
        messagingTemplate.convertAndSend("/topic/usuario/" + usuarioId, notificacion);
    }
}
