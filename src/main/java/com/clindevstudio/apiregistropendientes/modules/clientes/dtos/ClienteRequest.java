package com.clindevstudio.apiregistropendientes.modules.clientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoCliente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoDocumento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    private TipoCliente tipoCliente;          // PERSONA o EMPRESA
    private String nombre;                    // Razón social o nombre completo
    private TipoDocumento tipoDocumento;      // DNI, RUC, CE, etc.
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private String contactoRepresentante;     // Opcional (solo empresas)
}
