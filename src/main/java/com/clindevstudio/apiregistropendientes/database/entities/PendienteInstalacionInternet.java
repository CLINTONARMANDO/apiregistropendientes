package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoInstalacion;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pendientes_instalaciones_internet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteInstalacionInternet extends BaseEntity {

    @Id
    private Long pendienteId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pendiente_id")
    private Pendiente pendiente;

    @Column(name = "velocidad_solicitada", length = 50)
    private String velocidadSolicitada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instalacion")
    private TipoInstalacion tipoInstalacion;
}

