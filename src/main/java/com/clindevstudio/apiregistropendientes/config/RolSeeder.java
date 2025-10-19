package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolSeeder {

    @Bean
    CommandLineRunner initRoles(
            RolRepository rolRepository
    ) {
        return args -> {
            if (rolRepository.count() == 0) {

                rolRepository.save(new Rol("ADMN", "Administrador", "Acceso completo al sistema", null));
                rolRepository.save(new Rol("CORD", "Coordinadora", "Gestión y asignación de pendientes, validación y control PPOE", null));
                rolRepository.save(new Rol("ATAC", "Atención al Cliente", "Consulta de historial y control de registros PPOE", null));
                rolRepository.save(new Rol("CONT", "Contabilidad", "Validación de pendientes y consulta de historial", null));
                rolRepository.save(new Rol("ASES", "Asesor", "Acceso al historial de pendientes atendidos", null));
                rolRepository.save(new Rol("TECN", "Tecnico", "Acceso a sus pendientes etc", null));

                System.out.println("✅ Roles iniciales creados correctamente.");
            }
        };
    }
}
