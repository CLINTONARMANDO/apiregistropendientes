package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Cargo;
import com.clindevstudio.apiregistropendientes.database.repositories.CargoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargoSeeder {


    @Bean
    CommandLineRunner initCargos(
            CargoRepository cargoRepository
    ){
        return args -> {
            if (cargoRepository.count() == 0) {
                cargoRepository.save(new Cargo(null,"Coordinadora", "Coordina "));
                cargoRepository.save(new Cargo(null,"Marketing", "Publicidad por redes y precencial "));
                cargoRepository.save(new Cargo(null,"Atencion al Cliente", "Atiende al Cliente y recepciona averias y otros"));
                cargoRepository.save(new Cargo(null,"Asesor", "Personal encargado en tienda"));
                cargoRepository.save(new Cargo(null,"Tecnico", "Personal encargado de instalaciones, averias, etc"));
                cargoRepository.save(new Cargo(null,"Servicio Tecnico", "Personal encargado en tienda de repacion PC,s"));
                cargoRepository.save(new Cargo(null,"Almacen", "Personal encargado de almacen"));
                cargoRepository.save(new Cargo(null,"Gerente General", "Lider de la empresa"));
            }

        };

    }
}
