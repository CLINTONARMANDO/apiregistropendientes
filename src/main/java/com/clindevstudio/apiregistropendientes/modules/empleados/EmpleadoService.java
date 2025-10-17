package com.clindevstudio.apiregistropendientes.modules.empleados;

import com.clindevstudio.apiregistropendientes.database.entities.Cargo;
import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.repositories.CargoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.EmpleadoRepository;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoResponse;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoResponse;
import com.clindevstudio.apiregistropendientes.modules.empleados.mappers.EmpleadoMapper;
import com.clindevstudio.apiregistropendientes.modules.empleados.mappers.CargoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final CargoRepository cargoRepository;
    private final EmpleadoRepository empleadoRepository;


    public EmpleadoService(CargoRepository cargoRepository, EmpleadoRepository empleadoRepository) {
        this.cargoRepository = cargoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // Crear empleado
    public EmpleadoResponse crearEmpleado(EmpleadoRequest request) {
        Cargo cargo = cargoRepository.findById(request.getCargoId())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        Empleado empleado = EmpleadoMapper.toEntity(request, cargo);

        return EmpleadoMapper.toResponse(empleadoRepository.save(empleado));
    }
    // Crear cargo
    public CargoResponse crearCargo(CargoRequest cargoRequest) {

        Cargo cargo = CargoMapper.toEntity(cargoRequest);

        return CargoMapper.toResponse(cargoRepository.save(cargo));
    }

    // Listar empleados vigentes
    public List<EmpleadoResponse> listarEmpleadosVigentes() {
        return empleadoRepository.findByVigenteTrue()
                .stream()
                .map(EmpleadoMapper::toResponse)
                .toList();
    }

    // Listar cargos vigentes
    public List<CargoResponse> listarCargosVigentes() {
        return cargoRepository.findByVigenteTrue()
                .stream()
                .map(CargoMapper::toResponse)
                .toList();
    }

    // Actualizar empleado
    @Transactional
    public EmpleadoResponse actualizarEmpleado(Long id, EmpleadoRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Cargo cargo = cargoRepository.findById(request.getCargoId())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        EmpleadoMapper.updateEntity(empleado, request, cargo);
        return EmpleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public CargoResponse actualizarCargo(Long id, CargoRequest request) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        CargoMapper.updateEntity(cargo, request);
        return CargoMapper.toResponse(cargoRepository.save(cargo));
    }

    // Eliminar lógico de empleado
    @Transactional
    public void eliminarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setVigente(false);
        empleadoRepository.save(empleado);
    }

    // Eliminar lógico de cargo
    @Transactional
    public void eliminarCargo(Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        cargo.setVigente(false);
        cargoRepository.save(cargo);
    }
}

