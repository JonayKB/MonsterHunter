package es.ies.puerto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static double time = 0;
    static final int SIZE = 5;
    static Mapa mapa = new Mapa(SIZE);
    static GeneradorMonstruo generadorMonstruo = new GeneradorMonstruo(mapa);

    public static void main(String[] args) {
        List<Cazador> cazadores = new ArrayList<>();
        /**
         * for (int i = 1; i <= 4; i++) {
         * Cazador cazador = new Cazador(mapa, i, 0);
         * cazadores.add(cazador);
         * Thread cazadorThread = new Thread(cazador);
         * cazadorThread.start();
         * }
         */
        // Thread generadorMonstruosThread = new Thread(generadorMonstruo);
        // generadorMonstruosThread.start();
        Monstruo monstruo = new Monstruo(mapa, 3, 3);
        mapa.agregarObjeto(monstruo);
        Thread monstruoThread = new Thread(monstruo);
        monstruoThread.start();
        Monstruo monstruo2 = new Monstruo(mapa, 3, 3);
        mapa.agregarObjeto(monstruo2);
        Thread monstruoThread2 = new Thread(monstruo2);
        monstruoThread2.start();
        Cueva cueva = new Cueva(mapa, 0, 0);
        mapa.agregarObjeto(cueva);
        Thread cuevaThread = new Thread(cueva);
        cuevaThread.start();
        while (true) {
            mostrarMapa(mapa, cazadores);
            try {
                Thread.sleep(200);
                time += 0.2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (time >= 30) {
                mostrarMapa(mapa, cazadores);
                System.exit(0);
            }

        }
    }

    public static void mostrarMapa(Mapa mapa, List<Cazador> cazadores) {
        // System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(mapa.mostrarMapa());
        System.out.println("Cazadores:");
        for (Cazador cazador : cazadores) {
            System.out.println("    Monstruos Cazados: " + cazador.getMonstruosCazados());
        }
        System.out.println("Monstruos:");
        for (Monstruo monstruo : generadorMonstruo.getMonstruos()) {
            if (monstruo != null && monstruo.getPuntosDeVida() > 0) {
                System.out.println("    Vida: " + monstruo.getPuntosDeVida());

            }

        }
        System.out.println("Tiempo: " + (int) time);

    }
}