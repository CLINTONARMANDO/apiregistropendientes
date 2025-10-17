package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pendientes_instalaciones_camaras")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteInstalacionCamaras extends BaseEntity {

    @Id
    private Long pendienteId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pendiente_id")
    private Pendiente pendiente;
}
