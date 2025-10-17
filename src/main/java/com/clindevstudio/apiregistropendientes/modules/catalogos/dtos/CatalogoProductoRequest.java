package com.clindevstudio.apiregistropendientes.modules.catalogos.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoProductoRequest {
    private Long catalogoId;
    private Long productoId;
    private Integer cantidad;
}