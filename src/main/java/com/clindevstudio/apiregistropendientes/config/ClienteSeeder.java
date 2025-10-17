

package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Cliente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoCliente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoDocumento;
import com.clindevstudio.apiregistropendientes.database.repositories.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteSeeder {

    @Bean
    CommandLineRunner initClientes(ClienteRepository clienteRepository) {
        return args -> {
            if (clienteRepository.count() == 0) {
                Cliente cliente = Cliente.builder()
                        .tipoCliente(TipoCliente.EMPRESA)
                        .nombre("NETSOLUTIONS")
                        .tipoDocumento(TipoDocumento.RUC)
                        .numeroDocumento("00000000")
                        .direccion("Jr. Gardenias 123 - Lima")
                        .telefono("999888777")
                        .email("contacto@netsolutions.com")
                        .contactoRepresentante("Juan Pérez")
                        .build();

                clienteRepository.save(cliente);

                System.out.println("✅ Cliente de prueba creado correctamente.");
            } else {
                System.out.println("⚠️ Clientes ya existen, se omite seeding.");
            }
        };
    }
}
