package com.clindevstudio.apiregistropendientes.modules.appversion.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.AppVersion;
import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionRequest;
import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionResponse;

public class AppVersionMapper {

    // 🧱 De Request → Entidad
    public static AppVersion toEntity(AppVersionRequest request) {
        return AppVersion.builder()
                .versionCode(request.getVersionCode())
                .plataforma(request.getPlataforma())
                .obligatoria(request.isObligatoria())
                .descripcion(request.getDescripcion())
                .fechaPublicacion(request.getFechaPublicacion())
                .vigente(request.isVigente())
                .build();
    }

    // ✏️ Actualizar entidad existente
    public static void updateEntity(AppVersion appVersion, AppVersionRequest request) {
        appVersion.setVersionCode(request.getVersionCode());
        appVersion.setPlataforma(request.getPlataforma());
        appVersion.setObligatoria(request.isObligatoria());
        appVersion.setDescripcion(request.getDescripcion());
        appVersion.setFechaPublicacion(request.getFechaPublicacion());
        appVersion.setVigente(request.isVigente());
    }

    // 🔁 De Entidad → Response
    public static AppVersionResponse toResponse(AppVersion appVersion) {
        return AppVersionResponse.builder()
                .id(appVersion.getId())
                .versionCode(appVersion.getVersionCode())
                .plataforma(appVersion.getPlataforma())
                .obligatoria(appVersion.isObligatoria())
                .descripcion(appVersion.getDescripcion())
                .fechaPublicacion(appVersion.getFechaPublicacion())
                .vigente(appVersion.isVigente())
                .build();
    }
}