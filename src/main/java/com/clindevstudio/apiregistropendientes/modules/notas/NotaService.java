package com.clindevstudio.apiregistropendientes.modules.notas;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.*;
import com.clindevstudio.apiregistropendientes.modules.notas.mappers.PendienteNotaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final PendienteNotaRepository pendienteNotaRepository;
    private final PendienteNotaImagenRepository pendienteNotaImagenRepository;
    private final PendienteRepository pendienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public NotaService(
            PendienteNotaRepository pendienteNotaRepository,
            PendienteNotaImagenRepository pendienteNotaImagenRepository,
            PendienteRepository pendienteRepository,
            EmpleadoRepository empleadoRepository
    ) {
        this.pendienteNotaRepository = pendienteNotaRepository;
        this.pendienteNotaImagenRepository = pendienteNotaImagenRepository;
        this.pendienteRepository = pendienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // 🔹 Crear una nueva nota
    @Transactional
    public PendienteNotaResponse crearNota(PendienteNotaRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        PendienteNota nota = PendienteNotaMapper.toEntity(request, pendiente, empleado);
        PendienteNota guardado = pendienteNotaRepository.save(nota);

        return PendienteNotaMapper.toResponse(guardado);
    }

    // 🔹 Actualizar una nota existente
    @Transactional
    public PendienteNotaResponse actualizarNota(Long id, PendienteNotaRequest request) {
        PendienteNota nota = pendienteNotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada con id: " + id));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        PendienteNotaMapper.updateEntity(nota, request, empleado);

        PendienteNota actualizada = pendienteNotaRepository.save(nota);
        return PendienteNotaMapper.toResponse(actualizada);
    }

    // 🔹 Obtener una nota por su ID
    @Transactional(readOnly = true)
    public PendienteNotaResponse obtenerNotaPorId(Long id) {
        PendienteNota nota = pendienteNotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada con id: " + id));
        return PendienteNotaMapper.toResponse(nota);
    }

    // 🔹 Listar todas las notas de un pendiente
    @Transactional(readOnly = true)
    public List<PendienteNotaResponse> listarNotasPorPendiente(Long pendienteId) {
        List<PendienteNota> notas = pendienteNotaRepository.findByPendienteIdOrderByFechaCreacionDesc(pendienteId);
        return PendienteNotaMapper.toResponseList(notas);
    }

    // 🔹 Eliminar lógicamente una nota (marcar como no vigente)
    @Transactional
    public void eliminarNota(Long id) {
        PendienteNota nota = pendienteNotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada con id: " + id));
        nota.setVigente(false);
        pendienteNotaRepository.save(nota);
    }

    // 🔹 Actualizar imágenes asociadas a una nota
    @Transactional
    public List<PendienteNotaImagenResponse> actualizarImagenesNota(Long notaId, List<PendienteNotaImagenRequest> nuevasImagenes) {
        PendienteNota nota = pendienteNotaRepository.findById(notaId)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada con id: " + notaId));

        // Imágenes actuales en BD
        List<PendienteNotaImagen> actuales = pendienteNotaImagenRepository.findByNotaId(notaId);

        // URLs actuales y nuevas
        Set<String> urlsActuales = actuales.stream()
                .map(PendienteNotaImagen::getUrl)
                .collect(Collectors.toSet());

        Set<String> urlsNuevas = nuevasImagenes.stream()
                .map(PendienteNotaImagenRequest::getUrl)
                .collect(Collectors.toSet());

        // 🔸 1. Eliminar imágenes que ya no están
        List<PendienteNotaImagen> aEliminar = actuales.stream()
                .filter(img -> !urlsNuevas.contains(img.getUrl()))
                .collect(Collectors.toList());

        pendienteNotaImagenRepository.deleteAll(aEliminar);

        // 🔸 2. Agregar nuevas imágenes
        List<PendienteNotaImagen> aAgregar = nuevasImagenes.stream()
                .filter(req -> !urlsActuales.contains(req.getUrl()))
                .map(req -> PendienteNotaImagen.builder()
                        .nota(nota)
                        .url(req.getUrl())
                        .build())
                .collect(Collectors.toList());

        pendienteNotaImagenRepository.saveAll(aAgregar);

        // 🔸 3. Devolver la lista actualizada
        List<PendienteNotaImagen> actualizadas = pendienteNotaImagenRepository.findByNotaId(notaId);
        return actualizadas.stream()
                .map(img -> PendienteNotaImagenResponse.builder()
                        .id(img.getId())
                        .notaId(notaId)
                        .url(img.getUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
