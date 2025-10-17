package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendienteItemRepository extends JpaRepository<PendienteItem, Long> {
    List<PendienteItem> findByPendienteId(Long pendienteId);
    List<PendienteItem> findByPendienteIdAndVigenteTrue(Long pendienteId);
}
