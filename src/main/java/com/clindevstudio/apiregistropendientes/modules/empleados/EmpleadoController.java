package com.clindevstudio.apiregistropendientes.modules.empleados;

import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponse;
import com.clindevstudio.apiregistropendientes.modules.common.TransactionResponseFactory;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoResponse;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // ✅ Crear empleado
    @PostMapping
    public TransactionResponse<EmpleadoResponse> crearEmpleado(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse empleado = empleadoService.crearEmpleado(request);
        return TransactionResponseFactory.success(empleado, "Empleado creado correctamente");
    }

    // ✅ Crear cargo
    @PostMapping("/cargo")
    public TransactionResponse<CargoResponse> crearCargo(@RequestBody CargoRequest request) {
        CargoResponse cargo = empleadoService.crearCargo(request);
        return TransactionResponseFactory.success(cargo, "Cargo creado correctamente");
    }

    // ✅ Listar empleados vigentes
    @GetMapping
    public TransactionResponse<List<EmpleadoResponse>> listarEmpleadosVigentes() {
        List<EmpleadoResponse> empleados = empleadoService.listarEmpleadosVigentes();
        return TransactionResponseFactory.success(empleados, "Empleados vigentes obtenidos correctamente");
    }

    // ✅ Listar cargos vigentes
    @GetMapping("/cargos")
    public TransactionResponse<List<CargoResponse>> listarCargosVigentes() {
        List<CargoResponse> cargos = empleadoService.listarCargosVigentes();
        return TransactionResponseFactory.success(cargos, "Cargos vigentes obtenidos correctamente");
    }

    // ✅ Actualizar empleado
    @PutMapping("/{id}")
    @Transactional
    public TransactionResponse<EmpleadoResponse> actualizarEmpleado(
            @PathVariable Long id,
            @RequestBody EmpleadoRequest request
    ) {
        EmpleadoResponse actualizado = empleadoService.actualizarEmpleado(id, request);
        return TransactionResponseFactory.success(actualizado, "Empleado actualizado correctamente");
    }

    // ✅ Actualizar cargo
    @PutMapping("/cargo/{id}")
    @Transactional
    public TransactionResponse<CargoResponse> actualizarCargo(
            @PathVariable Long id,
            @RequestBody CargoRequest request
    ) {
        CargoResponse actualizado = empleadoService.actualizarCargo(id, request);
        return TransactionResponseFactory.success(actualizado, "Cargo actualizado correctamente");
    }

    // ✅ Eliminar lógico de empleado
    @DeleteMapping("/{id}")
    @Transactional
    public TransactionResponse<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return TransactionResponseFactory.success(null, "Empleado eliminado correctamente");
    }

    // ✅ Eliminar lógico de cargo
    @DeleteMapping("/cargo/{id}")
    @Transactional
    public TransactionResponse<Void> eliminarCargo(@PathVariable Long id) {
        empleadoService.eliminarCargo(id);
        return TransactionResponseFactory.success(null, "Cargo eliminado correctamente");
    }
}
