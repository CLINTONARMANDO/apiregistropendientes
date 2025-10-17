package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PendienteRepository extends JpaRepository<Pendiente, Long>, JpaSpecificationExecutor<Pendiente> {
    Page<Pendiente> findByEstado(EstadoPendiente estado, Pageable pageable);

    Page<Pendiente> findByClienteId(Long clienteId, Pageable pageable);

    Page<Pendiente> findByClienteIdAndEstado(Long clienteId, EstadoPendiente estado, Pageable pageable);

    Page<Pendiente> findByAsignadoA_Id(Long empleadoId, Pageable pageable);

    Page<Pendiente> findByRegistradoPor_Id(Long empleadoId, Pageable pageable);
}
