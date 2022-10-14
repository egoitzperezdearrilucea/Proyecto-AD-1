import com.thoughtworks.xstream.XStream;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Variables
        Partida partida;
        Jugador jugador;
        Enemigo[] enemigos;
        



    }

    //Importar datos

    public static Jugador importarJugadorDAT() throws IOException, ClassNotFoundException {

        File fichero = new File ("./src/Jugador.dat");

        FileInputStream filein = new FileInputStream(fichero);

        ObjectInputStream dataIS = new ObjectInputStream(filein);

        Jugador  jugador = (Jugador) dataIS.readObject();

        System.out.println("Datos cargados de el jugador:");
        System.out.println("Nombre:" + jugador.getNombre());
        System.out.println("Nivel:" + jugador.getNivel());
        System.out.println("Vida:" + jugador.getVida());
        System.out.println("Ataque:" + jugador.getAtaque());
        return jugador;
    }


    //Exportar datos

    public static void exportarJugadorDAT(Jugador jugador) throws IOException {

        File fichero = new File ("./src/Jugador.dat");
        FileOutputStream fileout = new FileOutputStream(fichero);
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

        dataOS.writeObject(jugador);

    }

    public static void exportarJugadorXML(Jugador jugador) throws FileNotFoundException {

        try {
            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("jugador", Jugador.class);
            //Insrtar los objetos en el XML
            xstream.toXML(jugador, new FileOutputStream("Jugador.xml"));
        }catch (Exception e)
        {e.printStackTrace();}



    }

}