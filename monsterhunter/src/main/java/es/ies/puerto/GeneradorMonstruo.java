package es.ies.puerto;

import java.util.ArrayList;
import java.util.List;

public class GeneradorMonstruo implements Runnable {
    private Mapa mapa;
    private List<Monstruo> monstruos;

    public GeneradorMonstruo(Mapa mapa) {
        this.mapa = mapa;
        monstruos = new ArrayList<>();
    }

    @Override
    public void run() {
        double time = 0;
        while (true) {
            try {
                Monstruo monstruo = new Monstruo(mapa, 0, 0);
                mapa.agregarObjeto(monstruo);
                monstruos.add(monstruo);
                Thread monstruoThread = new Thread(monstruo);
                monstruoThread.start();
                Thread.sleep(8999);
                time += 3;
                if (time >= 6) {
                    Monstruo monstruoAEliminar = monstruos.get(0);
                    mapa.eliminarMonstruo(monstruoAEliminar);
                    monstruos.remove(0);
                    time = 0;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

}
