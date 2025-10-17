package com.clindevstudio.apiregistropendientes.modules.catalogos.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoProductoResponse {
    private Long id;
    private Long catalogoId;
    private String catalogoNombre;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
}