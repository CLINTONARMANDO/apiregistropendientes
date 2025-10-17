package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pendientes_averias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendienteAveria extends BaseEntity {

    @Id
    private Long pendienteId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pendiente_id")
    private Pendiente pendiente;

    @Column(name = "descripcion_falla", columnDefinition = "TEXT")
    private String descripcionFalla;
}
