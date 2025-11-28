package com.clindevstudio.apiregistropendientes.database.enums;

import java.util.ArrayList;
import java.util.List;

public enum Permiso {

    // 🔹 Registro
    REGISTRAR_PENDIENTE(1L, "Registrar nuevo pendiente"),
    EDITAR_PENDIENTE(2L, "Editar detalle del pendiente"),
    VER_DETALLE_PENDIENTE(4L, "Ver detalle del pendiente propio"),

    // 🔹 Asignación
    VER_TODOS_PENDIENTES(8L, "Ver todos los pendientes"),
    ASIGNAR_TECNICO(16L, "Asignar técnico a un pendiente"),
    ASIGNAR_PPOE(32L, "Asignar PPOE"),
    ASIGNAR_VLAN(64L, "Asignar VLAN"),

    // 🔹 Ejecución / Técnico
    COMENZAR_TRABAJO(128L, "Comenzar trabajo"),
    PARAR_TRABAJO(256L, "Pausar trabajo"),
    CONTINUAR_TRABAJO(512L, "Continuar trabajo"),
    FINALIZAR_TRABAJO(1024L, "Finalizar trabajo"),

    // 🔹 Revisión / Control
    VER_PENDIENTES_HISTORIAL(2048L, "Ver todos los pendientes en historial"),
    REVISAR_GASTOS(4096L, "Revisar gastos asociados"),
    REVISAR_FINALIZADOS(8192L, "Revisar pendientes finalizados");

    private final long bit;
    private final String descripcion;

    Permiso(long bit, String descripcion) {
        this.bit = bit;
        this.descripcion = descripcion;
    }

    public long getBit() {
        return bit;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Convierte bitmask → lista de permisos
    public static List<Permiso> fromBitmask(long permisos) {
        List<Permiso> result = new ArrayList<>();
        for (Permiso p : Permiso.values()) {
            if ((p.bit & permisos) != 0L) {
                result.add(p);
            }
        }
        return result;
    }

    // Convierte lista de permisos → bitmask
    public static long toBitmask(List<Permiso> permisos) {
        long acc = 0L;
        for (Permiso p : permisos) {
            acc |= p.bit;
        }
        return acc;
    }
}