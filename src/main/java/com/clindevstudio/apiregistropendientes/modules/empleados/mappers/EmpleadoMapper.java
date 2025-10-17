package com.clindevstudio.apiregistropendientes.modules.empleados.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cargo;
import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.EmpleadoResponse;

public class EmpleadoMapper {
    public static Empleado toEntity(EmpleadoRequest empleadoRequest, Cargo cargo) {
        Empleado empleado = new Empleado();
        empleado.setCargo(cargo);
        empleado.setNombre(empleadoRequest.getNombre());
        empleado.setCorreo(empleadoRequest.getCorreo());
        empleado.setApellidoMaterno(empleadoRequest.getApellidoMaterno());
        empleado.setApellidoPaterno(empleadoRequest.getApellidoPaterno());
        empleado.setFechaIngreso(empleadoRequest.getFechaIngreso());
        empleado.setTelefono(empleadoRequest.getTelefono());

        return empleado;
    }
    public static EmpleadoResponse toResponse(Empleado empleado) {
        EmpleadoResponse empleadoResponse = new EmpleadoResponse();
        empleadoResponse.setNombre(empleado.getNombre());
        empleadoResponse.setCorreo(empleado.getCorreo());
        empleadoResponse.setApellidoMaterno(empleado.getApellidoMaterno());
        empleadoResponse.setApellidoPaterno(empleado.getApellidoPaterno());
        empleadoResponse.setTelefono(empleado.getTelefono());
        empleadoResponse.setId(empleado.getId());
        empleadoResponse.setCargo(empleado.getCargo().getNombre());
        empleadoResponse.setFechaIngreso( empleado.getFechaIngreso());
        return empleadoResponse;
    }

    public static void updateEntity(Empleado empleado, EmpleadoRequest empleadoRequest, Cargo cargo) {
        empleado.setCargo(cargo);
        empleado.setNombre(empleadoRequest.getNombre());
        empleado.setCorreo(empleadoRequest.getCorreo());
        empleado.setApellidoMaterno(empleadoRequest.getApellidoMaterno());
        empleado.setApellidoPaterno(empleadoRequest.getApellidoPaterno());
        empleado.setFechaIngreso(empleadoRequest.getFechaIngreso());
        empleado.setTelefono(empleadoRequest.getTelefono());
    }

}
