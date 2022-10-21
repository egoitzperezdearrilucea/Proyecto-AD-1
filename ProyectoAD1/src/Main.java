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
import java.time.LocalDateTime;


public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Variables
        Partida partida;
        Jugador jugador = null;
        Enemigo[] enemigos = new Enemigo[10];
        String[] resultados = new String[500];
        String opcion;
        boolean correcto = false;
        boolean fin = false;

        //Creacion de enemigos (Solo usar para crear .dat)
        /*
        enemigos[0] = new Enemigo("Master False Ant", 50, 5);
        enemigos[1] = new Enemigo("The Greasy Barbarian", 50, 10);
        enemigos[2] = new Enemigo("Lord Molten Katana", 100, 5);
        enemigos[3] = new Enemigo("The Puzzling Devil", 100, 5);
        enemigos[4] = new Enemigo("Doctor Light Spectacle", 100, 10);
        enemigos[5] = new Enemigo("Doctor Black Smasher", 100, 10);
        enemigos[6] = new Enemigo("The Mute Swine", 100, 20);
        enemigos[7] = new Enemigo("Doctor Genius", 150, 20);
        enemigos[8] = new Enemigo("Professor Dark Arsonist", 150, 25);
        enemigos[9] = new Enemigo("Nuclear Macaw", 200, 25);
        exportarEnemigosDAT(enemigos);
         */

        //Importar enemigos
        enemigos = importarEnemigosDAT();

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

        //Crear partida
        partida = new Partida(LocalDateTime.now(), jugador.getNombre());

        int nCombate = 0;
        while (!fin){
            //Seleccionar enemigo (falta)
            Enemigo enemigoSeleccionado = seleccionarEnemigo(enemigos);

            //turno
            Boolean combate = combate(jugador, enemigoSeleccionado, partida);

            //Comprobar resultado
            if (!combate){
                System.out.println("");
                resultados[nCombate]= ("" + enemigoSeleccionado.getNombre() + " vs " + jugador.getNombre() + " | Derrota");
                fin = true;
            }else {
                System.out.println("");
                resultados[nCombate]= ("" + enemigoSeleccionado.getNombre() + " vs " + jugador.getNombre() + " | Victoria");
                if (enemigoSeleccionado.getNombre() == "Mister Random"){
                    partida.setPuntuacionTotal(partida.getPuntuacionTotal() + 100);
                }

                //Subir de nivel
                jugador.setNivel(jugador.getNivel() + 1);
                System.out.println("Nivel: " + jugador.getNivel() + "(+1)");

                if(jugador.getNivel()%5==0){

                    correcto = false;
                    while (!correcto){
                        System.out.println("");
                        System.out.println("Selecciona mejora:");
                        System.out.println("1-Mejora vida (+10) | actual: " + jugador.getVida());
                        System.out.println("2-Mejora ataque (+5) | actual: " + jugador.getAtaque());
                        opcion = br.readLine();

                        switch (opcion){
                            case ("1"):{
                                jugador.setVida(jugador.getVida() + 10);
                                System.out.println("Vida: " + jugador.getVida());
                                correcto = true;
                            }break;

                            case ("2"):{
                                jugador.setAtaque(jugador.getAtaque() + 5);
                                System.out.println("Ataque: " + jugador.getAtaque());
                                correcto = true;
                            }break;

                            default: {
                                System.out.println("no se entiende el input");
                                correcto = false;
                            }

                        }
                    }

                }


            }
            nCombate++;
        }

        //Guardar datos partida
        int nCombates = 0;
        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] != null){
                nCombates++;
            }
        }

        String[] combates = new String[nCombates];

        for (int i = 0; i < nCombates; i++) {
                combates[i] = resultados[i];
        }

        partida.setCombates(combates);

        //Mostrar resultados
        System.out.println("");
        System.out.println("Resultados finales:");
        System.out.println("Fecha: " + partida.getFecha());
        System.out.println("Jugador: " + partida.getNombreJugador());
        System.out.println("Puntuacion: " + partida.getPuntuacionTotal());
        System.out.println("Combates:");
        for (int i = 0; i < partida.getCombates().length; i++) {
            System.out.println(partida.getCombates()[i]);
        }

        //Opciones partida
        correcto = false;
        while (!correcto) {
            System.out.println("");
            System.out.println("Desea exportar la partida?");
            System.out.println("1-No");
            System.out.println("2-Si");
            opcion=br.readLine();

            switch (opcion) {
                case ("1"): {

                    correcto = true;
                }
                break;

                case ("2"): {
                    exportarPartidaXML(partida);
                    correcto = true;
                }
                break;

                default: {
                    System.out.println("no se entiende el input");
                    correcto = false;
                }
            }
        }

        //Exportar jugador
        correcto = false;
        while (!correcto) {
            System.out.println("");
            System.out.println("Jugador: " + jugador.getNombre() + " | Nivel: " + jugador.getNivel() + " | Vida: " + jugador.getVida() + " | Ataque: " + jugador.getAtaque());
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
    }

    //Funciones

    public static Enemigo seleccionarEnemigo(Enemigo[] enemigos) throws IOException {
        boolean correcto = false;
        String opcion;
        Enemigo resultado = null;
        int seleccion = -1;

        while (!correcto){
            //Mostrar enemigos
            System.out.println("");
            System.out.println("Enemigos:");

            for (int i = 0; i < enemigos.length; i++) {
                System.out.println(i + "-" + enemigos[i].getNombre() + " | Vida: " + enemigos[i].getVida() + " | Ataque: " + enemigos[i].getAtaque());
            }
            System.out.println(enemigos.length + "-Mister Random | Vida: ?? | Ataque: ??");

            //Recoger input
            opcion = br.readLine();

            //Comprobar que el input sea valido
            try {
                seleccion = Integer.parseInt(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Input no valido");
            }

            if (seleccion !=-1){

                if (seleccion > enemigos.length || seleccion < 0 ){
                    System.out.println("Input no valido");
                }else{
                    if (seleccion == enemigos.length){
                        //Generar enemigo procedural
                        resultado = new Enemigo("Mister Random", (int) (Math.random() * (500 - 10 + 1) + 10), (int) (Math.random() * (100 - 5 + 1) + 5));
                        correcto = true;

                    }else {
                        //Guardar seleccion
                        resultado = enemigos[seleccion];
                        correcto = true;
                    }


                }
            }
        }
        return resultado;
    }

    public static Boolean combate(Jugador jugador, Enemigo enemigo, Partida partida) throws IOException {
        boolean fin = false;
        boolean correcto = false;
        boolean resultado = false;
        int vidaJugador = jugador.getVida();
        int vidaEnemigo = enemigo.getVida();
        String opcion;

        while (!fin) {
            correcto=false;

            //Turno jugador
            while (!correcto) {
                System.out.println("");
                System.out.println("Turno jugador:");
                System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " | Ataque: " + jugador.getAtaque());

                //Opciones turno
                System.out.println("Accion:");
                System.out.println("1-Atacar ("+ jugador.getAtaque() +" daño)");
                System.out.println("2-Curar (+25 vida | " + jugador.getVida() + " max )");
                opcion = br.readLine();

                switch (opcion) {
                    case ("1"): {
                        vidaEnemigo = vidaEnemigo - jugador.getAtaque();
                        System.out.println("Atacas al enemigo:");
                        System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " (-" + jugador.getAtaque() + ") | Ataque: " + enemigo.getAtaque());
                        System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " | Ataque: " + jugador.getAtaque());

                        partida.setPuntuacionTotal(partida.getPuntuacionTotal() + 10 );

                        correcto = true;
                    }
                    break;

                    case ("2"): {
                        vidaJugador = vidaJugador + 25;
                        if (vidaJugador > jugador.getVida()){
                            vidaJugador = jugador.getVida();
                        }

                        System.out.println("Te curas:");
                        System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                        System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " (+25) | Ataque: " + jugador.getAtaque());

                        partida.setPuntuacionTotal(partida.getPuntuacionTotal() + 5 );

                        correcto = true;
                    }
                    break;

                    default: {
                        System.out.println("no se entiende el input");
                        correcto = false;
                    }
                }

            }

            //Comprobacion victoria
            if (vidaEnemigo <= 0){
                System.out.println("Victoria!");
                partida.setPuntuacionTotal(partida.getPuntuacionTotal() + 25 );
                fin = true;
                resultado = true;
            }else {

                //Turno enemigo
                vidaJugador = vidaJugador - enemigo.getAtaque();
                System.out.println("");
                System.out.println("Turno enmigo:");
                System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " (-" + enemigo.getAtaque() +") | Ataque: " + jugador.getAtaque());

                //Comprobacion derrota
                if (vidaJugador <= 0){
                    System.out.println("Derrota!");
                    fin = true;
                    resultado = false;
                }
            }
        }
        return resultado;
    }

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

    public static void exportarPartidaXML(Partida partida) throws FileNotFoundException {

        try {
            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("partida", Partida.class);
            //Insrtar los objetos en el XML
            xstream.toXML(partida, new FileOutputStream("./src/Partida_"+ LocalDateTime.now() +".xml"));
        }catch (Exception e)
        {e.printStackTrace();}
    }

}