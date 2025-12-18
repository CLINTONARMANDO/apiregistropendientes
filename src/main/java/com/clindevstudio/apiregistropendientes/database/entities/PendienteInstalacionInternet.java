package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoTecnico;
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

    @Column(name = "costo_fijo", length = 50)
    private String costoFijo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instalacion")
    private TipoInstalacion tipoInstalacion;

    @Column(name = "vlan", length = 50)
    private String vlan;

    @Column(name = "ppoe", length = 50)
    private String ppoe;

    @Column(name = "ppoe_password", length = 50)
    private String ppoePassword;
}

