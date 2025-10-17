package com.clindevstudio.apiregistropendientes.modules.empleados.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmpleadoResponse {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String cargo; // DTO anidado
    private LocalDate fechaIngreso;
}
