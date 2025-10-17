package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {


    Page<Pago> findByVigenteTrue(Pageable pageable);

    List<Pago> findByPendienteIdAndVigenteTrue(Long pendienteId);
}
