package com.clindevstudio.apiregistropendientes.modules.usuarios.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.database.entities.Usuario;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.UsuarioRequest;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.UsuarioResponse;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario){
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNombre(usuario.getNombre());
        usuarioResponse.setDni(usuario.getDni());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setActivo(usuario.getVigente());
        if (usuario.getRol() != null) {
            usuarioResponse.setRol(new UsuarioResponse.RolData(
                    usuario.getRol().getId(),
                    usuario.getRol().getNombre()
            ));
        }

        if (usuario.getEmpleado() != null) {
            usuarioResponse.setEmpleado(new UsuarioResponse.EmpleadoData(
                    usuario.getEmpleado().getId(),
                    usuario.getEmpleado().getNombre()
            ));
        }
        return usuarioResponse;
    }

    public static Usuario toEntity(UsuarioRequest usuarioRequest, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setDni(usuarioRequest.getDni());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setVigente(usuarioRequest.getActivo());
        usuario.setRol(rol);
        usuario.setPassword(usuarioRequest.getPassword());
        return usuario;
    }
}
