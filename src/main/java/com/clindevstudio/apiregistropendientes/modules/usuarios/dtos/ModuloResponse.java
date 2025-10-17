package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import lombok.Data;

@Data
public class ModuloResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ruta;
    private Long moduloPadreId; // id del padre si existe
    private String moduloPadreNombre; // opcional, para mostrar en UI
    private Boolean activo;
}