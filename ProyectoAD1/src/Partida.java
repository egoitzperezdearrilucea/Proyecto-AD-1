import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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


    //Exportar Partida

    public static void exportarPartidaXML(Partida partida) throws FileNotFoundException {

        try {

            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("partida", Partida.class);
            //Insrtar los objetos en el XML
            xstream.toXML(partida, new FileOutputStream("./src/Partida_"+ LocalDateTime.now() +".xml"));

            System.out.println("Partida exportada correctamente a 'Partida_"+ LocalDateTime.now() +".xml'");
        }catch (Exception e)
        {e.printStackTrace();}
    }
}
