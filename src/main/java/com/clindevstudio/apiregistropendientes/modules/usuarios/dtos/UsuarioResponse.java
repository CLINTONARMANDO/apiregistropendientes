package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String dni;
    private String email;
    private String password;
    private Boolean activo;

    // Cambiado a par id/nombre
    private RolData rol;
    private EmpleadoData empleado;

    @Data
    public static class RolData {
        private String id;
        private String nombre;
        private Long permisos;

        public RolData(String id, String nombre, Long permisos) {
            this.id = id;
            this.nombre = nombre;
            this.permisos = permisos;
        }
    }

    @Data
    public static class EmpleadoData {
        private Long id;
        private String nombre;

        public EmpleadoData(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
    }
}
