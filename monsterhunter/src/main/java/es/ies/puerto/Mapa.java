package es.ies.puerto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapa {
    private Objetos[][] ubicaciones;

    private int size;

    public Mapa(int size) {
        this.ubicaciones = generarMapa(size);
        this.size = size;
    }

    public Mapa(int size, Objetos[][] ubicaciones) {
        this.ubicaciones = ubicaciones;
        this.size = size;
    }

    /**
     * Genera el mapa inicial
     * 
     * @param size tamaño del mapa
     * @return mapa
     */
    public Objetos[][] generarMapa(int size) {
        return new Objetos[size][size];
    }

    /**
     * Genera una ubicacion aletoria
     * 
     * @return ubicacion aleatoria
     */
    public int[] generarUbicacionAleatoria() {
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);
        return new int[] { x, y };
    }

    /**
     * Genera una ubicacion aletoria cercana a la actual
     * 
     * @param posicionActual posicion actual
     * @return ubicacion aleatoria cercana
     */
    public int[] generarUbicacionAleatoriaCercana(int[] posicionActual) {
        int x = posicionActual[0];
        int y = posicionActual[1];
        boolean xBolean = (Math.random() * 10) > 5;
        boolean yBolean = (Math.random() * 10) > 5;
        if (xBolean) {
            x = (x + 1) % size;
        } else {
            x = ((x - 1) % size) < 0 ? 0 : ((x - 1) % size);
        }
        if (yBolean) {
            y = (y + 1) % size;
        } else {
            y = ((y - 1) % size) < 0 ? 0 : ((y - 1) % size);
        }
        return new int[] { x, y };

    }

    /**
     * Se obtiene la posicion que tendria que dirigirse al monsturo mas cercano
     * 
     * @param posCazador posicion incial
     * @return poisicion a la que se movera
     */
    public synchronized int[] obtenerPosicionMonstruoMasCercano(int[] posCazador) {
        int[] posicionCercana = null;
        int minDistancia = Integer.MAX_VALUE;
        int[] direccion = new int[2];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ubicaciones[i][j] instanceof Monstruo) {
                    int distancia = Math.abs(i - posCazador[0]) + Math.abs(j - posCazador[1]);

                    if (distancia < minDistancia) {
                        minDistancia = distancia;
                        posicionCercana = new int[] { i, j };

                        direccion[0] = Integer.signum(i - posCazador[0]);
                        direccion[1] = Integer.signum(j - posCazador[1]);
                    }
                }
            }
        }
        return new int[] {
                posCazador[0] + direccion[0],
                posCazador[1] + direccion[1]
        };
    }

    public int comprobarDistanciaMinima(int[] posCazador, int minDistancia, int i, int j) {
        int distancia = Math.abs(i - posCazador[0]) + Math.abs(j - posCazador[1]);
        if (distancia < minDistancia) {
            minDistancia = distancia;
            return minDistancia;
        }
        return minDistancia;
    }

    /**
     * Agrega un objeto al mapa en una ubicacion aleatoria
     * 
     * @param objeto a añadir
     */
    public synchronized void agregarObjeto(Objetos objeto) {
        int[] posicionAleatoria = generarUbicacionAleatoria();
        while (ubicaciones[posicionAleatoria[0]][posicionAleatoria[1]] != null) {
            posicionAleatoria = generarUbicacionAleatoria();
        }
        objeto.setX(posicionAleatoria[0]);
        objeto.setY(posicionAleatoria[1]);
        ubicaciones[posicionAleatoria[0]][posicionAleatoria[1]] = objeto;

    }

    /**
     * Mover un objeto a una posicion cercana en el mapa
     * Si es un cazador puede cazar
     * Si es un monstruo se puede meter en la cueva
     * 
     * @param objeto a mover
     */
    public synchronized void moverObjeto(Objetos objeto) {
        int[] posicionActual = objeto.getPosicion();
        int[] nuevaPosicion = null;

        if (objeto instanceof Cazador) {
            nuevaPosicion = obtenerPosicionMonstruoMasCercano(posicionActual);
            Cazador cazador = (Cazador) objeto;

            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Monstruo) {
                cazar(cazador, (Monstruo) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]]);
            } else if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]];
                powerUp.coger(cazador);
                ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] = null;

            } else if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] != null) {
                return;
            }

        } else {
            nuevaPosicion = generarUbicacionAleatoriaCercana(posicionActual);
            Monstruo monstruo = (Monstruo) objeto;
            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Cueva) {

                Cueva cueva = (Cueva) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]];

                new Thread(() -> monstruo.intentarEntrarCueva(cueva)).start();

            } else if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]];
                powerUp.coger(monstruo);
                ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] = null;

            } else if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] != null) {
                return;
            }
        }
        ubicaciones[posicionActual[0]][posicionActual[1]] = null;
        objeto.setX(nuevaPosicion[0]);
        objeto.setY(nuevaPosicion[1]);
        ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] = objeto;
    }

    /**
     * Caza a un monstruo
     * 
     * @param cazador  que lo caza
     * @param monstruo a cazar
     */
    private synchronized void cazar(Cazador cazador, Monstruo monstruo) {
        int[] posicionMonstruo = monstruo.getPosicion();
        int danio = cazador.getDanio();

        if (monstruo.sufrirDanio(danio) <= 0) {
            cazador.cazarMonstruo();
            ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] = null;
            monstruo = null;

        }

    }

    /**
     * Enseña el mapa a un string
     * 
     * @return string del mapa
     */
    public String mostrarMapa() {
        StringBuilder mapaVisualizado = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ubicaciones[i][j] == null) {
                    mapaVisualizado.append(" ·");
                } else if (ubicaciones[i][j] instanceof Cazador) {
                    mapaVisualizado.append(" H");
                } else if (ubicaciones[i][j] instanceof Cueva) {
                    mapaVisualizado.append(" C");
                } else if (ubicaciones[i][j] instanceof Monstruo) {
                    mapaVisualizado.append(" m");
                } else if (ubicaciones[i][j] instanceof PowerUp) {
                    mapaVisualizado.append(" P");
                }
            }
            mapaVisualizado.append("\n");
        }
        return mapaVisualizado.toString();

    }

    /**
     * Elimina un monstruo del juego
     * 
     * @param monstruo a eliminar
     */
    public synchronized void eliminarMonstruo(Monstruo monstruo) {
        int[] posicionMonstruo = monstruo.getPosicion();
        monstruo.setStatus(false);
        if (ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] instanceof Monstruo) {
            ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] = null;
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
