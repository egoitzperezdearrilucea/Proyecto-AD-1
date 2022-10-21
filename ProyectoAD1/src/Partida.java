import java.time.LocalDateTime;
import java.util.Date;

public class Partida {
    //Variables
    LocalDateTime fecha;
    int puntuacionTotal = 0;
    String[] combates;
    String nombreJugador;

    //Constructor
    public Partida(LocalDateTime fecha, String nombreJugador) {
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

    public String[] getCombates() {
        return combates;
    }

    public void setCombates(String[] combates) {
        this.combates = combates;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
}
