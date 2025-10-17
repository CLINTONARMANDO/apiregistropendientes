package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteTiempo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendienteTiempoRepository extends JpaRepository<PendienteTiempo, Long> {
    List<PendienteTiempo> findByPendienteId(Long pendienteId);
    List<PendienteTiempo> findByPendienteIdAndVigenteTrue(Long pendienteId);
}
