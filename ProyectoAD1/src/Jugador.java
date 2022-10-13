public class Jugador {
    //Variables
    String nombre;
    int vida;
    int ataque;
    int nivel;

    //Constructor
    public Jugador(String nombre, int vida, int ataque, int nivel) {
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    //Funciones


}
