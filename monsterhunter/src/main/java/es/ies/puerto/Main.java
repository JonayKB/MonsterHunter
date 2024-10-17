package es.ies.puerto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static double time = 0;
    static Mapa mapa = new Mapa(8);
    static GeneradorMonstruo generadorMonstruo = new GeneradorMonstruo(mapa);

    public static void main(String[] args) {
        List<Cazador> cazadores = new ArrayList<>();
        Cazador cazador = new Cazador(mapa, 0, 0);
        Cazador cazador2 = new Cazador(mapa, 1, 0);
        Cazador cazador3 = new Cazador(mapa, 2, 0);
        Cazador cazador4 = new Cazador(mapa, 3, 0);

        cazadores.add(cazador);
        cazadores.add(cazador2);
        cazadores.add(cazador3);
        cazadores.add(cazador4);
        Thread generadorMonstruosThread = new Thread(generadorMonstruo);

        Thread cazadorThread = new Thread(cazador);
        Thread cazador2Thread = new Thread(cazador2);
        Thread cazador3Thread = new Thread(cazador3);
        Thread cazador4Thread = new Thread(cazador4);

        cazadorThread.start();
        cazador2Thread.start();
        cazador3Thread.start();
        cazador4Thread.start();
        generadorMonstruosThread.start();
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(mapa.mostrarMapa());
        System.out.println("Cazadores:");
        for (Cazador cazador : cazadores) {
            System.out.println("    Monstruos Cazados: " + cazador.getMonstruosCazados());
        }
        System.out.println("Monstruos:");
        for (Monstruo monstruo : generadorMonstruo.getMonstruos()) {
            if (monstruo != null && monstruo.getPuntosDeVida()>0) {
                System.out.println("    Vida: " + monstruo.getPuntosDeVida());

            }

        }
        System.out.println("Tiempo: " + (int) time);

    }
}