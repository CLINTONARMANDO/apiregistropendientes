package com.clindevstudio.apiregistropendientes.modules.usuarios.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.RolRequest;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.RolResponse;

public class RolMapper {
    public static RolResponse toResponse(Rol rol) {
        RolResponse rolResponse = new RolResponse();
        rolResponse.setId(rol.getId());
        rolResponse.setNombre(rol.getNombre());
        rolResponse.setDescripcion(rol.getDescripcion());
        return rolResponse;
    }
    public static Rol toEntity(RolRequest rolRequest) {
        Rol rol = new Rol();
        rol.setId(rolRequest.getId());
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        return rol;
    }
}
