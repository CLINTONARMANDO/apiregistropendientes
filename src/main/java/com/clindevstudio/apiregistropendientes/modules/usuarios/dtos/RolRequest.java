package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RolRequest {
    private String id;
    private String nombre;
    private Long permisos = 0L;
    private String descripcion;
}
