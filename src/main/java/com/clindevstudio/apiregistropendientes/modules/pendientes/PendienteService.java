package com.clindevstudio.apiregistropendientes.modules.pendientes;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.enums.*;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import com.clindevstudio.apiregistropendientes.database.specifications.PendienteSpecification;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.NotificacionService;
import com.clindevstudio.apiregistropendientes.modules.notificaciones.dtos.NotificacionRequest;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.*;
import com.clindevstudio.apiregistropendientes.modules.pendientes.mappers.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
    private final UsuarioRepository usuarioRepository;
    private final NotificacionService notificacionService;


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
            PendienteRecojoDispositivoRepository pendienteRecojoDispositivoRepository,
            UsuarioRepository usuarioRepository,
            NotificacionService notificacionService
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
        this.usuarioRepository = usuarioRepository;
        this.notificacionService = notificacionService;
    }

    public Page<PendienteResponse> filtrarPendientes(FiltroPendienteRequest filtro, Pageable pageable) {
        Specification<Pendiente> spec = PendienteSpecification.conFiltros(filtro)
                .and((root, query, cb) -> cb.equal(root.get("vigente"), true));
        return pendienteRepository.findAll(spec, pageable)
                .map(PendienteMapper::toResponse);
    }

    public PendienteResponse obtenerPorId(Long id) {
        Pendiente pendiente = pendienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado"));
        return PendienteMapper.toResponse(pendiente);
    }

    // --- Función para devolver todos los enums disponibles ---
    public Map<String, Object> obtenerFiltrosDisponibles() {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("estados", EstadoPendiente.values());
        filtros.put("tipos", TipoPendiente.values());
        return filtros;
    }


    @Transactional
    public PendienteResponse cambiarEstado(Long pendienteId, EstadoPendiente nuevoEstado) {
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + pendienteId));

        pendiente.setEstado(nuevoEstado);
        Pendiente actualizado = pendienteRepository.save(pendiente);

        switch (nuevoEstado) {

            case SIN_PPOE -> {
                notificacionService.enviarNotificacionPorPermiso(
                        Permiso.ASIGNAR_PPOE,
                        new NotificacionRequest(
                                "Pendiente requiere PPOE",
                                "El pendiente #" + pendiente.getId() + " requiere asignación de usuario PPOE.",
                                NotificationTipo.WARNING,
                                NotificationEstado.NO_LEIDO,
                                0L
                        )
                );
            }

            case SIN_VLAN -> {
                notificacionService.enviarNotificacionPorPermiso(
                        Permiso.ASIGNAR_VLAN,
                        new NotificacionRequest(
                                "Pendiente requiere VLAN",
                                "El pendiente #" + pendiente.getId() + " necesita asignación de VLAN.",
                                NotificationTipo.WARNING,
                                NotificationEstado.NO_LEIDO,
                                0L
                        )
                );
            }

            case POR_VALIDAR -> {
                notificacionService.enviarNotificacionPorPermiso(
                        Permiso.REVISAR_GASTOS,
                        new NotificacionRequest(
                                "Pendiente por validar",
                                "El pendiente #" + pendiente.getId() + " está listo para revisión y validación.",
                                NotificationTipo.INFO,
                                NotificationEstado.NO_LEIDO,
                                0L
                        )
                );
            }

            case ASIGNADO -> {
                notificacionService.enviarNotificacionPorPermiso(
                        Permiso.ASIGNAR_TECNICO,
                        new NotificacionRequest(
                                "Nuevo pendiente asignado",
                                "Se ha asignado el pendiente #" + pendiente.getId(),
                                NotificationTipo.INFO,
                                NotificationEstado.NO_LEIDO,
                                0L
                        )
                );
            }

            default -> {}
        }

        return PendienteMapper.toResponse(actualizado);
    }



    @Transactional
    public PendienteResponse asignarEmpleadoId(Long pendienteId, Long idEmpleado) {
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado con id: " + pendienteId));

        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + idEmpleado));

        pendiente.setAsignadoA(empleado);
        Pendiente actualizado = pendienteRepository.save(pendiente);

        // 🔹 Buscar el usuario asociado a este empleado
        Usuario usuario = usuarioRepository.findByEmpleadoIdAndVigenteTrue(idEmpleado)
                .orElse(null);

        if (usuario != null) {
            // 🔹 Crear y enviar notificación al usuario del empleado
            NotificacionRequest notificacion = new NotificacionRequest();
            notificacion.setTitulo("Nuevo pendiente asignado");
            notificacion.setMensaje("Se te ha asignado el pendiente: " + pendiente.getId());
            notificacion.setUsuarioId(usuario.getId());
            notificacion.setTipo(NotificationTipo.INFO);
            notificacion.setEstado(NotificationEstado.NO_LEIDO);

            notificacionService.crearNotificacion(notificacion);
        }

        return PendienteMapper.toResponse(actualizado);
    }


    @Transactional
    public PendienteInstalacionInternetResponse asignarPpoe(Long pendienteId, String ppoeUser, String ppoePassword) {
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado con id: " + pendienteId));

        PendienteInstalacionInternet pendienteInstalacionInternet = pendienteInstalacionInternetRepository.findByPendienteId(pendienteId)
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado con id: " + pendienteId));


        pendienteInstalacionInternet.setPpoe(ppoeUser);
        pendienteInstalacionInternet.setPpoePassword(ppoePassword);
        PendienteInstalacionInternet actualizado = pendienteInstalacionInternetRepository.save(pendienteInstalacionInternet);

        // 🔹 Buscar el usuario asociado a este empleado
        Usuario usuario = usuarioRepository.findByEmpleadoIdAndVigenteTrue(pendiente.getAsignadoA().getId())
                .orElse(null);

        if (usuario != null) {
            // 🔹 Crear y enviar notificación al usuario del empleado
            NotificacionRequest notificacion = new NotificacionRequest();
            notificacion.setTitulo("Nuevo pendiente asignado");
            notificacion.setMensaje("Se te ha asignado el Ppoe para la instalacion: " + pendienteInstalacionInternet.getPendienteId());
            notificacion.setUsuarioId(usuario.getId());
            notificacion.setTipo(NotificationTipo.INFO);

            notificacionService.crearNotificacion(notificacion);
        }

        return PendienteInstalacionInternetMapper.toResponse(actualizado);
    }


    @Transactional
    public PendienteInstalacionInternetResponse asignarVlan(Long pendienteId, String vlan) {
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado con id: " + pendienteId));

        PendienteInstalacionInternet pendienteInstalacionInternet = pendienteInstalacionInternetRepository.findByPendienteId(pendienteId)
                .orElseThrow(() -> new RuntimeException("Pendiente no encontrado con id: " + pendienteId));


        pendienteInstalacionInternet.setVlan(vlan);
        PendienteInstalacionInternet actualizado = pendienteInstalacionInternetRepository.save(pendienteInstalacionInternet);

        // 🔹 Buscar el usuario asociado a este empleado
        Usuario usuario = usuarioRepository.findByEmpleadoIdAndVigenteTrue(pendiente.getAsignadoA().getId())
                .orElse(null);

        if (usuario != null) {
            // 🔹 Crear y enviar notificación al usuario del empleado
            NotificacionRequest notificacion = new NotificacionRequest();
            notificacion.setTitulo("Nuevo pendiente asignado");
            notificacion.setMensaje("Se te ha asignado el Vlan para la instalacion: " + pendienteInstalacionInternet.getPendienteId());
            notificacion.setUsuarioId(usuario.getId());
            notificacion.setTipo(NotificationTipo.INFO);

            notificacionService.crearNotificacion(notificacion);
        }

        return PendienteInstalacionInternetMapper.toResponse(actualizado);
    }


    @Transactional
    public PendienteResponse postergarPendiente(Long pendienteId, PostergarPendienteRequest request) {
        // 🔹 1. Validar que el pendiente existe
        Pendiente pendiente = pendienteRepository.findById(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException("Pendiente no encontrado con id: " + pendienteId));

        // 🔹 2. Validar que se puede postergar según el estado actual
        if (!esPosiblePostergar(pendiente.getEstado())) {
            throw new IllegalStateException("No se puede postergar un pendiente en estado: " + pendiente.getEstado());
        }

        // 🔹 3. Validar que la nueva fecha es posterior a la actual
        if (request.getNuevaFecha() != null && pendiente.getFechaPendiente() != null) {
            LocalDateTime fechaActualDelPendiente = pendiente.getFechaPendiente();

            // Compara fechas y horas
            if (!request.getNuevaFecha().isAfter(fechaActualDelPendiente)) {
                throw new IllegalArgumentException("La nueva fecha debe ser posterior a la fecha actual del pendiente");
            }
        }
        // 🔹 4. Actualizar el pendiente
        EstadoPendiente estadoAnterior = pendiente.getEstado();
        pendiente.setEstado(EstadoPendiente.POSTERGADO);

        if (request.getNuevaFecha() != null) {
            // ✅ Convertir LocalDate a LocalDateTime (inicio del día)
            pendiente.setFechaPendiente(request.getNuevaFecha());
        }

        Pendiente actualizado = pendienteRepository.save(pendiente);

        // 🔹 5. Crear notificación base
        NotificacionRequest notificacionBase = new NotificacionRequest();
        notificacionBase.setTitulo("Pendiente postergado");
        notificacionBase.setMensaje(String.format(
                "El pendiente #%d ha sido postergado. Nueva fecha: %s.",
                pendiente.getId(),
                request.getNuevaFecha() != null ? request.getNuevaFecha().toString() : "Sin cambio"
        ));
        notificacionBase.setTipo(NotificationTipo.INFO);

        // 🔹 6. Notificar según quién está involucrado
        // a) Notificar al empleado asignado (si existe)
        if (pendiente.getAsignadoA() != null) {
            Usuario usuarioAsignado = usuarioRepository.findByEmpleadoIdAndVigenteTrue(pendiente.getAsignadoA().getId())
                    .orElse(null);

            if (usuarioAsignado != null) {
                NotificacionRequest notifAsignado = new NotificacionRequest();
                notifAsignado.setTitulo("Tu pendiente fue postergado");
                notifAsignado.setMensaje(String.format(
                        "El pendiente #%d que te fue asignado ha sido postergado hasta %s",
                        pendiente.getId(),
                        request.getNuevaFecha()
                ));
                notifAsignado.setUsuarioId(usuarioAsignado.getId());
                notifAsignado.setTipo(NotificationTipo.WARNING);

                notificacionService.crearNotificacion(notifAsignado);
            }
        }

        // b) Notificar a los coordinadores y administradores
        notificacionService.enviarNotificacionARol("CORD", notificacionBase);
        notificacionService.enviarNotificacionARol("ADMN", notificacionBase);

        return PendienteMapper.toResponse(actualizado);
    }

    /**
     * Valida si un pendiente puede ser postergado según su estado actual
     * @param estado Estado actual del pendiente
     * @return true si se puede postergar, false en caso contrario
     */
    private boolean esPosiblePostergar(EstadoPendiente estado) {
        return estado != EstadoPendiente.FINALIZADO &&
                estado != EstadoPendiente.CANCELADO &&
                estado != EstadoPendiente.POSTERGADO;
    }

    public PendienteResponse crearPendiente(CrearPendienteRequest crearPendienteRequest) {
        // Puedes inicializar valores por defecto aquí
        Cliente cliente = clienteRepository.findById(crearPendienteRequest.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + crearPendienteRequest.getClienteId()));
        Empleado empleado = empleadoRepository.findById(crearPendienteRequest.getRegistradoPorId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + crearPendienteRequest.getRegistradoPorId()));
        Pendiente pendiente = PendienteMapper.toEntity(crearPendienteRequest, cliente, empleado);
        if (pendiente.getEstado() == null) {
            pendiente.setEstado(EstadoPendiente.REGISTRADO); // Estado inicial por defecto
        }
        notificacionService.enviarNotificacionPorPermiso(
                Permiso.ASIGNAR_TECNICO,
                new NotificacionRequest(
                        "Nuevo pendiente",
                        "Se requiere asignación de técnico",
                        NotificationTipo.INFO,
                        NotificationEstado.NO_LEIDO,
                        0L
                )
        );
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

    public PendienteTiempoResponse cerrarUltimoTiempo(Long pendienteId) {

        // Buscar el último tiempo vigente
        PendienteTiempo ultimoTiempo = pendienteTiempoRepository
                .findTopByPendienteIdAndVigenteTrueOrderByHoraInicioDesc(pendienteId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un tiempo vigente para el pendienteId: " + pendienteId));

        // Asignar hora fin = ahora
        ultimoTiempo.setHoraFin(LocalDateTime.now());

        // Guardar
        PendienteTiempo guardado = pendienteTiempoRepository.save(ultimoTiempo);

        return PendienteTiempoMapper.toResponse(guardado);
    }


    public void deletePendienteTiempo(Long id) {
        PendienteTiempo entity = pendienteTiempoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de tiempo no encontrado con id: " + id));
        entity.setVigente(false);
        pendienteTiempoRepository.save(entity);
    }


}
