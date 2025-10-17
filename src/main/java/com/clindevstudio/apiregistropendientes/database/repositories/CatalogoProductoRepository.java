package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.CatalogoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {

    List<CatalogoProducto> findByCatalogoIdAndVigenteTrue(Long catalogoId);
    Optional<CatalogoProducto> findByIdAndVigenteTrue(Long id);
}
