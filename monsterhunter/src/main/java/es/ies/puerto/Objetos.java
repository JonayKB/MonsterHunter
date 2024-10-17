package es.ies.puerto;

import java.util.Objects;

public abstract class Objetos implements Runnable {
    private int id;
    private Mapa mapa;
    private int x;
    private int y;

    public Objetos(Mapa mapa, int x, int y) {
        this.id = (int) (Math.random() * 1000000);
        this.mapa = mapa;
        this.x = x;
        this.y = y;
    }

    public Objetos(int id, Mapa mapa, int x, int y) {
        this.id = id;
        this.mapa = mapa;
        this.x = x;
        this.y = y;
    }

    public int[] getPosicion() {
        return new int[] {x,y};
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Objetos)) {
            return false;
        }
        Objetos objetos = (Objetos) o;
        return id == objetos.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
