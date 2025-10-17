package com.clindevstudio.apiregistropendientes.modules.usuarios.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Modulo;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.ModuloRequest;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.ModuloResponse;


public class ModuloMapper {
    // Request → Entity
    public static Modulo toEntity(ModuloRequest request, Modulo moduloPadre) {
        Modulo modulo = new Modulo();
        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setRuta(request.getRuta());
        modulo.setVigente(request.getActivo() != null ? request.getActivo() : true);
        modulo.setModuloPadre(moduloPadre);
        return modulo;
    }
    public static Modulo toEntity(ModuloRequest request) {
        Modulo modulo = new Modulo();
        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setRuta(request.getRuta());
        modulo.setVigente(request.getActivo() != null ? request.getActivo() : true);
        modulo.setModuloPadre(null);
        return modulo;
    }
    // Entity → Response
    public static ModuloResponse toResponse(Modulo modulo) {
        ModuloResponse response = new ModuloResponse();
        response.setId(modulo.getId());
        response.setNombre(modulo.getNombre());
        response.setDescripcion(modulo.getDescripcion());
        response.setRuta(modulo.getRuta());
        response.setActivo(modulo.getVigente());

        if (modulo.getModuloPadre() != null) {
            response.setModuloPadreId(modulo.getModuloPadre().getId());
            response.setModuloPadreNombre(modulo.getModuloPadre().getNombre());
        }

        return response;
    }

    // Update Entity desde Request
    public static void updateEntity(Modulo modulo, ModuloRequest request, Modulo moduloPadre) {
        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setRuta(request.getRuta());
        modulo.setVigente(request.getActivo() != null ? request.getActivo() : modulo.getVigente());
        modulo.setModuloPadre(moduloPadre);
    }
}

