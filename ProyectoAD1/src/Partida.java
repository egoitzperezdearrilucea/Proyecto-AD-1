import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Date;

public class Partida {
    //Variables
    LocalDateTime fecha;
    int puntuacionTotal = 0;
    Combate[] combates;
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

    public Combate[] getCombates() {
        return combates;
    }

    public void setCombates(Combate[] combates) {
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

    public static void exportarPartidaXML(Partida partida) {

        try {

            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("partida", Partida.class);

            xstream.useAttributeFor(Combate.class, "numero");
            xstream.aliasField("numero", Combate.class, "numero");

            xstream.useAttributeFor(Combate.class, "resultado");
            xstream.aliasField("resultado", Combate.class, "resultado");

            //Insertar los objetos en el XML
            xstream.toXML(partida, new FileOutputStream("./src/Partida_"+ LocalDateTime.now() +".xml"));

            System.out.println("Partida exportada correctamente a 'Partida_"+ LocalDateTime.now() +".xml'");
        }catch (Exception e)
        {e.printStackTrace();}
    }
}

class Combate{
    String jugador;
    String enemigo;
    String resultado;
    int numero;

    public Combate(String jugador, String enemigo, String resultado, int numero) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.resultado = resultado;
        this.numero = numero;
    }

    public String getJugador() {
        return jugador;
    }

    public String getEnemigo() {
        return enemigo;
    }

    public String getResultado() {
        return resultado;
    }

    public int getNumero() {
        return numero;
    }
}
