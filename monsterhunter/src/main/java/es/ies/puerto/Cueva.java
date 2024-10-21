package es.ies.puerto;

import java.util.concurrent.Semaphore;

public class Cueva extends Objetos {

    private Semaphore semaphore;

    public Cueva(Mapa mapa, int x, int y) {
        super(mapa, x, y);
        this.semaphore = new Semaphore(mapa.getSize()/2);
    }

    @Override
    public void run() {

    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

}
