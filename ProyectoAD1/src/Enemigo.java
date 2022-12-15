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
    public static Enemigo[] importarEnemigosDAT() {
        Enemigo[] enemigos = null;

        File fichero = new File("./src/Enemigos.dat");


        if (fichero.exists()) {
            ObjectInputStream dataIS = null;
            try {
                FileInputStream filein = new FileInputStream(fichero);
                dataIS = new ObjectInputStream(filein);
            } catch (IOException e) {
                System.out.println("Error: general");
                throw new RuntimeException(e);
            }

            enemigos = new Enemigo[10];

            for (int i = 0; i < 10; i++) {

                try {
                    enemigos[i] = (Enemigo) dataIS.readObject();
                } catch (IOException e) {
                    System.out.println("Error: general");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: no se encuentra la clase dataIS");
                    throw new RuntimeException(e);
                }

            }
        } else {
            System.out.println("Error: el fichero de los enemigos no existe, el codigo para generar este fichero se ejecutara generando un fichero nuevo");
        }

        return enemigos;
    }


    //Exportar datos

    public static void exportarEnemigosDAT(Enemigo[] enemigos) {

        File fichero = new File("./src/Enemigos.dat");
        ObjectOutputStream dataOS = null;
        try {
            FileOutputStream fileout = new FileOutputStream(fichero);
            dataOS = new ObjectOutputStream(fileout);
        } catch (IOException e) {
            System.out.println("Error: general");
            //throw new RuntimeException(e);
        }

        for (int i = 0; i < enemigos.length; i++) {
            try {
                dataOS.writeObject(enemigos[i]);
            } catch (IOException e) {
                System.out.println("Error: general");
                //throw new RuntimeException(e);
            }
        }


        System.out.println("Enemigos exportados correctamente a 'Enemigos.dat'");
    }


}
