package com.clindevstudio.apiregistropendientes.modules.clientes.dtos;

import com.clindevstudio.apiregistropendientes.database.enums.TipoCliente;
import com.clindevstudio.apiregistropendientes.database.enums.TipoDocumento;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private TipoCliente tipoCliente;
    private String nombre;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private String contactoRepresentante;
}
