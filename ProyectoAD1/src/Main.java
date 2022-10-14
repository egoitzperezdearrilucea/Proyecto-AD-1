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

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Variables
        Partida partida;
        Jugador jugador = null;
        Enemigo[] enemigos = new Enemigo[10];
        String opcion;
        Boolean correcto = false;


        //Opciones jugador
        correcto = false;
        while (!correcto){
            System.out.println("");
            System.out.println("Jugador:");
            System.out.println("1-Crear nuevo");
            System.out.println("2-Cargar .dat");
            opcion = br.readLine();

            switch (opcion){
                case ("1"):{
                    jugador = crearJugador();
                    correcto = true;
                }break;

                case ("2"):{
                    jugador = importarJugadorDAT();
                    correcto = true;
                }break;

                default: {
                    System.out.println("no se entiende el input");
                    correcto = false;
                }

            }
        }

        System.out.println("Jugador cargado: " + jugador.getNombre());



    }

    //Funciones
    public static Jugador crearJugador() throws IOException {
        String nombre;
        String opcion = null;
        Boolean correcto = false;

        System.out.println("Nombre jugador:");
        nombre = br.readLine();

        System.out.println("Vida = 100");
        System.out.println("Ataque = 25");

        Jugador jugador = new Jugador(nombre, 100, 25, 0);


        while (!correcto) {
            System.out.println("");
            System.out.println("Desea exportar el jugador?");
            System.out.println("1-No");
            System.out.println("2-Si");
            opcion=br.readLine();

            switch (opcion) {
                case ("1"): {

                    correcto = true;
                }
                break;

                case ("2"): {
                    exportarJugadorDAT(jugador);
                    exportarJugadorXML(jugador);
                    correcto = true;
                }
                break;

                default: {
                    System.out.println("no se entiende el input");
                    correcto = false;
                }
            }
        }

        return jugador;

    }




    //Importar datos
    public static Jugador importarJugadorDAT() throws IOException, ClassNotFoundException {

        File fichero = new File ("./src/Jugador.dat");

        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);

        Jugador  jugador = (Jugador) dataIS.readObject();

        System.out.println("");
        System.out.println("Datos cargados de el jugador:");
        System.out.println("Nombre:" + jugador.getNombre());
        System.out.println("Nivel:" + jugador.getNivel());
        System.out.println("Vida:" + jugador.getVida());
        System.out.println("Ataque:" + jugador.getAtaque());
        return jugador;
    }

    public static Enemigo[] importarEnemigosDAT() throws IOException, ClassNotFoundException {

        File fichero = new File ("./src/Enemigos.dat");

        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);

        Enemigo[] enemigos = new Enemigo[10];

        for (int i = 0; i < 10; i++) {

            enemigos[i] = (Enemigo) dataIS.readObject();

            System.out.println(enemigos[i].getNombre());

        }
        return enemigos;
    }


    //Exportar datos

    public static void exportarEnemigosDAT(Enemigo[] enemigos) throws IOException {

        File fichero = new File ("./src/Enemigos.dat");
        FileOutputStream fileout = new FileOutputStream(fichero);
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

        for (int i = 0; i < enemigos.length; i++) {
            dataOS.writeObject(enemigos[i]);
        }

    }

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
            xstream.toXML(jugador, new FileOutputStream("./src/Jugador.xml"));
        }catch (Exception e)
        {e.printStackTrace();}



    }

}