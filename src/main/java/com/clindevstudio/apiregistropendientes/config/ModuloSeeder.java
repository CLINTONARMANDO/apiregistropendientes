package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Modulo;
import com.clindevstudio.apiregistropendientes.database.repositories.ModuloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModuloSeeder {

    @Bean
    CommandLineRunner initModulos(ModuloRepository moduloRepository) {
        return args -> {
            if (moduloRepository.count() == 0) {

                // --- Módulos principales ---
                Modulo pendientes = moduloRepository.save(Modulo.builder()
                        .nombre("Pendientes")
                        .descripcion("Gestión de pendientes de instalación, averías, validación y PPOE.")
                        .ruta("/pendientes")
                        .vigente(true)
                        .build());

                Modulo clientes = moduloRepository.save(Modulo.builder()
                        .nombre("Clientes")
                        .descripcion("Gestión de clientes y sus servicios.")
                        .ruta("/clientes")
                        .vigente(true)
                        .build());

                Modulo catalogos = moduloRepository.save(Modulo.builder()
                        .nombre("Catálogos")
                        .descripcion("Gestión de planes, productos y servicios asociados.")
                        .ruta("/catalogos")
                        .vigente(true)
                        .build());

                Modulo pagos = moduloRepository.save(Modulo.builder()
                        .nombre("Pagos")
                        .descripcion("Registro y control de pagos de los clientes.")
                        .ruta("/pagos")
                        .vigente(true)
                        .build());

                Modulo usuarios = moduloRepository.save(Modulo.builder()
                        .nombre("Usuarios / Empleados")
                        .descripcion("Administración de cuentas, roles, empleados y cargos.")
                        .ruta("/usuarios")
                        .vigente(true)
                        .build());


                // --- Submódulos de Pendientes ---
                moduloRepository.save(Modulo.builder()
                        .nombre("Mis Pendientes")
                        .descripcion("Lista de pendientes asignados al usuario actual.")
                        .ruta("/pendientes/mis")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Por Asignar")
                        .descripcion("Pendientes aún no asignados a un técnico.")
                        .ruta("/pendientes/asignar")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Historial")
                        .descripcion("Historial de pendientes atendidos.")
                        .ruta("/pendientes/historial")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Por Validar")
                        .descripcion("Pendientes completados que requieren validación del asesor.")
                        .ruta("/pendientes/por-validar")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Faltantes PPOE")
                        .descripcion("Control de pendientes sin configuración PPOE registrada.")
                        .ruta("/pendientes/faltantes-ppoe")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Crear Pendiente")
                        .descripcion("Crear Pendiente.")
                        .ruta("/pendientes/crear-pendiente")
                        .moduloPadre(pendientes)
                        .vigente(true)
                        .build());


                // --- Submódulos de Catálogos ---
                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Catálogos")
                        .descripcion("Visualización general de planes y catálogos.")
                        .ruta("/catalogos/lista")
                        .moduloPadre(catalogos)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Productos")
                        .descripcion("Inventario y gestión de productos del sistema.")
                        .ruta("/catalogos/productos")
                        .moduloPadre(catalogos)
                        .vigente(true)
                        .build());


                // --- Submódulos de Pagos ---
                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Pagos")
                        .descripcion("Listado de pagos realizados por los clientes.")
                        .ruta("/pagos/lista")
                        .moduloPadre(pagos)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Reportes de Pagos")
                        .descripcion("Reportes y estadísticas de pagos.")
                        .ruta("/pagos/reportes")
                        .moduloPadre(pagos)
                        .vigente(true)
                        .build());


                // --- Submódulos de Usuarios / Empleados ---
                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Usuarios")
                        .descripcion("Administración de usuarios del sistema.")
                        .ruta("/usuarios/lista")
                        .moduloPadre(usuarios)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Roles")
                        .descripcion("Gestión de roles y permisos.")
                        .ruta("/usuarios/roles")
                        .moduloPadre(usuarios)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Empleados")
                        .descripcion("Gestión de datos del personal técnico y administrativo.")
                        .ruta("/usuarios/empleados")
                        .moduloPadre(usuarios)
                        .vigente(true)
                        .build());

                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Cargos")
                        .descripcion("Catálogo de cargos dentro de la organización.")
                        .ruta("/usuarios/cargos")
                        .moduloPadre(usuarios)
                        .vigente(true)
                        .build());

                // --- Submódulos de Clientes ---
                moduloRepository.save(Modulo.builder()
                        .nombre("Lista de Clientes")
                        .descripcion("Gestión y visualización de clientes.")
                        .ruta("/clientes/lista")
                        .moduloPadre(clientes)
                        .vigente(true).build());

                System.out.println("✅ Módulos iniciales creados correctamente.");
            }
        };
    }
}
