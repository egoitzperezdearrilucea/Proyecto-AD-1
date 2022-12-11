import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import java.lang.reflect.InvocationTargetException;


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
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean mostrarJugadores () {
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
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("");
                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: el documento no se pudo consultar");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
        }
        return true;
    }


    public static boolean mostrarPartidas () {
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("/partidas/partida/concat(\"Fecha: \",fecha, \"| Puntuacion: \", puntuacionTotal)");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" Error:la consulta no retorna valores");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("");
                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" Error: el documento no se pudo consultar");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: la conexion esta mal");
        }
        return true;
    }
}
