package com.clindevstudio.apiregistropendientes.modules.pendientes;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import com.clindevstudio.apiregistropendientes.database.specifications.PendienteSpecification;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.*;
import com.clindevstudio.apiregistropendientes.modules.pendientes.mappers.*;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemRequest;
import com.clindevstudio.apiregistropendientes.modules.productos.dtos.PendienteItemResponse;
import com.clindevstudio.apiregistropendientes.modules.productos.mappers.PendienteItemMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PendienteService {

    private final PendienteRepository pendienteRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PendienteTiempoRepository pendienteTiempoRepository;
    private final PendienteTrasladoRepository pendienteTrasladoRepository;
    private final PendienteInstalacionCamarasRepository pendienteInstalacionCamarasRepository;
    private final PendienteInstalacionInternetRepository pendienteInstalacionInternetRepository;
    private final PendienteAveriaRepsitory pendienteAveriaRepsitory;
    private final PendienteRecojoDispositivoRepository pendienteRecojoDispositivoRepository;


    // ✅ Constructor injection (sin @Autowired en campos)
    public PendienteService(
            PendienteRepository pendienteRepository,
            ClienteRepository clienteRepository,
            EmpleadoRepository empleadoRepository,
            PendienteTiempoRepository pendienteTiempoRepository,
            PendienteTrasladoRepository pendienteTrasladoRepository,
            PendienteInstalacionCamarasRepository pendienteInstalacionCamarasRepository,
            PendienteInstalacionInternetRepository pendienteInstalacionInternetRepository,
            PendienteAveriaRepsitory pendienteAveriaRepsitory,
            PendienteRecojoDispositivoRepository pendienteRecojoDispositivoRepository
    ) {
        this.pendienteRepository = pendienteRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
        this.pendienteTiempoRepository = pendienteTiempoRepository;
        this.pendienteTrasladoRepository = pendienteTrasladoRepository;
        this.pendienteInstalacionCamarasRepository = pendienteInstalacionCamarasRepository;
        this.pendienteInstalacionInternetRepository = pendienteInstalacionInternetRepository;
        this.pendienteAveriaRepsitory = pendienteAveriaRepsitory;
        this.pendienteRecojoDispositivoRepository = pendienteRecojoDispositivoRepository;
    }

    public Page<PendienteResponse> filtrarPendientes(FiltroPendienteRequest filtro, Pageable pageable) {
        Specification<Pendiente> spec = PendienteSpecification.conFiltros(filtro)
                .and((root, query, cb) -> cb.equal(root.get("vigente"), true));
        return pendienteRepository.findAll(spec, pageable)
                .map(PendienteMapper::toResponse);
    }

    // --- Función para devolver todos los enums disponibles ---
    public Map<String, Object> obtenerFiltrosDisponibles() {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("estados", EstadoPendiente.values());
        filtros.put("tipos", TipoPendiente.values());
        return filtros;
    }

    public PendienteResponse cambiarEstado(Long pendienteId, EstadoPendiente nuevoEstado) {
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + pendienteId));

        pendiente.setEstado(nuevoEstado);
        return PendienteMapper.toResponse(pendienteRepository.save(pendiente));
    }

    public PendienteResponse crearPendiente(CrearPendienteRequest crearPendienteRequest) {
        // Puedes inicializar valores por defecto aquí
        Cliente cliente = clienteRepository.findById(crearPendienteRequest.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + crearPendienteRequest.getClienteId()));
        Empleado empleado = empleadoRepository.findById(crearPendienteRequest.getRegistradoPorId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + crearPendienteRequest.getAsignadoAId()));
        Pendiente pendiente = PendienteMapper.toEntity(crearPendienteRequest, cliente, empleado);
        if (pendiente.getEstado() == null) {
            pendiente.setEstado(EstadoPendiente.REGISTRADO); // Estado inicial por defecto
        }
        return PendienteMapper.toResponse(pendienteRepository.save(pendiente));
    }

    public void eliminarPendiente(Long id) {
        Pendiente pendiente = pendienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + id));

        pendiente.setVigente(false);
        pendienteRepository.save(pendiente);
    }

    // ==========================================================
    // 🔹 PENDIENTE TRASLADO
    // ==========================================================

    public PendienteTrasladoResponse findPendienteTraslado(Long pendienteId) {
        PendienteTraslado entity = pendienteTrasladoRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Traslado no encontrado con pendienteId: " + pendienteId));
        return PendienteTrasladoMapper.toResponse(entity);
    }

    public PendienteTrasladoResponse createPendienteTraslado(PendienteTrasladoRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteTraslado entity = PendienteTrasladoMapper.toEntity(request, pendiente);
        return PendienteTrasladoMapper.toResponse(pendienteTrasladoRepository.save(entity));
    }

    public PendienteTrasladoResponse updatePendienteTraslado(PendienteTrasladoRequest request) {
        PendienteTraslado entity = pendienteTrasladoRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Traslado no encontrado con pendienteId: " + request.getPendienteId()));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteTrasladoMapper.updateEntity(entity, request, pendiente);
        return PendienteTrasladoMapper.toResponse(pendienteTrasladoRepository.save(entity));
    }



    // ==========================================================
    // 🔹 PENDIENTE INSTALACIÓN CÁMARAS
    // ==========================================================
    public PendienteInstalacionCamarasResponse findPendienteInstalacionCamaras(Long pendienteId) {
        PendienteInstalacionCamaras entity = pendienteInstalacionCamarasRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Instalación de cámaras no encontrada con pendienteId: " + pendienteId));
        return PendienteInstalacionCamarasMapper.toResponse(entity);
    }

    public PendienteInstalacionCamarasResponse createPendienteInstalacionCamaras(PendienteInstalacionCamarasRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        PendienteInstalacionCamaras entity = PendienteInstalacionCamarasMapper.toEntity(request, pendiente);
        return PendienteInstalacionCamarasMapper.toResponse(pendienteInstalacionCamarasRepository.save(entity));
    }

    public PendienteInstalacionCamarasResponse updatePendienteInstalacionCamaras(PendienteInstalacionCamarasRequest request) {
        PendienteInstalacionCamaras entity = pendienteInstalacionCamarasRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Instalación de cámaras no encontrada con pendienteId: " + request.getPendienteId()));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteInstalacionCamarasMapper.updateEntity(entity, request, pendiente);
        return PendienteInstalacionCamarasMapper.toResponse(pendienteInstalacionCamarasRepository.save(entity));
    }

    // ==========================================================
    // 🔹 PENDIENTE INSTALACIÓN INTERNET
    // ==========================================================
    public PendienteInstalacionInternetResponse findPendienteInstalacionInternet(Long pendienteId) {
        PendienteInstalacionInternet entity = pendienteInstalacionInternetRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Instalación de internet no encontrada con pendienteId: " + pendienteId));
        return PendienteInstalacionInternetMapper.toResponse(entity);
    }

    public PendienteInstalacionInternetResponse createPendienteInstalacionInternet(PendienteInstalacionInternetRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        PendienteInstalacionInternet entity = PendienteInstalacionInternetMapper.toEntity(request, pendiente);
        return PendienteInstalacionInternetMapper.toResponse(pendienteInstalacionInternetRepository.save(entity));
    }

    public PendienteInstalacionInternetResponse updatePendienteInstalacionInternet(PendienteInstalacionInternetRequest request) {
        PendienteInstalacionInternet entity = pendienteInstalacionInternetRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Instalación de internet no encontrada con pendienteId: " + request.getPendienteId()));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteInstalacionInternetMapper.updateEntity(entity, request, pendiente);
        return PendienteInstalacionInternetMapper.toResponse(pendienteInstalacionInternetRepository.save(entity));
    }

    // ==========================================================
    // 🔹 PENDIENTE AVERÍA
    // ==========================================================
    public PendienteAveriaResponse findPendienteAveria(Long pendienteId) {
        PendienteAveria entity = pendienteAveriaRepsitory.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Avería no encontrada con pendienteId: " + pendienteId));
        return PendienteAveriaMapper.toResponse(entity);
    }

    public PendienteAveriaResponse createPendienteAveria(PendienteAveriaRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        PendienteAveria entity = PendienteAveriaMapper.toEntity(request, pendiente);
        return PendienteAveriaMapper.toResponse(pendienteAveriaRepsitory.save(entity));
    }

    public PendienteAveriaResponse updatePendienteAveria(PendienteAveriaRequest request) {
        PendienteAveria entity = pendienteAveriaRepsitory.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Avería no encontrada con pendienteId: " + request.getPendienteId()));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteAveriaMapper.updateEntity(entity, request, pendiente);
        return PendienteAveriaMapper.toResponse(pendienteAveriaRepsitory.save(entity));
    }

    // ==========================================================
    // 🔹 PENDIENTE RECOJO DISPOSITIVO
    // ==========================================================
    public PendienteRecojoDispositivoResponse findPendienteRecojoDispositivo(Long pendienteId) {
        PendienteRecojoDispositivo entity = pendienteRecojoDispositivoRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Recojo de dispositivo no encontrado con pendienteId: " + pendienteId));
        return PendienteRecojoDispositivoMapper.toResponse(entity);
    }

    public PendienteRecojoDispositivoResponse createPendienteRecojoDispositivo(PendienteRecojoDispositivoRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));
        PendienteRecojoDispositivo entity = PendienteRecojoDispositivoMapper.toEntity(request, pendiente);
        return PendienteRecojoDispositivoMapper.toResponse(pendienteRecojoDispositivoRepository.save(entity));
    }

    public PendienteRecojoDispositivoResponse updatePendienteRecojoDispositivo(PendienteRecojoDispositivoRequest request) {
        PendienteRecojoDispositivo entity = pendienteRecojoDispositivoRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Recojo de dispositivo no encontrado con pendienteId: " + request.getPendienteId()));
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        PendienteRecojoDispositivoMapper.updateEntity(entity, request, pendiente);
        return PendienteRecojoDispositivoMapper.toResponse(pendienteRecojoDispositivoRepository.save(entity));
    }


    // ==========================================================
    // 🔹 PENDIENTE TIEMPOS
    // ==========================================================

    public List<PendienteTiempoResponse> findTiemposByPendiente(Long pendienteId) {
        List<PendienteTiempo> tiempos = pendienteTiempoRepository.findByPendienteIdAndVigenteTrue(pendienteId);
        if (tiempos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron registros de tiempo para el pendienteId: " + pendienteId);
        }
        return tiempos.stream()
                .map(PendienteTiempoMapper::toResponse)
                .toList();
    }

    public PendienteTiempoResponse createPendienteTiempo(PendienteTiempoRequest request) {
        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        PendienteTiempo entity = PendienteTiempoMapper.toEntity(request, pendiente, empleado);
        return PendienteTiempoMapper.toResponse(pendienteTiempoRepository.save(entity));
    }

    public PendienteTiempoResponse updatePendienteTiempo(Long id, PendienteTiempoRequest request) {
        PendienteTiempo entity = pendienteTiempoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de tiempo no encontrado con id: " + id));

        Pendiente pendiente = pendienteRepository.findById(request.getPendienteId())
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + request.getPendienteId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        PendienteTiempoMapper.updateEntity(entity, request, pendiente, empleado);
        return PendienteTiempoMapper.toResponse(pendienteTiempoRepository.save(entity));
    }

    public void deletePendienteTiempo(Long id) {
        PendienteTiempo entity = pendienteTiempoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de tiempo no encontrado con id: " + id));
        entity.setVigente(false);
        pendienteTiempoRepository.save(entity);
    }


}
