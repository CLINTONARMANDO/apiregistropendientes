package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.TipoCliente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private TipoCliente tipoCliente; // PERSONA o EMPRESA

    @Column(length = 150, nullable = false)
    private String nombre; // Razón social o nombre completo

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento; // DNI, RUC, CE, etc.

    @Column(length = 200)
    private String numeroDocumento;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(name = "contacto_representante", length = 150)
    private String contactoRepresentante; // opcional para empresas
}