package com.clindevstudio.apiregistropendientes.modules.notas;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import com.clindevstudio.apiregistropendientes.modules.notas.dtos.*;
import com.clindevstudio.apiregistropendientes.modules.notas.mappers.PendienteNotaImagenMapper;
import com.clindevstudio.apiregistropendientes.modules.notas.mappers.PendienteNotaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final PendienteNotaRepository pendienteNotaRepository;
    private final PendienteNotaImagenRepository pendienteNotaImagenRepository;
    private final PendienteRepository pendienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final Path rootLocation = Paths.get("uploads/notas");

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

    @Transactional
    public List<PendienteNotaImagenResponse> subirImagenes(Long notaId, List<MultipartFile> files) {
        PendienteNota nota = pendienteNotaRepository.findById(notaId)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada con id: " + notaId));

        // Asegurar que el directorio exista
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de subida", e);
        }

        List<PendienteNotaImagen> guardadas = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty() || file.getSize() == 0) continue;

            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.isBlank()) {
                originalName = "imagen_" + UUID.randomUUID() + ".jpg";
            }

            String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(originalName);
            Path destino = rootLocation.resolve(filename);

            try {
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Error guardando archivo: " + filename, e);
            }

            String url = "/uploads/notas/" + filename;

            PendienteNotaImagen imagen = PendienteNotaImagen.builder()
                    .nota(nota)
                    .url(url)
                    .build();

            guardadas.add(pendienteNotaImagenRepository.save(imagen));
        }

        return guardadas.stream()
                .map(img -> PendienteNotaImagenResponse.builder()
                        .id(img.getId())
                        .notaId(notaId)
                        .url(img.getUrl())
                        .build())
                .toList();
    }

    // 🔹 Crear una nueva nota junto con imágenes
    @Transactional
    public PendienteNotaResponse crearNota(PendienteNotaRequest request) {
        // 1️⃣ Validar pendiente
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        // 2️⃣ Validar empleado
        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        // 3️⃣ Crear nota
        PendienteNota nota = PendienteNotaMapper.toEntity(request, pendiente, empleado);
        PendienteNota guardado = pendienteNotaRepository.save(nota);

        // 4️⃣ Guardar imágenes si existen
        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            List<PendienteNotaImagen> imagenes = request.getImagenes().stream()
                    .map(imgReq -> PendienteNotaImagen.builder()
                            .nota(guardado)
                            .url(imgReq.getUrl()) // Asegúrate de que aquí venga la URL o la ruta del archivo
                            .build())
                    .toList();
            pendienteNotaImagenRepository.saveAll(imagenes);
        }

        // 5️⃣ Devolver nota con las imágenes guardadas
        PendienteNotaResponse response = PendienteNotaMapper.toResponse(guardado);
        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            response.setImagenes(obtenerImagenesPorNota(guardado.getId()));
        }

        return response;
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


    public List<PendienteNotaImagenResponse> obtenerImagenesPorNota(Long notaId) {
        return pendienteNotaImagenRepository.findByNotaId(notaId)
                .stream()
                .map(PendienteNotaImagenMapper::toResponse)
                .collect(Collectors.toList());
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
