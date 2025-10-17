package com.clindevstudio.apiregistropendientes.modules.empleados.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}