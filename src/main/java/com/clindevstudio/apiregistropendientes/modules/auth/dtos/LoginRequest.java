package com.clindevstudio.apiregistropendientes.modules.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String dni;
    private String password;
}
