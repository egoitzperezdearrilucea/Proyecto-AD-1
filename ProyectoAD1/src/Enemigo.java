import java.io.*;

public class Enemigo implements Serializable {
    //Variables
    String nombre;
    int vida;
    int ataque;

    //Constructor
    public Enemigo(String nombre, int vida, int ataque) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
    }

    //Getter y Setter s
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    //Importar datos
    public static Enemigo[] importarEnemigosDAT() throws IOException, ClassNotFoundException {
        Enemigo[] enemigos = null;

        File fichero = new File("./src/Enemigos.dat");


        if (fichero.exists()) {
            FileInputStream filein = new FileInputStream(fichero);
            ObjectInputStream dataIS = new ObjectInputStream(filein);

            enemigos = new Enemigo[10];

            for (int i = 0; i < 10; i++) {

                enemigos[i] = (Enemigo) dataIS.readObject();

            }
        } else {
            System.out.println("Error el fichero de los enemigos no existe, el codigo para generar este fichero se ejecutara generando un fichero nuevo");
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


        System.out.println("Enemigos exportados correctamente a 'Enemigos.dat'");
    }


}
