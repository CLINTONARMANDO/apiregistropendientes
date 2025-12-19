package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteAveria;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteInstalacionInternet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendienteAveriaRepsitory extends JpaRepository<PendienteAveria, Long> {
    Optional<PendienteAveria> findByPendienteId(Long pendienteId);

}
