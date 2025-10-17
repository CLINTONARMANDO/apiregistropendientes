package com.clindevstudio.apiregistropendientes.modules.appversion;


import com.clindevstudio.apiregistropendientes.database.entities.AppVersion;
import com.clindevstudio.apiregistropendientes.database.repositories.AppVersionRepository;
import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionRequest;
import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionResponse;
import com.clindevstudio.apiregistropendientes.modules.appversion.mappers.AppVersionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppVersionService {

    private final AppVersionRepository appVersionRepository;

    public AppVersionService(AppVersionRepository appVersionRepository) {
        this.appVersionRepository = appVersionRepository;
    }

    /**
     * 📋 Listar versiones vigentes con paginación
     */
    public Page<AppVersionResponse> listarVersionesVigentes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return appVersionRepository.findByVigenteTrue(pageable)
                .map(AppVersionMapper::toResponse);
    }

    /**
     * 🆕 Crear una nueva versión de la aplicación
     */
    @Transactional
    public AppVersionResponse crearVersion(AppVersionRequest request) {
        AppVersion appVersion = AppVersionMapper.toEntity(request);
        appVersion.setVigente(true);
        return AppVersionMapper.toResponse(appVersionRepository.save(appVersion));
    }

    public AppVersionResponse obtenerUltimaVersion() {
        AppVersion ultima = appVersionRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No existen versiones registradas"));
        return AppVersionMapper.toResponse(ultima);
    }
}
