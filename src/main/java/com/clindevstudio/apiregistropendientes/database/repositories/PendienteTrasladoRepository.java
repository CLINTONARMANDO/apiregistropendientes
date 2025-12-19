package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteInstalacionInternet;
import com.clindevstudio.apiregistropendientes.database.entities.PendienteTraslado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendienteTrasladoRepository extends JpaRepository<PendienteTraslado, Long> {
    Optional<PendienteTraslado> findByPendienteId(Long pendienteId);
}
