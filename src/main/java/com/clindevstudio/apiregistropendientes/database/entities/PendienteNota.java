package com.clindevstudio.apiregistropendientes.database.entities;

import com.clindevstudio.apiregistropendientes.database.base.BaseEntity;
import com.clindevstudio.apiregistropendientes.database.enums.EstadoPendiente;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pendientes_notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendienteNota extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el pendiente principal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pendiente_id", nullable = false)
    private Pendiente pendiente;

    // Empleado que dejó la nota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Texto de la nota
    @Column(columnDefinition = "TEXT")
    private String nota;

    // Estado del pendiente en el momento en que se escribió la nota
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pendiente", nullable = false)
    private EstadoPendiente estadoPendiente;

    // Relación uno a muchos con las imágenes
    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PendienteNotaImagen> imagenes;
}
