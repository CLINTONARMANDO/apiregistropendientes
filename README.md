## 🟢 Estados del Pendiente

```mermaid
stateDiagram-v2
    [*] --> Creado
    Creado --> Asignado : asignar
    Asignado --> EnProceso : iniciar
    EnProceso --> Resuelto : resolver
    Resuelto --> Cerrado : cerrar
