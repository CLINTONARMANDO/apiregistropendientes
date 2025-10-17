package com.clindevstudio.apiregistropendientes.modules.pagos;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.entities.Pago;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.repositories.CargoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.ClienteRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.PagoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.PendienteRepository;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoRequest;
import com.clindevstudio.apiregistropendientes.modules.pagos.dtos.PagoResponse;
import com.clindevstudio.apiregistropendientes.modules.pagos.mappers.PagoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;
    private final PendienteRepository pendienteRepository;

    public PagoService(PagoRepository pagoRepository, ClienteRepository clienteRepository, PendienteRepository pendienteRepository) {
        this.pagoRepository = pagoRepository;
        this.clienteRepository = clienteRepository;
        this.pendienteRepository = pendienteRepository;
    }

    /**
     * 📄 Listar pagos vigentes con paginación
     */
    public Page<PagoResponse> listarPagosVigentes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pagoRepository.findByVigenteTrue(pageable)
                .map(PagoMapper::toResponse);
    }

    /**
     * 💰 Crear nuevo pago
     */
    @Transactional
    public PagoResponse crearPago(PagoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado"));

        Pago pago = PagoMapper.toEntity(request, cliente, pendiente);
        pago.setVigente(true);

        return PagoMapper.toResponse(pagoRepository.save(pago));
    }

    /**
     * ✏️ Actualizar pago existente
     */
    @Transactional
    public PagoResponse actualizarPago(Long id, PagoRequest request) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado"));

        PagoMapper.updateEntity(pago, request, cliente, pendiente);
        return PagoMapper.toResponse(pagoRepository.save(pago));
    }

    /**
     * ❌ Eliminación lógica: marca el pago como no vigente
     */
    @Transactional
    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setVigente(false);
        pagoRepository.save(pago);
    }

    /**
     * 🔍 Buscar pagos por pendiente (solo vigentes)
     */
    public List<PagoResponse> obtenerPagosPorPendiente(Long pendienteId) {
        return pagoRepository.findByPendienteIdAndVigenteTrue(pendienteId)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }
}