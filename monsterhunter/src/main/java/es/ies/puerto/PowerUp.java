package es.ies.puerto;

public class PowerUp extends Objetos {

    @Override
    public void run() {
    }

    public PowerUp(Mapa mapa, int x, int y) {
        super(mapa, x, y);
    }

    public void coger(Objetos objeto) {
        if (objeto instanceof Cazador) {
            Cazador cazador = (Cazador) objeto;
            cazador.setDanioBase(cazador.getDanioBase() + 50);

        } else {
            Monstruo monstruo = (Monstruo) objeto;
            monstruo.setPuntosDeVida(monstruo.getPuntosDeVida() + 200);
        }

    }

}
