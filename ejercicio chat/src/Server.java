import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(4999);
        Socket cliente;
        System.out.println("Servidor iniciado");
        while(true) {
            cliente = servidor.accept(); //esperando cliente
            ServerLogic hilo=new ServerLogic(cliente);
            hilo.run();

        }
    }
}

class ServerLogic extends Thread{
    ObjectOutputStream oos;
    ObjectInputStream ois;

    Socket cliente;

    public ServerLogic(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(cliente.getOutputStream());
            ois = new ObjectInputStream(cliente.getInputStream());

            while (true){
                Mensaje mensaje = (Mensaje) ois.readObject();
                if (mensaje.getType() == 1){
                    System.out.println(mensaje.getHora() + " | " + mensaje.getUser() + ": " + mensaje.getTexto());
                } else if (mensaje.getType() == 2) {
                    System.out.println(mensaje.getHora() + " | ( " + mensaje.getUser() + " se ha " + mensaje.getTexto() + " )");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
