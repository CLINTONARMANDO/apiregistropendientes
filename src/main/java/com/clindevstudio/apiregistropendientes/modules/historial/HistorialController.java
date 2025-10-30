package com.clindevstudio.apiregistropendientes.modules.historial;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialRequest;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/historiales")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    // 🔹 Crear un nuevo historial (por ejemplo, cambio de estado o acción)
    @PostMapping
    public TransactionResponse<HistorialResponse> crearHistorial(
            @RequestBody HistorialRequest request
    ) {
        HistorialResponse response = historialService.crearHistorial(request);
        return TransactionResponseFactory.success(response, "Historial creado correctamente");
    }

    // 🔹 Obtener todos los historiales de un pendiente
    @GetMapping("/pendiente/{pendienteId}")
    public TransactionResponse<List<HistorialResponse>> obtenerPorPendiente(
            @PathVariable Long pendienteId
    ) {
        List<HistorialResponse> response = historialService.buscarPorPendiente(pendienteId);
        return TransactionResponseFactory.success(response, "Historiales obtenidos correctamente");
    }
}