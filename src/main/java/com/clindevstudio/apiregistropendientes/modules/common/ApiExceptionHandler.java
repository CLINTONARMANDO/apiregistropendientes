package com.clindevstudio.apiregistropendientes.modules.common;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TransactionResponse<Object>> handleBadRequest(IllegalArgumentException ex) {
        String mensaje = "La petición contiene parámetros inválidos";

        // 🔍 Si el mensaje interno da pista útil, la añadimos
        if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
            mensaje += ": " + ex.getMessage();
        }

        TransactionResponse<Object> response = TransactionResponseFactory.error(
                HttpStatus.BAD_REQUEST.value(),
                mensaje
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<TransactionResponse<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "El recurso solicitado no existe";
        TransactionResponse<Object> response = TransactionResponseFactory.error(
                HttpStatus.NOT_FOUND.value(),
                message
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TransactionResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        TransactionResponse<Object> response = TransactionResponseFactory.error(
                HttpStatus.BAD_REQUEST.value(),
                "El parámetro '" + paramName + "' no tiene un formato válido"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<TransactionResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        TransactionResponse<Object> response = TransactionResponseFactory.error(
                HttpStatus.FORBIDDEN.value(),
                "No tienes permisos para acceder a este recurso"
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // 👇 Handler genérico para errores desconocidos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TransactionResponse<Object>> handleUnknown(Exception ex) {

        TransactionResponse<Object> response = TransactionResponseFactory.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error desconocido: " + ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}