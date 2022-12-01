import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

import java.lang.reflect.InvocationTargetException;


public class Consultas {
    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionCross"; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = "admin"; //Clave
    static Collection col;


    public static void conectar() {

        try {
            Class<Database> cl = (Class<Database>) Class.forName(driver); //Cargar del driver
            Database database = cl.getDeclaredConstructor().newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
