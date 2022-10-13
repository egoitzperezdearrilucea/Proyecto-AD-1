import java.io.*;

public class Main {
    public static void main(String[] args) {
        //Variables
        Partida partida;
        Jugador jugador;
        Enemigo[] enemigos;





    }

    //Importar datos

    public Jugador importarJugadorDAT() throws IOException, ClassNotFoundException {

        File fichero = new File ("./src/PersonaDat.dat");

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

    public void exportarJugadorDAT(Jugador jugador) throws IOException {
        File fichero = new File ("./src/Jugador.dat");

        FileOutputStream fileout = new FileOutputStream(fichero);

        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

        dataOS.writeObject(jugador);

    }

    public void exportarJugadorXML(Jugador jugador){


    }

}