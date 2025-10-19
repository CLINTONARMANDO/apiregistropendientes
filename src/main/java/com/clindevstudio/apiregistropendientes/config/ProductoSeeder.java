package com.clindevstudio.apiregistropendientes.config;

import com.clindevstudio.apiregistropendientes.database.entities.Producto;
import com.clindevstudio.apiregistropendientes.database.repositories.EmpleadoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.ProductoRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.RolRepository;
import com.clindevstudio.apiregistropendientes.database.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class ProductoSeeder {
    @Bean
    CommandLineRunner initProductos(
            ProductoRepository productoRepository
    ) {
        return args -> {
            if (productoRepository.count() != 0) {
                System.out.println("⚠️ Productos ya existen, se omite seeding.");
                return;
            }

            List<Producto> productos = List.of(

                    // 🌐 INTERNET INALÁMBRICO (Plan Hogar)
                    Producto.builder()
                            .nombre("Plan 10 Mbps Hogar")
                            .descripcion("Internet inalámbrico hogar 10 Mbps")
                            .costoUnitario(BigDecimal.valueOf(29.90))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 20 Mbps Hogar")
                            .descripcion("Internet inalámbrico hogar 20 Mbps")
                            .costoUnitario(BigDecimal.valueOf(40.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 30 Mbps Hogar")
                            .descripcion("Internet inalámbrico hogar 30 Mbps")
                            .costoUnitario(BigDecimal.valueOf(50.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 40 Mbps Hogar")
                            .descripcion("Internet inalámbrico hogar 40 Mbps")
                            .costoUnitario(BigDecimal.valueOf(60.00))
                            .build(),

                    // 💼 CORPORATIVO INALÁMBRICO
                    Producto.builder()
                            .nombre("Plan 55 Mbps Corporativo GO")
                            .descripcion("Internet inalámbrico corporativo 55 Mbps")
                            .costoUnitario(BigDecimal.valueOf(75.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 65 Mbps Corporativo GO")
                            .descripcion("Internet inalámbrico corporativo 65 Mbps")
                            .costoUnitario(BigDecimal.valueOf(99.00))
                            .build(),

                    // 🧵 INTERNET FIBRA ÓPTICA
                    Producto.builder()
                            .nombre("Plan 100 Mbps Fibra")
                            .descripcion("Internet fibra óptica hogar 100 Mbps")
                            .costoUnitario(BigDecimal.valueOf(47.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 200 Mbps Fibra + TV")
                            .descripcion("Internet fibra óptica hogar 200 Mbps con TV")
                            .costoUnitario(BigDecimal.valueOf(59.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 300 Mbps Fibra + TV")
                            .descripcion("Internet fibra óptica hogar 300 Mbps con TV")
                            .costoUnitario(BigDecimal.valueOf(69.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 400 Mbps Fibra + TV")
                            .descripcion("Internet fibra óptica hogar 400 Mbps con TV")
                            .costoUnitario(BigDecimal.valueOf(79.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 500 Mbps Corporativo Fibra")
                            .descripcion("Internet fibra óptica corporativo 500 Mbps")
                            .costoUnitario(BigDecimal.valueOf(129.00))
                            .build(),

                    Producto.builder()
                            .nombre("Plan 700 Mbps Corporativo Fibra")
                            .descripcion("Internet fibra óptica corporativo 700 Mbps")
                            .costoUnitario(BigDecimal.valueOf(175.00))
                            .build(),
                    // ==================================================
                    // PRODUCTOS DE VIDEVIGILANCIA (Del Excel)
                    // ==================================================

                    // --- Cámaras y Grabación ---
                    Producto.builder()
                            .nombre("CAMARA TUBO")
                            .descripcion("Cámara de videovigilancia tipo Tubo (Bullet)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CAMARA DOMO")
                            .descripcion("Cámara de videovigilancia tipo Domo")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CAMARA PTZ")
                            .descripcion("Cámara de videovigilancia PTZ (Pan-Tilt-Zoom)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("DVR")
                            .descripcion("Grabador de video digital")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("NVR")
                            .descripcion("Grabador de video en red")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("DISCO DURO")
                            .descripcion("Disco duro para videovigilancia")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    // --- Conectores y Cables ---
                    Producto.builder()
                            .nombre("CABLE UTP")
                            .descripcion("Cable UTP para red y video (precio por metro)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CABLE COAXIAL")
                            .descripcion("Cable coaxial para video (precio por metro)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("VIDEO BALUN")
                            .descripcion("Conector Video Balun para UTP (par)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CONECTOR DC")
                            .descripcion("Conector de energía DC (Macho/Hembra)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("PATCH CORD")
                            .descripcion("Cable de red Patch Cord (varias longitudes)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("FACE PLATE")
                            .descripcion("Tapa de pared para conexión de red")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("DATA JACK")
                            .descripcion("Conector hembra RJ45 (Keystone Jack)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    // --- Alimentación y Gabinetes ---
                    Producto.builder()
                            .nombre("FUENTE PODER 12V 1A")
                            .descripcion("Fuente de poder 12V 1 Amperio")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("FUENTE PODER 12V 2A")
                            .descripcion("Fuente de poder 12V 2 Amperios")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("FUENTE PODER CENTRALIZADA")
                            .descripcion("Fuente de poder centralizada para cámaras")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("RACK 6RU")
                            .descripcion("Gabinete Rack de pared 6RU")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("RACK 9RU")
                            .descripcion("Gabinete Rack de pared 9RU")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    // --- Redes y Adicionales ---
                    Producto.builder()
                            .nombre("ROUTER")
                            .descripcion("Router inalámbrico")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("ACCESS POINT")
                            .descripcion("Punto de acceso WiFi")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("SWITCH")
                            .descripcion("Switch de red (ej. 8 puertos)")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("MONITOR 18.5 LG")
                            .descripcion("Monitor LG 18.5 pulgadas")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("MONITOR 21.5 LG")
                            .descripcion("Monitor LG 21.5 pulgadas")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    // --- Servicios ---
                    Producto.builder()
                            .nombre("INSTALACION KIT")
                            .descripcion("Servicio de instalación de kit de cámaras")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("PUNTO ADCIONAL")
                            .descripcion("Instalación de punto de cámara adicional")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CANALETEADO (METRO)")
                            .descripcion("Suministro e instalación de canaleta por metro")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build(),

                    Producto.builder()
                            .nombre("CONFIGURACION CELULAR")
                            .descripcion("Configuración adicional para visualización remota")
                            .costoUnitario(BigDecimal.valueOf(0.00)) // <-- REEMPLAZAR PRECIO
                            .build()
            );

            productoRepository.saveAll(productos);
            System.out.println("✅ Productos (planes) cargados correctamente.");

        };
    }
}
