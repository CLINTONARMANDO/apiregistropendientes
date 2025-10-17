package com.clindevstudio.apiregistropendientes.modules.productos.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoResponse {
    private Long id;
    private String codigoBarra;
    private String nombre;
    private String descripcion;
    private BigDecimal costoUnitario;
}
