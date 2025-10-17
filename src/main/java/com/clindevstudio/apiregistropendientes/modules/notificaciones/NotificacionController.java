package com.clindevstudio.apiregistropendientes.modules.notificaciones;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionRequest;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // 🔹 Crear notificación
    @PostMapping
    public TransactionResponse<NotificacionResponse> crear(@RequestBody NotificacionRequest request) {
        NotificacionResponse response = notificacionService.crearNotificacion(request);
        return TransactionResponseFactory.success(response, "Notificación creada correctamente");
    }

    // 🔹 Listar todas las vigentes
    @GetMapping
    public TransactionResponse<List<NotificacionResponse>> listarVigentes() {
        List<NotificacionResponse> notificaciones = notificacionService.listarNotificacionesVigentes();
        return TransactionResponseFactory.success(notificaciones, "Notificaciones obtenidas correctamente");
    }

    // 🔹 Listar por usuario
    @GetMapping("/usuario/{usuarioId}")
    public TransactionResponse<List<NotificacionResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionResponse> notificaciones = notificacionService.listarPorUsuario(usuarioId);
        return TransactionResponseFactory.success(notificaciones, "Notificaciones del usuario obtenidas correctamente");
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public TransactionResponse<NotificacionResponse> actualizar(
            @PathVariable Long id,
            @RequestBody NotificacionRequest request
    ) {
        NotificacionResponse response = notificacionService.actualizarNotificacion(id, request);
        return TransactionResponseFactory.success(response, "Notificación actualizada correctamente");
    }

    // 🔹 Eliminar (soft delete)
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return TransactionResponseFactory.success(null, "Notificación eliminada correctamente");
    }
}