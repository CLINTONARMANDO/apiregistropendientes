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
public class AppVersionRequest {

    private String versionCode;           // Ejemplo: "1.2.3"
    private String plataforma;            // Ejemplo: "Android" o "iOS"
    private boolean obligatoria;          // Si fuerza actualización
    private String descripcion;           // Mensaje o notas de versión
    private LocalDateTime fechaPublicacion; // Fecha de lanzamiento
    private boolean vigente;              // Si está activa
}