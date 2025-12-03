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

                System.out.println("✅ Roles iniciales creados correctamente.");
            }
        };
    }
}
