package com.clindevstudio.apiregistropendientes.modules.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String field;          // Nombre del campo (opcional)
    private String error;          // Mensaje de error
    private String code;           // Código interno del error (opcional)
    private Object rejectedValue;  // Valor que causó el error (opcional)
}