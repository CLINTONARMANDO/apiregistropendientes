package com.clindevstudio.apiregistropendientes.modules.auth;


import com.clindevstudio.apiregistropendientes.modules.auth.dtos.LoginRequest;
import com.clindevstudio.apiregistropendientes.modules.auth.dtos.LoginResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.UsuarioRequest;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping
    public TransactionResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse result = authService.login(loginRequest);
        return TransactionResponseFactory.success(
                result,
                "Usuarios obtenidos correctamente"
        );
    }
    @GetMapping("/validar-token")
    public ResponseEntity<String> validarToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token no proporcionado o formato inválido");
        }

        String token = authHeader.substring(7); // Quitar "Bearer "
        authService.validarTokenYUsuarioActivo(token);

        return ResponseEntity.ok("Token válido y usuario activo");
    }
}
