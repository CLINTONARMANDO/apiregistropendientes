package com.clindevstudio.apiregistropendientes.modules.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // <- Lombok te genera el constructor de 4 args
@NoArgsConstructor
public class TransactionResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;
}