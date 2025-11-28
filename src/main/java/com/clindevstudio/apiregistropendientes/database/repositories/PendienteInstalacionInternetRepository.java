package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteInstalacionInternet;
import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PendienteInstalacionInternetRepository extends JpaRepository<PendienteInstalacionInternet, Long> {
    Optional<PendienteInstalacionInternet> findByPendienteId(Long pendienteId);
}
