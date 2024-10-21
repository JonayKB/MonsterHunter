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

    public Objetos[][] generarMapa(int size) {
        Objetos[][] mapaGenerado = new Objetos[size][size];
        return mapaGenerado;
    }

    public int[] generarUbicacionAleatoria() {
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);
        return new int[] { x, y };
    }

    public int[] generarUbicacionAleatoriaCercana(int[] posicionActual) {
        int x = posicionActual[0];
        int y = posicionActual[1];
        boolean xBolean = (Math.random() * 10) > 5;
        boolean yBolean = (Math.random() * 10) > 5;
        if (xBolean) {
            x = (x + 1) % size;
        } else {
            x = (x - 1 + size) % size;
        }
        if (yBolean) {
            y = (y + 1) % size;
        } else {
            y = (y - 1 + size) % size;
        }
        return new int[] { x, y };

    }

    public synchronized void agregarObjeto(Objetos objeto) {
        int[] posicionAleatoria = generarUbicacionAleatoria();
        while (ubicaciones[posicionAleatoria[0]][posicionAleatoria[1]] != null) {
            posicionAleatoria = generarUbicacionAleatoria();
        }
        objeto.setX(posicionAleatoria[0]);
        objeto.setY(posicionAleatoria[1]);
        ubicaciones[posicionAleatoria[0]][posicionAleatoria[1]] = objeto;

    }

    public synchronized void moverObjeto(Objetos objeto) {
        int[] posicionActual = objeto.getPosicion();
        int[] nuevaPosicion = generarUbicacionAleatoriaCercana(posicionActual);
        if (nuevaPosicion[0] == posicionActual[0] && nuevaPosicion[1] == posicionActual[1]) {
            return;
        }

        if (objeto instanceof Cazador) {
            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Monstruo) {
                Cazador cazador = (Cazador) objeto;
                cazar(cazador, (Monstruo) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]]);
            }
            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] != null) {
                return;
            }

        } else {
            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Cueva) {
                Monstruo monstruo = (Monstruo) objeto;
                Cueva cueva = (Cueva) ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]];

                new Thread(() -> {
                    monstruo.intentarEntrarCueva(cueva);

                }).start();

            }
            if (ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] != null) {
                return;
            }
        }
        ubicaciones[posicionActual[0]][posicionActual[1]] = null;
        objeto.setX(nuevaPosicion[0]);
        objeto.setY(nuevaPosicion[1]);
        ubicaciones[nuevaPosicion[0]][nuevaPosicion[1]] = objeto;
    }

    private synchronized void cazar(Cazador cazador, Monstruo monstruo) {
        int[] posicionMonstruo = monstruo.getPosicion();
        int danio = cazador.getDanio();

        if (monstruo.sufrirDanio(danio) <= 0) {
            cazador.cazarMonstruo();
            ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] = null;
            monstruo = null;

        }

    }

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
                    mapaVisualizado.append(" M");
                }
            }
            mapaVisualizado.append("\n");
        }
        return mapaVisualizado.toString();

    }

    public synchronized void eliminarMonstruo(Monstruo monstruo) {
        int[] posicionMonstruo = monstruo.getPosicion();
        monstruo.setStatus(false);
        if (ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] instanceof Monstruo) {
            ubicaciones[posicionMonstruo[0]][posicionMonstruo[1]] = null;
        }
        monstruo = null;
    }

}
