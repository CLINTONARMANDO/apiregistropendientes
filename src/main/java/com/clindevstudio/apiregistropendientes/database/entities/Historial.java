package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "historiales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Historial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Relación con el pendiente afectado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pendiente_id", nullable = false)
    private Pendiente pendiente;

    // 🔹 Empleado que realizó la acción
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Estado actual
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPendiente estado;

    // 🔹 Comentario u observación adicional
    @Column(length = 500)
    private String detalles;
}
