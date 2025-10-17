package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Empleado;
import com.clindevstudio.apiregistropendientes.database.repositories.CargoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class EmpleadoSeeder {
    @Bean
    CommandLineRunner initEmpleados(
            EmpleadoRepository empleadoRepository,
            CargoRepository cargoRepository
    ){

        return args -> {

            if (empleadoRepository.count() == 0) {
            empleadoRepository.save(new Empleado(null, "Joel David", "Nina", "Calle", "951936726","ninacallijoeldavid@gmail.com", cargoRepository.findById(3L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Natalia", "Quispe", "Quispe", "974642136","natalymeyly@gmail.com", cargoRepository.findById(3L).orElseThrow(),LocalDate.now()));

            empleadoRepository.save(new Empleado(null, "Luis Alberto", "Cacerez", "Callata", "984188626","cacerezluisabelardo20@gmail.com", cargoRepository.findById(5L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Hitler", "Garcia", "", "936534581","garciagarciahitlereusebio@gmail.com", cargoRepository.findById(5L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Ronald", "Condori", "Perca", "939694283","ronaldcondoriperca@gmail.com", cargoRepository.findById(5L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Eber", "Aro", "Huanca", "919102277","everrarohuanca@gmail.com", cargoRepository.findById(5L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Anderson", "Paquita", "Viscarra", "927367382","ander.pv74@gmail.com", cargoRepository.findById(5L).orElseThrow(),LocalDate.now()));

            empleadoRepository.save(new Empleado(null, "Martin", "Chambilla", "Chambilla", "965272538","17martinchambilla@gmail.com", cargoRepository.findById(6L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Jessica Adelina", "Maye", "Maquera", "983101119","jessicamayema@gmail.com", cargoRepository.findById(4L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Lenin", "Arcaya", "Chura", "935624541","le5.9ac@gmail.com", cargoRepository.findById(6L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Yaneth", "Contreras", "Laura", "954726978","norkalauracl@gmail.com", cargoRepository.findById(6L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Henry", "Quispe", "Quispe", "950770918","henrywilson3@hotmail.com", cargoRepository.findById(2L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Royer", "Mamani", "Mamani", "910911455","royer6375@gmail.com", cargoRepository.findById(7L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Asucena", "Calizaya", "Chique", "921672161","asucenasimax@gmail.com", cargoRepository.findById(1L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Eliseo", "Aduviri", "Contreras", "928531406","elicesosimax@gmail.com", cargoRepository.findById(8L).orElseThrow(),LocalDate.now()));
            empleadoRepository.save(new Empleado(null, "Elias", "Aduviri", "Contreras", "931131247","aduviric300@gmail.com", cargoRepository.findById(6L).orElseThrow(), LocalDate.now()));


        } else {
            System.out.println("⚠️ Empleados ya existen, se omite seeding.");
        }
        };
    }
}
