## 🟢 Estados del Pendiente

```mermaid
stateDiagram-v2
    [*] --> REGISTRADO

    REGISTRADO --> ASIGNADO : Asignar Tecnico
    REGISTRADO --> CANCELADO : Cancelar
    REGISTRADO --> POSTERGADO : Postergar

    ASIGNADO --> EN_PROGRESO : Comenzar trabajo
    ASIGNADO --> REGISTRADO : Reasignar
    ASIGNADO --> CANCELADO : Cancelar


    EN_PROGRESO --> PAUSADO : Parar trabajo
    EN_PROGRESO --> POR_VALIDAR : Terminar trabajo
    EN_PROGRESO --> CANCELADO : Cancelar
    EN_PROGRESO --> REGISTRADO : Reasignar

    PAUSADO --> POR_VALIDAR : Terminar trabajo
    PAUSADO --> EN_PROGRESO : Continuar trabajo
    PAUSADO --> CANCELADO : Cancelar
    PAUSADO --> REGISTRADO : Reasignar

    POR_VALIDAR --> FINALIZADO : Finalizar
    POR_VALIDAR --> OBSERVADO : Observar

    OBSERVADO --> POR_VALIDAR : Validar

    POSTERGADO --> ASIGNADO : Asignar Tecnico
    POSTERGADO --> POSTERGADO : Postergar

    FINALIZADO --> [*]

    CANCELADO --> POSTERGADO : Postergar
```
## 🟢 Estados del Pendiente

```mermaid

stateDiagram-v2
    [*] --> OK

    OK --> PENDIENTE_PPPoE : Solicitar PPPoE
    OK --> PENDIENTE_VLAN : Solicitar VLAN

    PENDIENTE_PPPoE --> PENDIENTE_PPPoE_Y_VLAN : Solicitar VLAN
    PENDIENTE_VLAN --> PENDIENTE_PPPoE_Y_VLAN : Solicitar PPPoE

    PENDIENTE_PPPoE --> OK : PPPoE configurado
    PENDIENTE_VLAN --> OK : VLAN configurada
    PENDIENTE_PPPoE_Y_VLAN --> OK : Configuración completa
```
