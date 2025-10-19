package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.ModuloRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRolRepository extends JpaRepository<ModuloRol, Long> {
    List<ModuloRol> findByRolId(String rolId);
}
