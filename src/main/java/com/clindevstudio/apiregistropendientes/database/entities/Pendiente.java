package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pendientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pendiente extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_pendiente")
    private LocalDateTime fechaPendiente;

    // Empleado que registró el pendiente (asesor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por", nullable = false)
    private Empleado registradoPor;

    // Empleado asignado (técnico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignado_a")
    private Empleado asignadoA;

    // Estado actual
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPendiente estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_tecnico")
    private EstadoTecnico estadoTecnico;

    // Lugar
    @Enumerated(EnumType.STRING)
    @Column(name = "lugar", nullable = false)
    private LugarPendiente lugar;

    @Enumerated(EnumType.STRING)
    @Column(name = "empresa")
    private EmpresaPendiente empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private PrioridadPendiente prioridad;

    @Column(length = 200)
    private String direccion;

    // Tipo de pendiente
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPendiente tipo;

    // Cliente asociado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}