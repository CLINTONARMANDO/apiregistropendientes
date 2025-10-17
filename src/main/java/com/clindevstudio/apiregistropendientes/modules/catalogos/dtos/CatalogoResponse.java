package com.clindevstudio.apiregistropendientes.modules.catalogos.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoCatalogo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoCatalogo tipo;
}