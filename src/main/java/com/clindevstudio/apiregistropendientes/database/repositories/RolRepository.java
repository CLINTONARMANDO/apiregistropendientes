package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, String> {
    public Optional<Rol> findByNombre(String rolName);
    public List<Rol> findAllByVigente(Boolean vigente);
}
