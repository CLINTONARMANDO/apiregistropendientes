package com.clindevstudio.apiregistropendientes.modules.historial;

import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.entities.Historial;
import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.repositories.EmpleadoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.HistorialRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.PendienteRepository;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialRequest;
import com.clindevstudio.apiregistropendientes.modules.historial.dtos.HistorialResponse;
import com.clindevstudio.apiregistropendientes.modules.historial.mappers.HistorialMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HistorialService {

    private final HistorialRepository historialRepository;
    private final PendienteRepository pendienteRepository;
    private final EmpleadoRepository empleadoRepository;

    // 🔹 Crear un nuevo historial
    public HistorialResponse crearHistorial(HistorialRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con ID: " + request.getPendienteId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + request.getEmpleadoId()));

        Historial historial = HistorialMapper.toEntity(request, pendiente, empleado);
        historial = historialRepository.save(historial);

        return HistorialMapper.toResponse(historial);
    }

    // 🔹 Obtener todos los historiales
    @Transactional(readOnly = true)
    public List<HistorialResponse> listarTodos() {
        return historialRepository.findAll().stream()
                .map(HistorialMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Buscar historial por ID
    @Transactional(readOnly = true)
    public HistorialResponse obtenerPorId(Long id) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado con ID: " + id));

        return HistorialMapper.toResponse(historial);
    }

    // 🔹 Buscar historiales por pendiente
    @Transactional(readOnly = true)
    public List<HistorialResponse> buscarPorPendiente(Long pendienteId) {
        List<Historial> historiales = historialRepository.findByPendienteIdOrderByFechaCreacionDesc(pendienteId);

        if (historiales.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron historiales para el pendiente con ID: " + pendienteId);
        }

        return historiales.stream()
                .map(HistorialMapper::toResponse)
                .collect(Collectors.toList());
    }
}
