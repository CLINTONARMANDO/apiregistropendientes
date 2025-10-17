package com.clindevstudio.apiregistropendientes.modules.productos.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequest {
    private String codigoBarra;
    private String nombre;
    private String descripcion;
    private BigDecimal costoUnitario;
}