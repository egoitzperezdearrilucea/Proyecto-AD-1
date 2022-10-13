import java.util.Date;

public class Partida {
    //Variables
    int puntuacionTotal;
    int[] puntuaciones;
    Date fecha;
    String nombreJugador;

    //Constructor
    public Partida(Date fecha, String nombreJugador) {
        this.fecha = fecha;
        this.nombreJugador = nombreJugador;
    }

    //Getter y Setter s
    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public int[] getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(int[] puntuaciones) {
        this.puntuaciones = puntuaciones;
    }
}
