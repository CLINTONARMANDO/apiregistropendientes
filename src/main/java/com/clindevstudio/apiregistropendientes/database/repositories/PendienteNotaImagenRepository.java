package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.PendienteNotaImagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendienteNotaImagenRepository extends JpaRepository<PendienteNotaImagen, Long> {
    List<PendienteNotaImagen> findByNotaId(Long notaId);

}
