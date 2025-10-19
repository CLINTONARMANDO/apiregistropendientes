package com.clindevstudio.apiregistropendientes.database.repositories;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.database.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDni(String dni);
    boolean existsByNombre(String nombre);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    public List<Usuario> findAllByVigente(Boolean vigente);

    boolean existsByRolId(String id);

    List<Usuario> findByRol(Rol rol);
    Optional<Usuario> findByEmpleadoIdAndVigenteTrue(Long empleadoId);
    List<Usuario> findByRol_NombreAndVigenteTrue(String rolNombre);
}
