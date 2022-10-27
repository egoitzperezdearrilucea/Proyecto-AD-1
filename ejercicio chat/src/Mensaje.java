import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {
    int type;
    String user;
    String texto;
    Date hora;

    public Mensaje(int type, String user, String texto, Date hora) {
        this.type = type;
        this.user = user;
        this.texto = texto;
        this.hora = hora;
    }

    public int getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getTexto() {
        return texto;
    }

    public Date getHora() {
        return hora;
    }
}
