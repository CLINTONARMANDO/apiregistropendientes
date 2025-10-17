package com.clindevstudio.apiregistropendientes.modules.notificaciones;


import com.clindevstudio.apiregistropendientes.database.entities.Notificacion;
import com.clindevstudio.apiregistropendientes.database.repositories.NotificacionRepository;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionRequest;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionResponse;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.mappers.NotificacionMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // 🔹 Crear notificación
    public NotificacionResponse crearNotificacion(NotificacionRequest request) {
        Notificacion notificacion = NotificacionMapper.toEntity(request);
        return NotificacionMapper.toResponse(notificacionRepository.save(notificacion));
    }

    // 🔹 Listar notificaciones vigentes
    public List<NotificacionResponse> listarNotificacionesVigentes() {
        return NotificacionMapper.toResponseList(
                notificacionRepository.findByVigenteTrue()
        );
    }

    // 🔹 Buscar por usuario (solo vigentes)
    public List<NotificacionResponse> listarPorUsuario(Long usuarioId) {
        return NotificacionMapper.toResponseList(
                notificacionRepository.findByUsuarioIdAndVigenteTrue(usuarioId)
        );
    }

    // 🔹 Actualizar notificación
    @Transactional
    public NotificacionResponse actualizarNotificacion(Long id, NotificacionRequest request) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        NotificacionMapper.updateEntity(notificacion, request);
        return NotificacionMapper.toResponse(notificacionRepository.save(notificacion));
    }

    // 🔹 Soft delete
    @Transactional
    public void eliminarNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setVigente(false);
        notificacionRepository.save(notificacion);
    }
}