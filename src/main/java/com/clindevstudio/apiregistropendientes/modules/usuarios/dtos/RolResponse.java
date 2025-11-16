package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class RolResponse {
    private String id;
    private String nombre;
    private Long permisos;
    private String descripcion;
}
