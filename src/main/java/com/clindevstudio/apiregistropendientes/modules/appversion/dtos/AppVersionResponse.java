package com.clindevstudio.apiregistropendientes.modules.appversion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppVersionResponse {

    private Long id;
    private String versionCode;
    private String plataforma;
    private boolean obligatoria;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private boolean vigente;
}