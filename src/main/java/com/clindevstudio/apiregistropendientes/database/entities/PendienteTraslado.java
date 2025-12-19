package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.LugarPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoPendiente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoServicio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pendientes_traslados")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PendienteTraslado extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendienteId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pendiente_id")
    private Pendiente pendiente;


    // Lugar
    @Enumerated(EnumType.STRING)
    @Column(name = "lugar_destino", nullable = false)
    private LugarPendiente lugarDestino;

    @Column(name = "direccion_destino", length = 200)
    private String direccionDestino;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", length = 50)
    private TipoServicio tipoServicio;

    @Column(name = "vlan", length = 50)
    private String vlan;

    @Column(name = "ppoe", length = 50)
    private String ppoe;

    @Column(name = "ppoe_password", length = 50)
    private String ppoePassword;
}
