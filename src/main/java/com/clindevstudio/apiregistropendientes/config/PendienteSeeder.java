package com.clindevstudio.apiregistropendientes.config;


import com.clindevstudio.apiregistropendientes.database.entities.*;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.PrioridadPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import com.clindevstudio.apiregistropendientes.database.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class PendienteSeeder {

    private final PendienteRepository pendienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;

    @PostConstruct
    public void seedPendientes() {
        if (pendienteRepository.count() != 0) {
            System.out.println("⚠️ Pendientes ya existen, se omite seeding.");
            return;
        }

        Empleado asesor = empleadoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay empleados disponibles para 'registradoPor'"));
        Empleado tecnico = empleadoRepository.findAll().stream().skip(1).findFirst()
                .orElse(asesor);
        Cliente cliente = clienteRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));


        for (EstadoPendiente estado : EstadoPendiente.values()) {
            for (int i = 1; i <= 2; i++) {
                Pendiente p = Pendiente.builder()
                        .titulo("Pendiente " + estado.name() + " #" + i)
                        .descripcion("Descripción de prueba para estado " + estado.name())
                        .fechaCreacion(LocalDateTime.now().minusDays(i))
                        .registradoPor(asesor)
                        .asignadoA(tecnico)
                        .estado(estado)
                        .lugar(LugarPendiente.ACORA) // ajusta si tienes enum distinto
                        .prioridad(PrioridadPendiente.values()[i % PrioridadPendiente.values().length])
                        .direccion("Av. Ejemplo " + i + " - Ciudad X")
                        .tipo(TipoPendiente.values()[i % TipoPendiente.values().length])
                        .cliente(cliente)
                        .build();

                pendienteRepository.save(p);
            }
        }

        System.out.println("✅ Se cargaron pendientes de prueba: " + pendienteRepository.count());
    }
}
