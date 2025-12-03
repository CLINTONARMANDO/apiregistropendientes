package com.clindevstudio.apiregistropendientes.modules.notificaciones;


import com.clindevstudio.apiregistropendientes.database.entities.Notificacion;
import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.database.entities.Usuario;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationEstado;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationTipo;
import com.clindevstudio.apiregistropendientes.database.enums.Permiso;
import com.clindevstudio.apiregistropendientes.database.repositories.NotificacionRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.UsuarioRepository;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionRequest;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionResponse;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.mappers.NotificacionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionSocketController notificacionSocketController;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public NotificacionService(
            NotificacionRepository notificacionRepository,
            NotificacionSocketController notificacionSocketController,
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionSocketController = notificacionSocketController;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    // 🔹 Crear notificación
    public NotificacionResponse crearNotificacion(NotificacionRequest request) {
        Notificacion notificacion = NotificacionMapper.toEntity(request);
        NotificacionResponse response = NotificacionMapper.toResponse(notificacionRepository.save(notificacion));

        // 🚀 Enviar en tiempo real
        notificacionSocketController.enviarNotificacionUsuario(request.getUsuarioId(), response);

        return response;
    }

    // 🔹 Enviar notificación a todos los usuarios de un rol
    @Transactional
    public void enviarNotificacionARol(String idRol, NotificacionRequest requestBase) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);

        if (usuarios.isEmpty()) {
            throw new RuntimeException("No se encontraron usuarios con el rol: " + idRol);
        }

        for (Usuario usuario : usuarios) {
            NotificacionRequest request = new NotificacionRequest();
            request.setTitulo(requestBase.getTitulo());
            request.setMensaje(requestBase.getMensaje());
            request.setUsuarioId(usuario.getId());
            request.setTipo(requestBase.getTipo());

            Notificacion notificacion = NotificacionMapper.toEntity(request);
            NotificacionResponse response = NotificacionMapper.toResponse(notificacionRepository.save(notificacion));

            // 🚀 Enviar en tiempo real por WebSocket
            notificacionSocketController.enviarNotificacionUsuario(usuario.getId(), response);
        }
    }

    // 🔹 Listar notificaciones vigentes
    public List<NotificacionResponse> listarNotificacionesVigentes() {
        return NotificacionMapper.toResponseList(
                notificacionRepository.findByVigenteTrue()
        );
    }

    // 🔹 Buscar por usuario (solo vigentes)
    // 🔹 Buscar por usuario con paginación y filtro opcional
    public Page<NotificacionResponse> listarPorUsuario(Long usuarioId, NotificationEstado leida, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());

        Page<Notificacion> pageResult;
        if (leida != null) {
            pageResult = notificacionRepository.findByUsuarioIdAndVigenteTrueAndEstado(usuarioId, leida, pageable);
        } else {
            pageResult = notificacionRepository.findByUsuarioIdAndVigenteTrue(usuarioId, pageable);
        }

        return pageResult.map(NotificacionMapper::toResponse);
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

    // 🔹 Enviar notificación a usuarios cuyos roles tengan un permiso específico
    @Transactional
    public void enviarNotificacionPorPermiso(Permiso permisoRequerido, NotificacionRequest requestBase) {

        long bit = permisoRequerido.getBit();

        // 1️⃣ Buscar roles que tengan ese permiso en su bitmask
        List<Rol> roles = rolRepository.findAll()
                .stream()
                .filter(r -> (r.getPermisos() & bit) != 0) // rol tiene el permiso
                .toList();

        if (roles.isEmpty()) {
            throw new RuntimeException("No existen roles con el permiso: " + permisoRequerido.name());
        }
        // 2️⃣ Buscar usuarios con esos roles
        List<Usuario> usuarios = usuarioRepository.findByRolIn(roles);

        if (usuarios.isEmpty()) {
            throw new RuntimeException("No existen usuarios con el permiso: " + permisoRequerido.name());
        }

        // 3️⃣ Crear y enviar notificación
        for (Usuario usuario : usuarios) {

            NotificacionRequest req = new NotificacionRequest();
            req.setTitulo(requestBase.getTitulo());
            req.setMensaje(requestBase.getMensaje());
            req.setTipo(requestBase.getTipo());
            req.setUsuarioId(usuario.getId());

            Notificacion notificacion = NotificacionMapper.toEntity(req);
            NotificacionResponse response =
                    NotificacionMapper.toResponse(notificacionRepository.save(notificacion));

            // 🚀 Enviar por WebSocket
            notificacionSocketController.enviarNotificacionUsuario(usuario.getId(), response);
        }
    }
}