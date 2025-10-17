package com.clindevstudio.apiregistropendientes.modules.auth;

import com.clindevstudio.apiregistropendientes.database.entities.Usuario;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.UsuarioRepository;
import com.clindevstudio.apiregistropendientes.modules.auth.dtos.LoginRequest;
import com.clindevstudio.apiregistropendientes.modules.auth.dtos.LoginResponse;
import com.clindevstudio.apiregistropendientes.modules.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByDni(request.getDni())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generamos JWT real
        String token = JwtUtil.generateToken(usuario.getId(), usuario.getRol().getNombre());

        return new LoginResponse(
                token,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }

    /**
     * ✅ Verifica si el token es válido y si el usuario está activo (vigente)
     */
    public void validarTokenYUsuarioActivo(String token) {
        // 1️⃣ Validar token
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token no proporcionado");
        }

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        // 2️⃣ Extraer ID de usuario del token
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            throw new RuntimeException("Token no contiene un ID de usuario válido");
        }

        // 3️⃣ Buscar usuario
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 4️⃣ Verificar si el usuario está activo (vigente)
        if (!usuario.getVigente()) {
            throw new RuntimeException("Usuario inactivo o dado de baja");
        }
    }
}