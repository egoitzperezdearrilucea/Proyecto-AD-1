import com.thoughtworks.xstream.XStream;

import java.io.*;

public class Jugador implements Serializable {
    //Variables
    String nombre;
    Vida vida;
    Ataque ataque;
    int nivel;

    //Constructor
    public Jugador(String nombre, Vida vida, Ataque ataque, int nivel) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.nivel = nivel;
    }

    //Getter y Setter s

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vida getVida() {
        return vida;
    }

    public Ataque getAtaque() {
        return ataque;
    }


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    //Importar datos (Descomentar para usar sin BBDD)

    /*
    public static Jugador importarJugadorDAT() throws IOException, ClassNotFoundException {
        File fichero = new File ("./src/Jugador.dat");
        Jugador  jugador = null;
        if (fichero.exists()){
            FileInputStream filein = new FileInputStream(fichero);
            ObjectInputStream dataIS = new ObjectInputStream(filein);
            jugador = (Jugador) dataIS.readObject();
            System.out.println("");
            System.out.println("Datos cargados de el jugador:");
            System.out.println("Nombre:" + jugador.getNombre());
            System.out.println("Nivel:" + jugador.getNivel());
            System.out.println("Vida:" + jugador.getVida());
            System.out.println("Ataque:" + jugador.getAtaque());
        }else {
            System.out.println("Error el fichero de el jugador no existe o no se encuentra en la ubicacion correcta, usa la funcion de generar un jugador nuevo o si ya tienes un 'Jugador.dat' asegurate de que este en la carpeta '/src' de el proyecto");
        }
        return jugador;
    }
     */

    //Exportar datos (Descomentar para usar sin BBDD)

    /*
    public static void exportarJugadorDAT(Jugador jugador) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File fichero = new File ("./src/Jugador.dat");
        System.out.println("");
        if (fichero.exists()){
            Jugador jugadorExistente = Jugador.importarJugadorDAT();
            System.out.println("Ya existe un fichero 'Jugador.dat' con el jugador: " + jugadorExistente.getNombre() + " guardado");
            String opcion;
            Boolean correcto = false;
            while (!correcto) {
                System.out.println("Desea sobreescribir el fichero?");
                System.out.println("1-No");
                System.out.println("2-Si");
                opcion=br.readLine();
                switch (opcion) {
                    case ("1"): {
                        correcto = true;
                    }
                    break;
                    case ("2"): {
                        FileOutputStream fileout = new FileOutputStream(fichero);
                        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
                        dataOS.writeObject(jugador);
                        System.out.println("Jugador exportado correctamente a 'Jugador.dat'");
                        correcto = true;
                    }
                    break;
                    default: {
                        System.out.println("no se entiende el input");
                        correcto = false;
                    }
                }
            }
        }else {
            FileOutputStream fileout = new FileOutputStream(fichero);
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
            dataOS.writeObject(jugador);
            System.out.println("Jugador exportado correctamente a 'Jugador.dat'");
        }
    }
     */
    /*
    public static void exportarJugadorXML(Jugador jugador) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {

            File fichero = new File ("./src/Jugador.xml");
            System.out.println("");
            if (fichero.exists()){
                System.out.println("Ya existe un fichero 'Jugador.xml");

                String opcion;
                Boolean correcto = false;
                while (!correcto) {
                    System.out.println("Desea sobreescribir el fichero?");
                    System.out.println("1-No");
                    System.out.println("2-Si");
                    opcion=br.readLine();

                    switch (opcion) {
                        case ("1"): {

                            correcto = true;
                        }
                        break;

                        case ("2"): {
                            XStream xstream = new XStream();
                            //cambiar de nombre a las etiquetas XML
                            xstream.alias("jugador", Jugador.class);


                            xstream.useAttributeFor(Vida.class, "mejorasVida");
                            xstream.aliasField("mejoras", Vida.class, "mejorasVida");

                            xstream.useAttributeFor(Ataque.class, "mejorasAtaque");
                            xstream.aliasField("mejoras", Ataque.class, "mejorasAtaque");

                            //Insertar los objetos en el XML
                            xstream.toXML(jugador, new FileOutputStream("./src/Jugador.xml"));

                            System.out.println("Jugador exportado correctamente a 'Jugador.xml'");
                            correcto = true;
                        }
                        break;

                        default: {
                            System.out.println("no se entiende el input");
                            correcto = false;
                        }
                    }
                }

            }else {
                XStream xstream = new XStream();
                //cambiar de nombre a las etiquetas XML
                xstream.alias("jugador", Jugador.class);
                //Insrtar los objetos en el XML
                xstream.toXML(jugador, new FileOutputStream("./src/Jugador.xml"));

                System.out.println("Jugador exportado correctamente a 'Jugador.xml'");
            }

        }catch (Exception e)
        {e.printStackTrace();}
    }
    */
}

class Vida {
    int puntosVida;
    int mejorasVida;

    public Vida(int puntosVida, int mejorasVida) {
        this.puntosVida = puntosVida;
        this.mejorasVida = mejorasVida;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    public int getMejorasVida() {
        return mejorasVida;
    }

    public void setMejorasVida(int mejorasVida) {
        this.mejorasVida = mejorasVida;
    }
}

class Ataque {
    int puntosAtaque;
    int mejorasAtaque;

    public Ataque(int puntosAtaque, int mejorasAtaque) {
        this.puntosAtaque = puntosAtaque;
        this.mejorasAtaque = mejorasAtaque;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public void setPuntosAtaque(int puntosAtaque) {
        this.puntosAtaque = puntosAtaque;
    }

    public int getMejorasAtaque() {
        return mejorasAtaque;
    }

    public void setMejorasAtaque(int mejorasAtaque) {
        this.mejorasAtaque = mejorasAtaque;
    }
}