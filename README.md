# MonsterHunter
## Jonay Contreras
Esta version consta de estas caracteristicas:

- El cazador se mueve
- El cazador hace daño a un monstruo
    - Si lo mata lo caza
    - Sino sigue vivo
- El monstruo se mueve
- El movimiento es de una posicion cercana
- Los monstruos se pueden ocultar en una cueva
- Hay power Ups que aumentan la vida del mosntruo o el daño del cazador
- El mapa se muestra de la siguiente manera:
    ```
    · M · · · C · M · H
    · · · · · · · · · ·
    · · · · · · · · · ·
    · · · · · H · H · ·
    · · · · · M · · · ·
    H · · · P · · · · ·
    · · · · · · · · · ·
    · · M · · · · · · ·
    · · · · · M · · · ·
    · · · · · M · · · M

    Cazadores:
        Monstruos Cazados: 3
        Monstruos Cazados: 0
        Monstruos Cazados: 0
        Monstruos Cazados: 1
    Monstruos:
        Vida: 35
        Vida: 8
        Vida: 72
        Vida: 65
        Vida: 50
        Vida: 2
    Tiempo: 7
    ```

- Funcionamiento:
  - Cada hilo realiza las acciones sobre una clase estatica (monstruo ejecuta mapa.mover(this))
  - Evitar errores de concurrencia usando metodos synchronized en funciones de movimiento
  - La cueva es un objeto en el mapa, que si un monstruo va a moverse se mete dentro si hay espacio disponible
  - Si se mueve a la misma posicion, o a una posicion ocupada, este no se mueve
  - Movimiento del hunter hacia el monstruo, normalizado