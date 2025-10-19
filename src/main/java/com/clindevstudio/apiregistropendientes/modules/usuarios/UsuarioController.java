package com.clindevstudio.apiregistropendientes.modules.usuarios;

import com.clindevstudio.apiregistropendientes.database.repositories.UsuarioRepository;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.usuarios.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public TransactionResponse<List<UsuarioResponse>> obtenerUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.getUsuarios();
        return TransactionResponseFactory.success(
                usuarios,
                "Usuarios obtenidos correctamente"
        );
    }

    @PostMapping
    public TransactionResponse<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioguardado = usuarioService.createUsuario(usuarioRequest);
        return TransactionResponseFactory.success(
                usuarioguardado,
                "Usuario creado correctamente"
        );
    }

    @GetMapping("/usuario")
    public TransactionResponse<UsuarioResponse> obtenerUsuario(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        UsuarioResponse usuario = usuarioService.getUserByToken(token);
        return TransactionResponseFactory.success(
                usuario,
                "Usuarios obtenidos correctamente"
        );
    }

    @GetMapping("/{id}")
    public TransactionResponse<UsuarioResponse> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioResponse usuarioResponse = usuarioService.getById(id);
        return TransactionResponseFactory.success(
                usuarioResponse,
                "Usuario obtenido correctamente"
        );
    }


    // ✅ Actualizar usuario
    @PutMapping("/{id}")
    public TransactionResponse<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioRequest usuarioRequest
    ) {
        UsuarioResponse usuarioActualizado = usuarioService.updateUsuario(id, usuarioRequest);
        return TransactionResponseFactory.success(usuarioActualizado, "Usuario actualizado correctamente");
    }

    // ✅ Eliminar usuario
    @DeleteMapping("/{id}")
    public TransactionResponse<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return TransactionResponseFactory.success(null, "Usuario eliminado correctamente");
    }

    @GetMapping("/roles")
    public TransactionResponse<List<RolResponse>> obtenerRolees() {
        List<RolResponse> rolResponseList = usuarioService.getRols();
        return TransactionResponseFactory.success(
                rolResponseList,
                "Roles obtenidos correctamente"
        );
    }

    @PostMapping("/roles")
    public TransactionResponse<RolResponse> crearRol(@RequestBody RolRequest rolRequest) {
        RolResponse rolguardado = usuarioService.createRol(rolRequest);
        return TransactionResponseFactory.success(
                rolguardado,
                "Usuario creado correctamente"
        );
    }


    // ✅ Actualizar rol
    @PutMapping("/roles/{id}")
    public TransactionResponse<RolResponse> actualizarRol(
            @PathVariable String id,
            @RequestBody RolRequest rolRequest
    ) {
        RolResponse rolActualizado = usuarioService.updateRol(id, rolRequest);
        return TransactionResponseFactory.success(rolActualizado, "Rol actualizado correctamente");
    }

    // ✅ Eliminar rol
    @DeleteMapping("/roles/{id}")
    public TransactionResponse<Void> eliminarRol(@PathVariable String id) {
        usuarioService.deleteRol(id);
        return TransactionResponseFactory.success(null, "Rol eliminado correctamente");
    }

    @GetMapping("/modulos/rol/{id}")
    public TransactionResponse<List<ModuloResponse>> obtenerModulosDelUsuario(
            @PathVariable String id
    ) {
        List<ModuloResponse> modulos = usuarioService.getModulosByRol(id);
        return TransactionResponseFactory.success(
                modulos,
                "Modulos obtenidos correctamente"
        );
    }

    @GetMapping("/modulos/usuario")
    public TransactionResponse<List<ModuloResponse>> obtenerModulosPorUsuario(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        List<ModuloResponse> modulos = usuarioService.getModulosByToken(token);
        return TransactionResponseFactory.success(
                modulos,
                "Modulos obtenidos correctamente"
        );
    }

    @GetMapping("/modulos")
    public TransactionResponse<List<ModuloResponse>> obtenerModulos(
    ) {
        List<ModuloResponse> modulos = usuarioService.getModulos();
        return TransactionResponseFactory.success(
                modulos,
                "Modulos obtenidos correctamente"
        );
    }

    // ✅ Crear módulo
    @PostMapping("/modulos")
    public TransactionResponse<ModuloResponse> crearModulo(@RequestBody ModuloRequest request) {
        ModuloResponse response = usuarioService.createModulo(request);
        return TransactionResponseFactory.success(
                response,
                "Módulo creado correctamente"
        );
    }

    // ✅ Actualizar módulo
    @PutMapping("/modulos/{id}")
    public TransactionResponse<ModuloResponse> actualizarModulo(
            @PathVariable Long id,
            @RequestBody ModuloRequest request
    ) {
        ModuloResponse response = usuarioService.updateModulo(id, request);
        return TransactionResponseFactory.success(
                response,
                "Módulo actualizado correctamente"
        );
    }

    // ✅ Obtener módulo por ID
    @GetMapping("/modulos/{id}")
    public TransactionResponse<ModuloResponse> obtenerModulo(@PathVariable Long id) {
        ModuloResponse modulo = usuarioService.getModuloById(id);
        return TransactionResponseFactory.success(
                modulo,
                "Módulo obtenido correctamente"
        );
    }

    @PostMapping("/modulos/sync")
    public TransactionResponse<Void> syncModulosConRol(
            @RequestBody ModuloRolSyncRequest request
    ) {
        usuarioService.syncModulosConRol(request);
        return TransactionResponseFactory.success(
                null,
                "Módulos sincronizados correctamente"
        );
    }

    // ✅ Eliminar módulo (soft delete → activo = false)
    @DeleteMapping("/modulos/{id}")
    public TransactionResponse<Void> eliminarModulo(@PathVariable Long id) {
        usuarioService.deleteModulo(id);
        return TransactionResponseFactory.success(
                null,
                "Módulo eliminado correctamente"
        );
    }
}
