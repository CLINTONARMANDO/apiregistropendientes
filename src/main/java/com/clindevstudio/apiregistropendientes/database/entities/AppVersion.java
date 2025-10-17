package com.clindevstudio.apiregistropendientes.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔢 Versión de la aplicación (por ejemplo: 1.0.0)
    @Column(nullable = false, length = 20)
    private String versionCode;

    // 📱 Plataforma (Android, iOS, Web)
    @Column(nullable = false, length = 20)
    private String plataforma;

    // ✅ Indica si esta versión es la mínima requerida
    @Column(nullable = false)
    private boolean obligatoria;

    // 📝 Descripción de cambios o mensaje de actualización
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // 📅 Fecha de publicación de esta versión
    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    // 🚀 Indica si sigue vigente
    @Column(nullable = false)
    private boolean vigente;
}
