package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteNota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendienteNotaRepository extends JpaRepository<PendienteNota,Long> {
    List<PendienteNota> findByPendienteIdOrderByFechaCreacionDesc(Long pendienteId);

}
