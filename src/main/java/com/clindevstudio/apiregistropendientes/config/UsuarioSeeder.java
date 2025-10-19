package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Rol;
import com.clindevstudio.apiregistropendientes.database.entities.Usuario;
import com.clindevstudio.apiregistropendientes.database.repositories.EmpleadoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioSeeder {

    @Bean
    CommandLineRunner initUsuarios(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            EmpleadoRepository empleadoRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (usuarioRepository.count() != 0) {
                System.out.println("⚠️ Usuarios ya existen, se omite seeding.");
                return;
            }

            // buscar roles (lanza si no existen para que corrijas antes)
            Rol rolAdmin = rolRepository.findByNombre("Administrador")
                    .orElseThrow(() -> new RuntimeException("Rol 'Administrador' no encontrado"));
            Rol rolCoordinadora = rolRepository.findByNombre("Coordinadora")
                    .orElseThrow(() -> new RuntimeException("Rol 'Coordinadora' no encontrado"));
            Rol rolAtencion = rolRepository.findByNombre("Atención al Cliente")
                    .orElseThrow(() -> new RuntimeException("Rol 'Atención al Cliente' no encontrado"));
            Rol rolContabilidad = rolRepository.findByNombre("Contabilidad")
                    .orElseThrow(() -> new RuntimeException("Rol 'Contabilidad' no encontrado"));
            Rol rolAsesor = rolRepository.findByNombre("Asesor")
                    .orElseThrow(() -> new RuntimeException("Rol 'Asesor' no encontrado"));
            Rol rolTecnico = rolRepository.findByNombre("Tecnico")
                    .orElseThrow(() -> new RuntimeException("Rol 'Tecnico' no encontrado"));

            // ------------------------
            // Usuario: sistema (asociado al empleado sistema por email)
            // ------------------------
            Usuario sistema = new Usuario();
            sistema.setNombre("Sistema SIMAX");
            sistema.setDni("000000000");
            sistema.setEmail("sistema@simax.com");
            sistema.setPassword(passwordEncoder.encode("sistema123")); // cambia si quieres otra
            sistema.setVigente(true);
            sistema.setRol(rolAdmin);
            // intenta enlazar empleado por email; si no existe, deja null o lanza
            empleadoRepository.findByCorreo("sistema@simax.com").ifPresent(sistema::setEmpleado);
            usuarioRepository.save(sistema);

            // ------------------------
            // Usuarios reales (vinculados por email con los empleados ya seedados)
            // Ajusta contraseñas por defecto si lo deseas.
            // ------------------------

            // Asesores: Jessica, Lenin, Yaneth
            empleadoRepository.findByCorreo("jessicamayema@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Jessica Adelina Maye");
                u.setDni("76048000");
                u.setEmail("jessica@simax.com");
                u.setPassword(passwordEncoder.encode("asesor123"));
                u.setVigente(true);
                u.setRol(rolAsesor);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            empleadoRepository.findByCorreo("le5.9ac@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Lenin Arcaya");
                u.setDni("70124030");
                u.setEmail("lenin@simax.com");
                u.setPassword(passwordEncoder.encode("asesor123"));
                u.setVigente(true);
                u.setRol(rolAsesor);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            empleadoRepository.findByCorreo("norkalauracl@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Yaneth Contreras");
                u.setDni("43496951");
                u.setEmail("yaneth@simax.com");
                u.setPassword(passwordEncoder.encode("asesor123"));
                u.setVigente(true);
                u.setRol(rolAsesor);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Coordinadora: Asucena
            empleadoRepository.findByCorreo("asucenasimax@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Asucena Calizaya");
                u.setDni("70098326");
                u.setEmail("asucena@simax.com");
                u.setPassword(passwordEncoder.encode("coordinadora123"));
                u.setVigente(true);
                u.setRol(rolCoordinadora);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Atención al Cliente (ejemplo: Joel)
            empleadoRepository.findByCorreo("ninacallijoeldavid@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Joel David Nina");
                u.setDni("43538387");
                u.setEmail("atencion@simax.com");
                u.setPassword(passwordEncoder.encode("cliente123"));
                u.setVigente(true);
                u.setRol(rolAtencion);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Contabilidad (ejemplo: Henry)
            empleadoRepository.findByCorreo("henrywilson3@hotmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Henry Quispe");
                u.setDni("45293365");
                u.setEmail("contabilidad@simax.com");
                u.setPassword(passwordEncoder.encode("conta123"));
                u.setVigente(true);
                u.setRol(rolContabilidad);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Otros empleados / técnicos (ejemplos) — los dejo mapeados por email:
            empleadoRepository.findByCorreo("cacerezluisabelardo20@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Luis Alberto Cacerez");
                u.setDni("75890917");
                u.setEmail("luis.cacerez@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico); // o el rol que prefieras (p. ej. técnico)
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            empleadoRepository.findByCorreo("garciagarciahitlereusebio@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Hitler Garcia");
                u.setDni("48134174");
                u.setEmail("hitler.garcia@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Ronald Condori (Técnico)
            empleadoRepository.findByCorreo("ronaldcondoriperca@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Ronald Condori");
                u.setDni("60221581");
                u.setEmail("ronald.condori@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico); // o crea un rol "Técnico" si lo manejas aparte
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Eber Aro (Técnico)
            empleadoRepository.findByCorreo("everrarohuanca@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Eber Aro Huanca");
                u.setDni("77016730");
                u.setEmail("eber.aro@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Anderson Paquita (Técnico)
            empleadoRepository.findByCorreo("ander.pv74@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Anderson Paquita");
                u.setDni("75654653");
                u.setEmail("anderson.paquita@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Martin Chambilla (Técnico)
            empleadoRepository.findByCorreo("17martinchambilla@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Martin Chambilla");
                u.setDni("74780616");
                u.setEmail("martin.chambilla@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Royer Mamani (Atención al Cliente)
            empleadoRepository.findByCorreo("royer6375@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Royer Mamani");
                u.setDni("73622509");
                u.setEmail("royer.mamani@simax.com");
                u.setPassword(passwordEncoder.encode("cliente123"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Eliseo Aduviri (Administrador o Coordinador Técnico)
            empleadoRepository.findByCorreo("elicesosimax@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Eliseo Aduviri");
                u.setDni("70398328");
                u.setEmail("eliseo.aduviri@simax.com");
                u.setPassword(passwordEncoder.encode("admin123"));
                u.setVigente(true);
                u.setRol(rolAdmin);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            // Elias Aduviri (Técnico / Soporte)
            empleadoRepository.findByCorreo("aduviric300@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Elias Aduviri");
                u.setDni("6529575");
                u.setEmail("elias.aduviri@simax.com");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setVigente(true);
                u.setRol(rolTecnico);
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });

            empleadoRepository.findByCorreo("natalymeyly@gmail.com").ifPresent(emp -> {
                Usuario u = new Usuario();
                u.setNombre("Natalia Quispe");
                u.setDni("73245988");
                u.setEmail("natalia.quispe@simax.com");
                u.setPassword(passwordEncoder.encode("cliente123"));
                u.setVigente(true);
                u.setRol(rolAtencion); // según su cargo (Atención al Cliente)
                u.setEmpleado(emp);
                usuarioRepository.save(u);
            });
            System.out.println("✅ Usuarios iniciales creados correctamente.");
        };
    }
}
