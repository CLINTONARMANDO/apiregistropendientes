package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modulos")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Modulo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private String icono;

    private String ruta;

    @ManyToOne
    @JoinColumn(name = "modulo_padre_id")
    private Modulo moduloPadre; // relación recursiva

    @Column(nullable = true)
    private Boolean vigente = true;
}
