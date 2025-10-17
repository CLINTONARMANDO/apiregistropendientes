package com.clindevstudio.apiregistropendientes.modules.pagos;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoRequest;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * 📄 Listar pagos vigentes con paginación
     */
    @GetMapping
    public TransactionResponse<Page<PagoResponse>> listarPagosVigentes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PagoResponse> pagos = pagoService.listarPagosVigentes(page, size);
        return TransactionResponseFactory.success(pagos, "Pagos vigentes obtenidos correctamente");
    }

    /**
     * 💰 Crear nuevo pago
     */
    @PostMapping
    public TransactionResponse<PagoResponse> crearPago(@RequestBody PagoRequest request) {
        PagoResponse pagoCreado = pagoService.crearPago(request);
        return TransactionResponseFactory.success(pagoCreado, "Pago creado correctamente");
    }

    /**
     * ✏️ Actualizar un pago existente
     */
    @PutMapping("/{id}")
    public TransactionResponse<PagoResponse> actualizarPago(
            @PathVariable Long id,
            @RequestBody PagoRequest request
    ) {
        PagoResponse pagoActualizado = pagoService.actualizarPago(id, request);
        return TransactionResponseFactory.success(pagoActualizado, "Pago actualizado correctamente");
    }

    /**
     * ❌ Eliminar (lógico) un pago
     */
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return TransactionResponseFactory.success(null, "Pago eliminado correctamente");
    }

    /**
     * 🔍 Buscar pagos por pendiente
     */
    @GetMapping("/pendiente/{pendienteId}")
    public TransactionResponse<List<PagoResponse>> obtenerPagosPorPendiente(
            @PathVariable Long pendienteId
    ) {
        List<PagoResponse> pagos = pagoService.obtenerPagosPorPendiente(pendienteId);
        return TransactionResponseFactory.success(pagos, "Pagos del pendiente obtenidos correctamente");
    }
}
