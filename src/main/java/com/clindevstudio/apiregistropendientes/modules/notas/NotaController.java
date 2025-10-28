package com.clindevstudio.apiregistropendientes.modules.notas;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }


    // 🔹 Subir imágenes para una nota
    @PostMapping("/{notaId}/imagenes/upload")
    public TransactionResponse<List<PendienteNotaImagenResponse>> subirImagenes(
            @PathVariable Long notaId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        List<PendienteNotaImagenResponse> response = notaService.subirImagenes(notaId, files);
        return TransactionResponseFactory.success(response, "Imágenes subidas correctamente");
    }

    // 🔹 Crear nueva nota
    @PostMapping
    public TransactionResponse<PendienteNotaResponse> crearNota(
            @RequestBody PendienteNotaRequest request
    ) {
        PendienteNotaResponse response = notaService.crearNota(request);
        return TransactionResponseFactory.success(response, "Nota creada correctamente");
    }

    // 🔹 Actualizar nota existente
    @PutMapping("/{id}")
    public TransactionResponse<PendienteNotaResponse> actualizarNota(
            @PathVariable Long id,
            @RequestBody PendienteNotaRequest request
    ) {
        PendienteNotaResponse response = notaService.actualizarNota(id, request);
        return TransactionResponseFactory.success(response, "Nota actualizada correctamente");
    }

    // 🔹 Obtener una nota por ID
    @GetMapping("/{id}")
    public TransactionResponse<PendienteNotaResponse> obtenerNotaPorId(@PathVariable Long id) {
        PendienteNotaResponse response = notaService.obtenerNotaPorId(id);
        return TransactionResponseFactory.success(response, "Nota obtenida correctamente");
    }

    // 🔹 Listar notas por pendiente
    @GetMapping("/pendiente/{pendienteId}")
    public TransactionResponse<List<PendienteNotaResponse>> listarNotasPorPendiente(
            @PathVariable Long pendienteId
    ) {
        List<PendienteNotaResponse> response = notaService.listarNotasPorPendiente(pendienteId);
        return TransactionResponseFactory.success(response, "Notas obtenidas correctamente");
    }

    // 🔹 Eliminar lógicamente una nota
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarNota(@PathVariable Long id) {
        notaService.eliminarNota(id);
        return TransactionResponseFactory.success(null, "Nota eliminada correctamente");
    }

    // 🔹 Actualizar imágenes asociadas a una nota
    @PutMapping("/{notaId}/imagenes")
    public TransactionResponse<List<PendienteNotaImagenResponse>> actualizarImagenesNota(
            @PathVariable Long notaId,
            @RequestBody List<PendienteNotaImagenRequest> nuevasImagenes
    ) {
        List<PendienteNotaImagenResponse> response = notaService.actualizarImagenesNota(notaId, nuevasImagenes);
        return TransactionResponseFactory.success(response, "Imágenes actualizadas correctamente");
    }
}
