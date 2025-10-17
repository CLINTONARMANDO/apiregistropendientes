package com.clindevstudio.apiregistropendientes.database.specifications;

import com.clindevstudio.apiregistropendientes.database.entities.Pendiente;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import com.clindevstudio.apiregistropendientes.modules.pendientes.dtos.FiltroPendienteRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PendienteSpecification {

    public static Specification<Pendiente> conFiltros(FiltroPendienteRequest filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (filtro.getUsuarioId() != null) {
                Predicate asignado = cb.equal(root.get("asignadoA").get("id"), filtro.getUsuarioId());
                Predicate registrado = cb.equal(root.get("registradoPor").get("id"), filtro.getUsuarioId());
                Predicate combinado = cb.or(asignado, registrado);
                predicates.add(combinado);
                System.out.println("📋 Filtro aplicado por usuarioId = " + filtro.getUsuarioId());
            }

            if (filtro.getClienteId() != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), filtro.getClienteId()));
            }

            if (filtro.getAsignadoAId() != null) {
                predicates.add(cb.equal(root.get("asignadoA").get("id"), filtro.getAsignadoAId()));
            }

            if (filtro.getRegistradoPorId() != null) {
                predicates.add(cb.equal(root.get("registradoPor").get("id"), filtro.getRegistradoPorId()));
            }

            if (filtro.getEstados() != null && !filtro.getEstados().isEmpty()) {
                predicates.add(root.get("estado").in(filtro.getEstados()));
            }

            if (filtro.getTipo() != null) {
                predicates.add(cb.equal(root.get("tipo"), filtro.getTipo()));
            }

            if (filtro.getFechaPendiente() != null) {
                predicates.add(cb.equal(root.get("fechaPendiente"), filtro.getFechaPendiente()));
            }

            if (filtro.getPrioridad() != null) {
                predicates.add(cb.equal(root.get("prioridad"), filtro.getPrioridad()));
            }

            if (filtro.getLugar() != null) {
                predicates.add(cb.equal(root.get("lugar"), filtro.getLugar()));
            }

            if (filtro.getTitulo() != null && !filtro.getTitulo().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("titulo")), "%" + filtro.getTitulo().toLowerCase() + "%")
                );
            }

            // 🔥 Combina todos los predicados con AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

