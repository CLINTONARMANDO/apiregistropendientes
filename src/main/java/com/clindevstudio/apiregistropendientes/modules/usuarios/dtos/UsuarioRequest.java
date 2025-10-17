package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nombre;
    private String dni;
    private String email;
    private String password;
    private Boolean activo;
    private Long idRol;
    private Long empleadoId;
}
