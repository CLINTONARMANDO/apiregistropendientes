package com.clindevstudio.apiregistropendientes.modules.usuarios.dtos;

import lombok.Data;
import java.util.List;

@Data
public class ModuloRolSyncRequest {
    private Long rolId;
    private List<Long> moduloIds;
}