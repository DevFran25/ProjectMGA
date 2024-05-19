package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Entregable;
import pe.edu.utp.service.EntregableService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroEntregable {

    // Conexión a la BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static EntregableService busquedaServiceEntregable = null;

    public RegistroEntregable(){

        try {
            busquedaServiceEntregable = new EntregableService(dao);
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registroEntregable(Entregable entregable) throws IOException{

        try {
            busquedaServiceEntregable.newEntregable(entregable);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e){
            System.out.println("AlreadyExistException: " + e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Error al crear: " + e.getMessage());
        } catch (IOException e){
            System.out.println("Error al crear: " + e.getMessage());
            String errorMsg = String.format("IOException al registrar entregable: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Listar Entregable
    public String getHtmlListarEntregable() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\entregable.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\entregable_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Entregables
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Entregable> listaEntregable = busquedaServiceEntregable.getAllEntregable();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Entregable entregable : listaEntregable) {

            //Tabla Entregable
            String item = htmlItem.replace("${id_entregable}", Integer.toString(entregable.getId_entregable()))
                    .replace("${id_proyecto}", entregable.getId_proyecto())
                    .replace("${nombre}", entregable.getNombre())
                    .replace("${fecha}", entregable.getFecha())
                    .replace("${archivo}", entregable.getArchivo());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsEntregable}", itemsHtml.toString());

        return reporteHtml;


    }
}
