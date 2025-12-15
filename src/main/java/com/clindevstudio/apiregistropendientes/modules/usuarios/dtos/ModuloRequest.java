package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import lombok.Data;

@Data
public class ModuloRequest {
    private String nombre;
    private String descripcion;
    private String icono;
    private String ruta;
    private Long moduloPadreId; // solo el id del padre
    private Boolean activo;
}