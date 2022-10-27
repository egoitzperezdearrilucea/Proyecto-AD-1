import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;

public class Client {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        ObjectOutputStream oos;
        ObjectInputStream ois;
        String nombre;
        try {



            System.out.println("Nombre usuario:");
            nombre = br.readLine();


            String input = null;
            boolean running = true;

            Socket peticion = new Socket("localhost", 4999);
            oos = new ObjectOutputStream(peticion.getOutputStream());
            ois = new ObjectInputStream(peticion.getInputStream());

            oos.writeObject(new Mensaje(2, nombre, "conectado", new Date()));


            System.out.println("Mensajes: (salir para acabar)");
            while (running){

                input = br.readLine();
                if (input.equals("salir")){
                    System.out.println("adios");

                    running = false;
                }else {

                    oos.writeObject(new Mensaje(1, nombre, input, new Date()));
                }


            }
            oos.writeObject(new Mensaje(2, nombre, "desconectado", new Date()));

            oos.close();
            ois.close();
            peticion.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
