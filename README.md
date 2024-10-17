# MonsterHunter
## Jonay Contreras
Esta version consta de estas caracteristicas:

- El cazador se mueve
- El cazador hace daño a un monstruo
    - Si lo mata lo caza
    - Sino sigue vivo
- El monstruo se mueve
- El movimiento es de una posicion cercana
- Los monstruos se generan en un tiempo
- El mapa se muestra de la siguiente manera:
    ```
    · · C · · · · ·
    · · · · · · · ·
    · · · · · · · C
    · · · · · · · ·
    · · · · · · · ·
    · · · · · M C ·
    C · · · · · · ·
    · · · · · · · ·
    ```
- Se muestran estadisticas varias de la partida:
    ```
    Cazadores:
        Monstruos Cazados: 0
        Monstruos Cazados: 0
        Monstruos Cazados: 0
        Monstruos Cazados: 0
    Monstruos:
        Vida: 6
    Tiempo: 6
    ```

- Funcionamiento:
  - Cada hilo realiza las acciones sobre una clase estatica (monstruo ejecuta mapa.mover(this))
  - Evitar errores de concurrencia usando metodos synchronized en funciones de movimiento