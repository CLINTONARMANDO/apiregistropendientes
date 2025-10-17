package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion,Long> {

    List<Notificacion> findByVigenteTrue();
    List<Notificacion> findByUsuarioIdAndVigenteTrue(Long usuarioId);
}
