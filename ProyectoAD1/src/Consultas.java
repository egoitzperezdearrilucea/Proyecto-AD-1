import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;


public class Consultas {
    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/proyecto"; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = "admin"; //Clave
    static Collection col;


    public static Collection conectar() {

        try {
            Class<Database> cl = (Class<Database>) Class.forName(driver); //Cargar del driver
            Database database = cl.getDeclaredConstructor().newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error: al inicializar la BBDD eXist.");
            return null;
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: en el driver.");
            return null;
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error: al instanciar la BBDD.");
            return null;
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error: iniciar sesion en la BBDD");
            return null;
            //e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Error");
            //e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            System.out.println("Error: no se encontro el metodo");
            //e.printStackTrace();
            return null;
        }

    }

    public static void mostrarJugadores() {
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/jugadores/jugador/concat(nombre, \"| Nivel: \", nivel, \"| Vida: \", vida, \"(\", vida/@mejoras, \" mejoras ) | Ataque: \", ataque, \"(\", ataque/@mejoras, \" mejoras )\")");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                }
                int a = 1;
                System.out.println("Jugadores almacenados:");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    String resultadoConsulta = (String) r.getContent();
                    if (resultadoConsulta.equals("| Nivel: | Vida: ( mejoras ) | Ataque: ( mejoras )")) {
                        System.out.println("Error: no se han encontrado datos");
                    } else {
                        System.out.println(a + "-" + resultadoConsulta);
                        a++;
                    }
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: el documento no se pudo consultar");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
        }

    }

    public static boolean buscarJugador(String nombre) {
        boolean resultado = false;
        if (conectar() != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/jugadores/jugador[nombre = \"" + nombre + "\"]/concat(nombre, \"| Nivel: \", nivel, \"| Vida: \", vida, \"(\", vida/@mejoras, \" mejoras ) | Ataque: \", ataque, \"(\", ataque/@mejoras, \" mejoras )\")");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                    resultado = false;
                }


                int a = 1;
                System.out.println("Jugadores almacenados:");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    String resultadoConsulta = (String) r.getContent();
                    if (resultadoConsulta.equals("| Nivel: | Vida: ( mejoras ) | Ataque: ( mejoras )")) {
                        System.out.println("Error: no se han encontrado datos");
                        resultado = false;
                    } else {

                        System.out.println("");
                        System.out.print(a + "-");
                        System.out.print(resultadoConsulta);
                        resultado = true;
                    }
                    a++;
                }

                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: el documento no se pudo consultar");
                resultado = false;
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
            resultado = false;
        }
        return resultado;
    }

    public static Jugador cargarJugador() {
        Jugador jugador = null;
        if (conectar() != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


            mostrarJugadores();
            System.out.println("Nombre jugador a cargar:");
            String nombre = null;
            try {
                nombre = br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al leer input");
            }

            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/jugadores/jugador[nombre = \"" + nombre + "\"]/concat(nombre, \",\", nivel, \",\", vida, \",\", vida/@mejoras, \",\", ataque, \",\", ataque/@mejoras)");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                }
                int a = 1;
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    String resultadoConsulta = (String) r.getContent();
                    //System.out.println(resultadoConsulta);
                    String[] datos = resultadoConsulta.split(",");

                    if (resultadoConsulta.equals(",,,,,")) {
                        System.out.println("Error: no se han encontrado datos");
                    } else {
                        try {
                            jugador = new Jugador(datos[0], new Vida(Integer.parseInt(datos[2]), Integer.parseInt(datos[1])), new Ataque(Integer.parseInt(datos[4]), Integer.parseInt(datos[3])), Integer.parseInt(datos[5]));
                            System.out.println("Jugador cargado");
                        } catch (NumberFormatException e) {
                            System.out.println("Error: no se han encontrado datos");
                        }
                    }

                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: al consultar el documento");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
        }
        return jugador;
    }


    public static void mostrarPartidas() {
        if (conectar() != null) {
            try {

                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/partidas/partida/concat(\"Fecha: \",fecha, \" | Jugador: \", nombreJugador , \"| Puntuacion: \", puntuacionTotal)");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                }
                int a = 1;
                System.out.println("Partidas almacenadas:");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    String resultadoConsulta = (String) r.getContent();
                    if (resultadoConsulta.equals("Fecha: | Jugador: | Puntuacion:")) {
                        System.out.println("Error: no se han encontrado datos");
                    } else {
                        System.out.println(a + "-" + (String) r.getContent());
                        a++;
                    }
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: al consultar el documento");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
        }
    }

    public static void mostrarCombatePartidas() {
        if (conectar() != null) {


            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            mostrarPartidas();
            System.out.println("Fecha partida a leer:");
            String fecha = null;
            try {
                fecha = br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al leer input");
                //throw new RuntimeException(e);
            }

            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/partidas/partida[fecha = \"" + fecha + "\"]/combates/combate/concat(@numero, \"- jugador: \", /jugador, \"(nivel: \", /jugador/@nivel,\") VS enemigo:\", /enemigo, \"| Resultado: \", @resultado)");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                }

                //- jugador: (nivel: ) VS enemigo:| Resultado:
                System.out.println("Combates almacenados:");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    String resultadoConsulta = (String) r.getContent();
                    if (resultadoConsulta.equals("- jugador: (nivel: ) VS enemigo:| Resultado: ")) {
                        System.out.println("Error: no se han encontrado datos");
                    } else {
                        System.out.println(resultadoConsulta);
                    }

                }

                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: al consultar el documento");
                //e.printStackTrace();
            }

        } else {
            System.out.println("Error: la conexion esta mal");
        }
    }

    public static void guardarJugador(Jugador jugador) {

        String nuevojugador = "<jugador><nombre>" + jugador.getNombre() + "</nombre><vida mejoras= \"" + jugador.getVida().getMejorasVida() + "\"><puntosVida>" + jugador.getVida().getPuntosVida() + "</puntosVida></vida><ataque mejoras=\"" + jugador.getAtaque().getMejorasAtaque() + "\"><puntosAtaque>" + jugador.getAtaque().getPuntosAtaque() + "</puntosAtaque></ataque><nivel>" + jugador.getNivel() + "</nivel></jugador>";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //System.out.printf("Inserto: %s \n", nuevojugador);
                //Consulta para insertar --> update insert ... into
                ResourceSet result = servicio.query("update insert " + nuevojugador + " into /jugadores");
                col.close(); //borramos
                System.out.println("Jugador insertado.");
            } catch (Exception e) {
                System.out.println("Error: al insertar al jugador.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: en la conexión. Comprueba datos.");
        }
    }


    public static void guardarPartida(Partida partida) {

        String combates = "<combates>";
        if (partida.getCombates() != null) {
            for (int i = 0; i < partida.getCombates().length; i++) {
                combates = combates + "<combate numero=\"" + partida.getCombates()[i].getNumero() + "\" resultado=\"" + partida.getCombates()[i].getResultado() + "\">";
                combates = combates + "<jugador nivel=\"" + partida.getCombates()[i].getJugador().getNivel() + "\">" + partida.getCombates()[i].getJugador().getNombre() + "</jugador>";
                combates = combates + "<enemigo>" + partida.getCombates()[i].getEnemigo() + "</enemigo>";
                combates = combates + "</combate>";
            }
        }
        combates = combates + "</combates>";

        String nuevaPartida = "<partida><fecha>" + partida.getFecha() + "</fecha><puntuacionTotal>" + partida.getPuntuacionTotal() + "</puntuacionTotal>" + combates + "<nombreJugador>" + partida.getNombreJugador() + "</nombreJugador></partida>";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para insertar --> update insert ... into
                ResourceSet result = servicio.query("update insert " + nuevaPartida + " into /partidas");
                col.close(); //borramos
                System.out.println("Partida insertada.");
            } catch (Exception e) {
                System.out.println("Error: al insertar la partida.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error: en la conexión. Comprueba datos.");
        }


    }

    public static void modificarJugador() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if (conectar() != null) {
            mostrarJugadores();
            System.out.println("Nombre jugador a editar:");
            String nombre = null;
            try {
                nombre = br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al leer input");
            }

            if (buscarJugador(nombre)) {

                String columna = null;
                boolean fin = false;
                String opcion = null;

                while (!fin) {
                    System.out.println("Columna a editar:");
                    System.out.println("1-puntosVida");
                    System.out.println("2-puntosAtaque");
                    System.out.println("3-nivel");

                    try {
                        opcion = br.readLine();
                    } catch (IOException e) {
                        System.out.println("Error: al introducir datos");
                        //throw new RuntimeException(e);
                    }

                    switch (opcion) {
                        case ("1"): {
                            columna = "/vida/puntosVida";
                            fin = true;
                        }
                        break;

                        case ("2"): {
                            columna = "/ataque/puntosAtaque";
                            fin = true;
                        }
                        break;

                        case ("3"): {
                            columna = "/nivel";
                            fin = true;
                        }
                        break;

                        default: {
                            System.out.println("no se entiende el input");
                        }

                    }
                }


                System.out.println("valor nuevo:");
                String valor = null;
                try {
                    valor = br.readLine();
                } catch (IOException e) {
                    System.out.println("Error: al leer input");
                }
                try {
                    System.out.printf("Actualizo el jugador: %s\n", nombre);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para modificar/actualizar un valor --> update value
                    ResourceSet result = servicio.query(
                            //"update value /jugadores/jugador[nombre=" + nombre + "]/" + columna + "with data(" + valor + ") ");
                            "update value /jugadores/jugador[nombre = \"" + nombre + "\"]" + columna + " with " + valor);

                    col.close();
                    System.out.println("Jugador actualizado.");
                } catch (Exception e) {
                    System.out.println("Error al actualizar.");
                    //e.printStackTrace();
                }
            } else {
                System.out.println("Error: no se ha encontrado ese jugador");
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }
    }

    public static void borrarJugador() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if (conectar() != null) {
            mostrarJugadores();
            System.out.println("Nombre jugador a borrar:");
            String nombre = null;
            try {
                nombre = br.readLine();
            } catch (IOException e) {
                System.out.println("Error: al leer input");
            }

            if (buscarJugador(nombre)) {

                try {

                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query("update delete /jugadores/jugador[nombre = \"" + nombre + "\"]");
                    col.close();
                    System.out.println("jugador borrado.");

                } catch (Exception e) {
                    System.out.println("Error: al borrar.");
                    //e.printStackTrace();
                }

            } else {
                System.out.println("Error: no se ha encontrado ese jugador");
            }

        } else {
            System.out.println("Error: en la conexión. Comprueba datos.");
        }
    }
}
