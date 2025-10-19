package com.clindevstudio.apiregistropendientes.modules.usuarios;

import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import com.clindevstudio.apiregistropendientes.modules.auth.utils.JwtUtil;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.*;
import com.clindevstudio.apiregistropendientes.modules.usuarios.mappers.ModuloMapper;
import com.clindevstudio.apiregistropendientes.modules.usuarios.mappers.RolMapper;
import com.clindevstudio.apiregistropendientes.modules.usuarios.mappers.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private ModuloRolRepository  moduloRolRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Obtener todos los usuarios
    public List<UsuarioResponse> getUsuarios() {
        return usuarioRepository.findAllByVigente(true)
                .stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }
    // Crear un usuario nuevo
    public UsuarioResponse createUsuario(UsuarioRequest usuarioRequest) {
        // Verificar duplicados antes de crear
        if (usuarioRepository.existsByNombre(usuarioRequest.getNombre())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre");
        }

        if (usuarioRepository.existsByDni(usuarioRequest.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo DNI");
        }

        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo correo electrónico");
        }

        Rol rol = rolRepository.findById(usuarioRequest.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest, rol);
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    // Actualizar un usuario existente
    public UsuarioResponse updateUsuario(Long id, UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validar duplicados solo si los valores cambian
        if (!usuario.getNombre().equals(usuarioRequest.getNombre()) &&
                usuarioRepository.existsByNombre(usuarioRequest.getNombre())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre");
        }

        if (!usuario.getDni().equals(usuarioRequest.getDni()) &&
                usuarioRepository.existsByDni(usuarioRequest.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo DNI");
        }

        if (!usuario.getEmail().equals(usuarioRequest.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo correo electrónico");
        }

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setDni(usuarioRequest.getDni());
        usuario.setEmail(usuarioRequest.getEmail());

        Rol rol = rolRepository.findById(usuarioRequest.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        usuario.setRol(rol);

        if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        }

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }


    // Obtener roles
    public List<RolResponse> getRols() {
        return rolRepository.findAllByVigente(true)
                .stream()
                .map(RolMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Crear rol
    public RolResponse createRol(RolRequest rolRequest) {
        Rol rol = RolMapper.toEntity(rolRequest);
        return RolMapper.toResponse(rolRepository.save(rol));
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Si manejas soft delete
        usuario.setVigente(false);
        usuarioRepository.save(usuario);

        // Si prefieres eliminar físicamente, usa:
        // usuarioRepository.delete(usuario);
    }

    // ✅ Actualizar rol
    public RolResponse updateRol(String id, RolRequest rolRequest) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        rol.setId(rolRequest.getId());
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());

        return RolMapper.toResponse(rolRepository.save(rol));
    }

    public void deleteRol(String id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        boolean tieneUsuarios = usuarioRepository.existsByRolId(id);
        if (tieneUsuarios) {
            throw new IllegalStateException("No se puede eliminar el rol porque está asignado a uno o más usuarios");
        }

        rol.setVigente(false);
        rolRepository.save(rol);
    }

    // Buscar usuario por ID
    public UsuarioResponse getById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    public List<ModuloResponse> getModulosByToken(String token) {
        // Extraer userId o rol del token
        Long userId = jwtUtil.extractUserId(token);

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rol rol = usuario.getRol();

        // Buscar qué módulos tiene ese rol
        List<ModuloRol> moduloRoles = moduloRolRepository.findByRolId(rol.getId());

        return moduloRoles.stream()
                .map(ModuloRol::getModulo)
                .filter(Modulo::getVigente) // solo activos
                .map(ModuloMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ModuloResponse> getModulos() {
        // Buscar qué módulos tiene ese rol
        List<Modulo> modulos = moduloRepository.findAll();

        return modulos.stream()
                .filter(Modulo::getVigente) // solo activos
                .map(ModuloMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ModuloResponse> getModulosByRol(String rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        // Buscar qué módulos tiene ese rol
        List<ModuloRol> moduloRoles = moduloRolRepository.findByRolId(rol.getId());

        return moduloRoles.stream()
                .map(ModuloRol::getModulo)
                .filter(Modulo::getVigente) // solo activos
                .map(ModuloMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse getUserByToken(String token) {
        // Extraer userId o rol del token
        Long userId = jwtUtil.extractUserId(token);

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return UsuarioMapper.toResponse(usuario);
    }
    // Crear módulo
    public ModuloResponse createModulo(ModuloRequest moduloRequest) {
        Modulo modulo = ModuloMapper.toEntity(moduloRequest);

        // Si viene un módulo padre, lo buscamos
        if (moduloRequest.getModuloPadreId() != null) {
            Modulo moduloPadre = moduloRepository.findById(moduloRequest.getModuloPadreId())
                    .orElseThrow(() -> new EntityNotFoundException("Módulo padre no encontrado"));
            modulo.setModuloPadre(moduloPadre);
        }

        Modulo savedModulo = moduloRepository.save(modulo);
        return ModuloMapper.toResponse(savedModulo);
    }
    // Actualizar módulo
    public ModuloResponse updateModulo(Long id, ModuloRequest moduloRequest) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado"));

        modulo.setNombre(moduloRequest.getNombre());
        modulo.setDescripcion(moduloRequest.getDescripcion());
        modulo.setRuta(moduloRequest.getRuta());
        modulo.setVigente(moduloRequest.getActivo());

        // Si viene un módulo padre lo actualizamos, si no lo dejamos null
        if (moduloRequest.getModuloPadreId() != null) {
            Modulo moduloPadre = moduloRepository.findById(moduloRequest.getModuloPadreId())
                    .orElseThrow(() -> new EntityNotFoundException("Módulo padre no encontrado"));
            modulo.setModuloPadre(moduloPadre);
        } else {
            modulo.setModuloPadre(null);
        }

        Modulo updatedModulo = moduloRepository.save(modulo);
        return ModuloMapper.toResponse(updatedModulo);
    }

    // Obtener módulo por ID
    public ModuloResponse getModuloById(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado"));
        return ModuloMapper.toResponse(modulo);
    }

    @Transactional
    public void syncModulosConRol(ModuloRolSyncRequest request) {
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Traemos los módulos a asignar, incluyendo sus padres
        Set<Long> modulosFinales = new HashSet<>();

        for (Long moduloId : request.getModuloIds()) {
            Modulo modulo = moduloRepository.findById(moduloId)
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

            // Añadir el módulo
            modulosFinales.add(modulo.getId());

            // Recorrer padres
            Modulo padre = modulo.getModuloPadre();
            while (padre != null) {
                modulosFinales.add(padre.getId());
                padre = padre.getModuloPadre();
            }
        }

        // Obtener los actuales asignados
        List<ModuloRol> actuales = moduloRolRepository.findByRolId(rol.getId());
        Set<Long> actualesIds = actuales.stream()
                .map(mr -> mr.getModulo().getId())
                .collect(Collectors.toSet());

        // 1️⃣ Crear los que faltan
        for (Long moduloId : modulosFinales) {
            if (!actualesIds.contains(moduloId)) {
                Modulo modulo = moduloRepository.findById(moduloId)
                        .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

                ModuloRol nuevo = new ModuloRol();
                nuevo.setModulo(modulo);
                nuevo.setRol(rol);
                nuevo.setPermisos("READ"); // default o null
                moduloRolRepository.save(nuevo);
            }
        }

        // 2️⃣ Eliminar los que sobran
        for (ModuloRol existente : actuales) {
            if (!modulosFinales.contains(existente.getModulo().getId())) {
                moduloRolRepository.delete(existente);
            }
        }
    }

    // Eliminar módulo (soft delete → activo = false)
    public void deleteModulo(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado"));
        modulo.setVigente(false);
        moduloRepository.save(modulo);
    }
}
