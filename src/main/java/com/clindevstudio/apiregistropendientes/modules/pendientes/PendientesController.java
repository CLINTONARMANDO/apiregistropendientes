package com.clindevstudio.apiregistropendientes.modules.pendientes;

import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pendientes")
public class PendientesController {

    private final PendienteService pendienteService;

    public PendientesController(PendienteService pendienteService) {
        this.pendienteService = pendienteService;
    }

    // ==========================================================
    // 🔹 CRUD GENERAL DE PENDIENTES
    // ==========================================================

    @PostMapping("/filtrar")
    public TransactionResponse<Page<PendienteResponse>> filtrarPendientes(
            @RequestBody FiltroPendienteRequest filtro,
            Pageable pageable
    ) {
        Page<PendienteResponse> pendientes = pendienteService.filtrarPendientes(filtro, pageable);
        return TransactionResponseFactory.success(pendientes, "Pendientes filtrados correctamente");
    }

    @PostMapping
    public TransactionResponse<PendienteResponse> crearPendiente(@RequestBody CrearPendienteRequest request) {
        PendienteResponse creado = pendienteService.crearPendiente(request);
        return TransactionResponseFactory.success(creado, "Pendiente creado correctamente");
    }

    @PutMapping("/{id}/estado")
    public TransactionResponse<PendienteResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPendiente nuevoEstado
    ) {
        PendienteResponse actualizado = pendienteService.cambiarEstado(id, nuevoEstado);
        return TransactionResponseFactory.success(actualizado, "Estado actualizado correctamente");
    }

    @PutMapping("/{id}/asignar")
    public TransactionResponse<PendienteResponse> asiganPendiente(
            @PathVariable Long id,
            @RequestParam Long idAsignado
    ) {
        PendienteResponse actualizado = pendienteService.asignarEmpleadoId(id, idAsignado);
        return TransactionResponseFactory.success(actualizado, "Estado actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarPendiente(@PathVariable Long id) {
        pendienteService.eliminarPendiente(id);
        return TransactionResponseFactory.success(null, "Pendiente eliminado correctamente");
    }

    @GetMapping("/filtros")
    public TransactionResponse<Object> obtenerFiltrosDisponibles() {
        return TransactionResponseFactory.success(
                pendienteService.obtenerFiltrosDisponibles(),
                "Filtros disponibles obtenidos correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE TRASLADO
    // ==========================================================

    @GetMapping("/{pendienteId}/traslado")
    public TransactionResponse<PendienteTrasladoResponse> obtenerTraslado(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findPendienteTraslado(pendienteId),
                "Traslado obtenido correctamente"
        );
    }

    @PostMapping("/traslado")
    public TransactionResponse<PendienteTrasladoResponse> crearTraslado(@RequestBody PendienteTrasladoRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteTraslado(request),
                "Traslado creado correctamente"
        );
    }

    @PutMapping("/traslado")
    public TransactionResponse<PendienteTrasladoResponse> actualizarTraslado(@RequestBody PendienteTrasladoRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteTraslado(request),
                "Traslado actualizado correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE INSTALACIÓN CÁMARAS
    // ==========================================================

    @GetMapping("/{pendienteId}/instalacion-camaras")
    public TransactionResponse<PendienteInstalacionCamarasResponse> obtenerInstalacionCamaras(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findPendienteInstalacionCamaras(pendienteId),
                "Instalación de cámaras obtenida correctamente"
        );
    }

    @PostMapping("/instalacion-camaras")
    public TransactionResponse<PendienteInstalacionCamarasResponse> crearInstalacionCamaras(@RequestBody PendienteInstalacionCamarasRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteInstalacionCamaras(request),
                "Instalación de cámaras creada correctamente"
        );
    }

    @PutMapping("/instalacion-camaras")
    public TransactionResponse<PendienteInstalacionCamarasResponse> actualizarInstalacionCamaras(@RequestBody PendienteInstalacionCamarasRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteInstalacionCamaras(request),
                "Instalación de cámaras actualizada correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE INSTALACIÓN INTERNET
    // ==========================================================

    @GetMapping("/{pendienteId}/instalacion-internet")
    public TransactionResponse<PendienteInstalacionInternetResponse> obtenerInstalacionInternet(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findPendienteInstalacionInternet(pendienteId),
                "Instalación de internet obtenida correctamente"
        );
    }

    @PostMapping("/instalacion-internet")
    public TransactionResponse<PendienteInstalacionInternetResponse> crearInstalacionInternet(@RequestBody PendienteInstalacionInternetRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteInstalacionInternet(request),
                "Instalación de internet creada correctamente"
        );
    }

    @PutMapping("/instalacion-internet")
    public TransactionResponse<PendienteInstalacionInternetResponse> actualizarInstalacionInternet(@RequestBody PendienteInstalacionInternetRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteInstalacionInternet(request),
                "Instalación de internet actualizada correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE AVERÍA
    // ==========================================================

    @GetMapping("/{pendienteId}/averia")
    public TransactionResponse<PendienteAveriaResponse> obtenerAveria(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findPendienteAveria(pendienteId),
                "Avería obtenida correctamente"
        );
    }

    @PostMapping("/averia")
    public TransactionResponse<PendienteAveriaResponse> crearAveria(@RequestBody PendienteAveriaRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteAveria(request),
                "Avería creada correctamente"
        );
    }

    @PutMapping("/averia")
    public TransactionResponse<PendienteAveriaResponse> actualizarAveria(@RequestBody PendienteAveriaRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteAveria(request),
                "Avería actualizada correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE RECOJO DE DISPOSITIVO
    // ==========================================================

    @GetMapping("/{pendienteId}/recojo-dispositivo")
    public TransactionResponse<PendienteRecojoDispositivoResponse> obtenerRecojoDispositivo(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findPendienteRecojoDispositivo(pendienteId),
                "Recojo de dispositivo obtenido correctamente"
        );
    }

    @PostMapping("/recojo-dispositivo")
    public TransactionResponse<PendienteRecojoDispositivoResponse> crearRecojoDispositivo(@RequestBody PendienteRecojoDispositivoRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteRecojoDispositivo(request),
                "Recojo de dispositivo creado correctamente"
        );
    }

    @PutMapping("/recojo-dispositivo")
    public TransactionResponse<PendienteRecojoDispositivoResponse> actualizarRecojoDispositivo(@RequestBody PendienteRecojoDispositivoRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteRecojoDispositivo(request),
                "Recojo de dispositivo actualizado correctamente"
        );
    }

    // ==========================================================
    // 🔹 PENDIENTE TIEMPOS
    // ==========================================================

    @GetMapping("/{pendienteId}/tiempos")
    public TransactionResponse<List<PendienteTiempoResponse>> listarTiemposPorPendiente(@PathVariable Long pendienteId) {
        return TransactionResponseFactory.success(
                pendienteService.findTiemposByPendiente(pendienteId),
                "Tiempos obtenidos correctamente"
        );
    }

    @PostMapping("/tiempos")
    public TransactionResponse<PendienteTiempoResponse> crearTiempo(@RequestBody PendienteTiempoRequest request) {
        return TransactionResponseFactory.success(
                pendienteService.createPendienteTiempo(request),
                "Tiempo registrado correctamente"
        );
    }

    @PutMapping("/tiempos/{id}")
    public TransactionResponse<PendienteTiempoResponse> actualizarTiempo(
            @PathVariable Long id,
            @RequestBody PendienteTiempoRequest request
    ) {
        return TransactionResponseFactory.success(
                pendienteService.updatePendienteTiempo(id, request),
                "Tiempo actualizado correctamente"
        );
    }

    @DeleteMapping("/tiempos/{id}")
    public TransactionResponse<Void> eliminarTiempo(@PathVariable Long id) {
        pendienteService.deletePendienteTiempo(id);
        return TransactionResponseFactory.success(null, "Tiempo eliminado correctamente");
    }
}
