package com.clindevstudio.apiregistropendientes.modules.common;

public class TransactionResponseFactory {

    public static <T> TransactionResponse<T> success(T data, String message) {
        TransactionResponse<T> response = new TransactionResponse<>();
        response.setSuccess(true);
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> TransactionResponse<T> error(int code, String message) {
        TransactionResponse<T> response = new TransactionResponse<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}