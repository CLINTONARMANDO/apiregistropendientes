package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Notificacion;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationEstado;
import com.clindevstudio.apiregistropendientes.database.enums.NotificationTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion,Long> {

    List<Notificacion> findByVigenteTrue();

    // 🔹 Paginado y filtrado por usuario, estado de lectura y vigencia
    Page<Notificacion> findByUsuarioIdAndVigenteTrueAndEstado(Long usuarioId, NotificationEstado leida, Pageable pageable);

    // 🔹 Si no se pasa el filtro de lectura, usar este
    Page<Notificacion> findByUsuarioIdAndVigenteTrue(Long usuarioId, Pageable pageable);
}
