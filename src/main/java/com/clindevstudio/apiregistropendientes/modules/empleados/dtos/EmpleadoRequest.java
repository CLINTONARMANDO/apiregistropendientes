package com.clindevstudio.apiregistropendientes.modules.empleados.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmpleadoRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private Long cargoId; // Referencia al ID del Cargo
    private LocalDate fechaIngreso;
}