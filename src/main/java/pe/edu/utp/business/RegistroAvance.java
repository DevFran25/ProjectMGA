package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Avance;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.service.AvanceService;
import pe.edu.utp.service.ProyectoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroAvance {
    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static AvanceService busquedaServiceAvance = null;

    public RegistroAvance() {

        try {
            busquedaServiceAvance = new AvanceService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarAvance(Avance avance) throws IOException {

        try {
            busquedaServiceAvance.newAvance(avance);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e){
            System.out.println("AlreadyExistsException:" +e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("SQLException:" +e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Error al crear:" +e.getMessage());
        } catch (IOException e) {
            String errorMsg = String.format("IOException al registrar avance: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }

    /*// Metodo para agregar proyecto
    public String getHtmlAddAvance() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        String filename = "src\\main\\resources\\web\\addavance.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de clientes
        String comboAvance = busquedaServiceAvance.getComboAvances();

        // Reemplazar el marcador de posición con las opciones del combo
        String resultHtml = html.replace("${comboAvance}", comboAvance);

        return resultHtml;
    }*/


    //Listar Avance
    public String getHtmlListarAvance() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\avance.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\avance_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Avance> listaAvance = busquedaServiceAvance.getAllAvance();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Avance avance : listaAvance) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_avance}", Integer.toString(avance.getId_avance()))
                    .replace("${id_proyecto}", avance.getId_proyecto())
                    .replace("${dni_colaborador}", avance.getDni_colaborador())
                    .replace("${progreso}", avance.getProgreso());
                    itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsAvance}", itemsHtml.toString());

        return reporteHtml;


    }

    public String getHtmlListarAvanceColaborador() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\avance_colaborador.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\avance_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Avance> listaAvance = busquedaServiceAvance.getAllAvance();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Avance avance : listaAvance) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_avance}", Integer.toString(avance.getId_avance()))
                    .replace("${id_proyecto}", avance.getId_proyecto())
                    .replace("${dni_colaborador}", avance.getDni_colaborador())
                    .replace("${progreso}", avance.getProgreso());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsAvanceColaborador}", itemsHtml.toString());

        return reporteHtml;


    }

}
