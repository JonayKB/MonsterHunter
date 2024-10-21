package es.ies.puerto;

public class Cazador extends Objetos {
    private int monstruosCazados;
    private int danioBase = 1;

    public Cazador(int id, Mapa mapa, int x, int y) {
        super(id, mapa, x, y);
    }

    public Cazador(Mapa mapa, int x, int y) {
        super(mapa, x, y);
    }

    @Override
    public void run() {
        while (true) {

            try {
                getMapa().moverObjeto(this);
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int getMonstruosCazados() {
        return this.monstruosCazados;
    }

    public void setMonstruosCazados(int monstruosCazados) {
        this.monstruosCazados = monstruosCazados;
    }

    public void cazarMonstruo() {
        this.monstruosCazados++;
    }

    public int getDanio() {

        return (int) (Math.random() * 100)+getDanioBase();
    }

    public int getDanioBase() {
        return this.danioBase;
    }

    public void setDanioBase(int danioBase) {
        this.danioBase = danioBase;
    }

}
