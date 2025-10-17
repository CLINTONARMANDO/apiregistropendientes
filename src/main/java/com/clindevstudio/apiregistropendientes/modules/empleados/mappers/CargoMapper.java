package com.clindevstudio.apiregistropendientes.modules.empleados.mappers;

import com.clindevstudio.apiregistropendientes.database.entities.Cargo;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoRequest;
import com.clindevstudio.apiregistropendientes.modules.empleados.dtos.CargoResponse;

public class CargoMapper {

    public static CargoResponse toResponse(Cargo cargo) {
        CargoResponse cargoResponse = new CargoResponse();
        cargoResponse.setId(cargo.getId());
        cargoResponse.setDescripcion(cargo.getDescripcion());
        cargoResponse.setNombre(cargo.getNombre());
        return cargoResponse;
    }

    public static Cargo toEntity(CargoRequest cargoRequest) {
        Cargo cargo = new Cargo();
        cargo.setDescripcion(cargoRequest.getDescripcion());
        cargo.setNombre(cargoRequest.getNombre());
        return cargo;
    }

    public static void updateEntity(Cargo cargo, CargoRequest cargoRequest) {
        cargo.setDescripcion(cargoRequest.getDescripcion());
        cargo.setNombre(cargoRequest.getNombre());
    }
}
