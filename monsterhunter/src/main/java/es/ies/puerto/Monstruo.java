package es.ies.puerto;

public class Monstruo extends Objetos {
    private int puntosDeVida;
    private boolean status = true;

    public Monstruo(Mapa mapa, int x, int y) {
        super(mapa, x, y);
        this.puntosDeVida = (int) (Math.random() * 100);
    }

    @Override
    public void run() {
        while (true) {
            getMapa().moverObjeto(this);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getPuntosDeVida() <= 0) {
                getMapa().eliminarMonstruo(this);
                break;
            }
            if (status == false) {
                break;
            }
        }
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    public void setPuntosDeVida(int puntosDeVida) {
        this.puntosDeVida = puntosDeVida;
    }

    public int sufrirDanio(int danio) {
        this.puntosDeVida -= danio;
        return this.puntosDeVida;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
