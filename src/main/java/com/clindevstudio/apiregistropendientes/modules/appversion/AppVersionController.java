package com.clindevstudio.apiregistropendientes.modules.appversion;

import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionRequest;
import com.clindevstudio.apiregistropendientes.modules.appversion.dtos.AppVersionResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/app-version")
public class AppVersionController {

    private final AppVersionService appVersionService;

    public AppVersionController(AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    /**
     * 📋 Listar versiones vigentes (paginadas)
     * Ejemplo: GET /app-version/vigentes?page=0&size=10
     */
    @GetMapping("/vigentes")
    public ResponseEntity<Page<AppVersionResponse>> listarVersionesVigentes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<AppVersionResponse> versiones = appVersionService.listarVersionesVigentes(page, size);
        return ResponseEntity.ok(versiones);
    }

    /**
     * 🆕 Crear una nueva versión de la aplicación
     * Ejemplo: POST /app-version
     * {
     *   "version": "1.0.5",
     *   "descripcion": "Corrección de errores y mejoras de rendimiento"
     * }
     */
    @PostMapping
    public ResponseEntity<AppVersionResponse> crearVersion(@RequestBody AppVersionRequest request) {
        if (request == null || request.getVersionCode() == null || request.getVersionCode().isEmpty()) {
            throw new RuntimeException("Debe proporcionar el número de versión");
        }
        AppVersionResponse nuevaVersion = appVersionService.crearVersion(request);
        return ResponseEntity.ok(nuevaVersion);
    }

    /**
     * 🔝 Obtener la última versión registrada (último insertado)
     * Ejemplo: GET /app-version/ultima
     */
    @GetMapping("/ultima")
    public ResponseEntity<AppVersionResponse> obtenerUltimaVersion() {
        AppVersionResponse ultimaVersion = appVersionService.obtenerUltimaVersion();
        return ResponseEntity.ok(ultimaVersion);
    }
}
