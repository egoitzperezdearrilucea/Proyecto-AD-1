import java.io.*;
import java.time.LocalDateTime;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        //Variables
        Partida partida;
        Jugador jugador = null;
        Enemigo[] enemigos = null;
        Combate[] resultados = new Combate[500];
        String opcion;
        boolean correcto = false;
        boolean fin = false;

        //Conexion BBDD
        Consultas.conectar();

        //Importar enemigos

        enemigos = Enemigo.importarEnemigosDAT();
        if (enemigos == null){
                //Creacion de enemigos (Solo usar para crear .dat)

                System.out.println("Generando enemigos");
                enemigos = new Enemigo[10];
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
            Enemigo.exportarEnemigosDAT(enemigos);
        }



        //Opciones jugador

            correcto = false;
            while (!correcto){
                System.out.println("");
                System.out.println("Jugador:");
                System.out.println("1-Crear nuevo");
                System.out.println("2-Cargar .dat");
                try {
                    opcion = br.readLine();
                } catch (IOException e) {
                    System.out.println("Error: al introducir datos");
                    throw new RuntimeException(e);
                }

                switch (opcion){
                    case ("1"):{
                        jugador = crearJugador();
                        correcto = true;
                    }break;

                    case ("2"):{
                       // jugador = Jugador.importarJugadorDAT();
                        if (jugador != null){
                            correcto = true;
                        }else {
                            correcto = false;
                        }

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
                    resultados[nCombate]= new Combate(jugador.getNombre(), enemigoSeleccionado.getNombre(), "Derrota", nCombate);
                    fin = true;
                }else {
                    System.out.println("");
                    resultados[nCombate]= new Combate(jugador.getNombre(), enemigoSeleccionado.getNombre(), "Victoria", nCombate);
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
                            System.out.println("1-Mejora vida (+10) | actual: " + jugador.getVida().getPuntosVida());
                            System.out.println("2-Mejora ataque (+5) | actual: " + jugador.getAtaque().getPuntosAtaque());
                            try {
                                opcion = br.readLine();
                            } catch (IOException e) {
                                System.out.println("Error: al introducir datos");
                                throw new RuntimeException(e);
                            }

                            switch (opcion){
                                case ("1"):{
                                    jugador.getVida().setPuntosVida(jugador.getVida().getPuntosVida() + 10);
                                    jugador.getVida().setMejorasVida(jugador.getVida().getMejorasVida() + 1);
                                    System.out.println("Vida: " + jugador.getVida().getPuntosVida());
                                    correcto = true;
                                }break;

                                case ("2"):{
                                    jugador.getAtaque().setPuntosAtaque(jugador.getAtaque().getPuntosAtaque() + 5);
                                    jugador.getAtaque().setMejorasAtaque(jugador.getAtaque().getMejorasAtaque() + 1);
                                    System.out.println("Ataque: " + jugador.getAtaque().getPuntosAtaque());
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

        Combate[] combates = new Combate[nCombates];

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
            System.out.println(i + "-" + partida.getCombates()[i].getJugador() + " vs " + partida.getCombates()[i].getEnemigo() + " | " + partida.getCombates()[i].getResultado());
        }

        //Opciones partida

        try {
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
                        Partida.exportarPartidaXML(partida);
                        correcto = true;
                    }
                    break;

                    default: {
                        System.out.println("no se entiende el input");
                        correcto = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error general");
            throw new RuntimeException(e);
        }

        //Exportar jugador


            correcto = false;
            while (!correcto) {
                System.out.println("");
                System.out.println("Jugador: " + jugador.getNombre() + " | Nivel: " + jugador.getNivel() + " | Vida: " + jugador.getVida().getPuntosVida() + " | Ataque: " + jugador.getAtaque().getPuntosAtaque());
                System.out.println("Desea exportar el jugador?");
                System.out.println("1-No");
                System.out.println("2-Si");
                try {
                    opcion=br.readLine();
                } catch (IOException e) {
                    System.out.println("Error: al introducir datos");
                    throw new RuntimeException(e);
                }

                switch (opcion) {
                    case ("1"): {

                        correcto = true;
                    }
                    break;

                    case ("2"): {
                        /*
                        try{
                        Jugador.exportarJugadorDAT(jugador);
                        } catch (FileNotFoundException e) {
                            System.out.println("Error: no se encuentra el fichero Jugador.dat");
                            throw new RuntimeException(e);
                        }
                         */

                        Jugador.exportarJugadorXML(jugador);
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
    public static Enemigo seleccionarEnemigo(Enemigo[] enemigos) {
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
            try {
                opcion = br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al introducir datos");
                throw new RuntimeException(e);
            }

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

    public static Boolean combate(Jugador jugador, Enemigo enemigo, Partida partida) {
        boolean fin = false;
        boolean correcto = false;
        boolean resultado = false;
        int vidaJugador = jugador.getVida().getPuntosVida();
        int vidaEnemigo = enemigo.getVida();
        String opcion;

        while (!fin) {
            correcto=false;

            //Turno jugador
            while (!correcto) {
                System.out.println("");
                System.out.println("Turno jugador:");
                System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " | Ataque: " + jugador.getAtaque().getPuntosAtaque());

                //Opciones turno
                System.out.println("Accion:");
                System.out.println("1-Atacar ("+ jugador.getAtaque().getPuntosAtaque() +" daño)");
                System.out.println("2-Curar (+25 vida | " + jugador.getVida().getPuntosVida() + " max )");
                try {
                    opcion = br.readLine();
                } catch (IOException e) {
                    System.out.println("Error: al introducir datos");
                    throw new RuntimeException(e);
                }

                switch (opcion) {
                    case ("1"): {
                        vidaEnemigo = vidaEnemigo - jugador.getAtaque().getPuntosAtaque();
                        System.out.println("Atacas al enemigo:");
                        System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " (-" + jugador.getAtaque().getPuntosAtaque() + ") | Ataque: " + enemigo.getAtaque());
                        System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " | Ataque: " + jugador.getAtaque().getPuntosAtaque());

                        partida.setPuntuacionTotal(partida.getPuntuacionTotal() + 10 );

                        correcto = true;
                    }
                    break;

                    case ("2"): {
                        vidaJugador = vidaJugador + 25;
                        if (vidaJugador > jugador.getVida().getPuntosVida()){
                            vidaJugador = jugador.getVida().getPuntosVida();
                        }

                        System.out.println("Te curas:");
                        System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                        System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " (+25) | Ataque: " + jugador.getAtaque().getPuntosAtaque());

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
                System.out.println("El enemigo te ataca:");
                System.out.println("Enemigo: " + enemigo.getNombre() + " | Vida: " + vidaEnemigo + " | Ataque: " + enemigo.getAtaque());
                System.out.println("Jugador: " + jugador.getNombre() + " | Vida: " + vidaJugador + " (-" + enemigo.getAtaque() +") | Ataque: " + jugador.getAtaque().getPuntosAtaque());

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

    public static Jugador crearJugador() {
        String nombre;
        String opcion = null;
        Boolean correcto = false;

        System.out.println("Nombre jugador:");
        try {
            nombre = br.readLine();
        } catch (IOException e) {
            System.out.println("Error: al introducir datos");
            throw new RuntimeException(e);
        }

        Jugador jugador = new Jugador(nombre, new Vida(100,0), new Ataque(20,0), 0);

        System.out.println("Vida = " + jugador.getVida().getPuntosVida());
        System.out.println("Ataque = " + jugador.getAtaque().getPuntosAtaque());

        while (!correcto) {
            System.out.println("");
            System.out.println("Desea exportar el jugador?");
            System.out.println("1-No");
            System.out.println("2-Si");
            try {
                opcion=br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al introducir datos");
                throw new RuntimeException(e);
            }

            switch (opcion) {
                case ("1"): {

                    correcto = true;
                }
                break;

                case ("2"): {
                    //Jugador.exportarJugadorDAT(jugador);
                    Jugador.exportarJugadorXML(jugador);
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

}