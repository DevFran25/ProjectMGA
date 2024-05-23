package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Actividades;
import pe.edu.utp.model.Avance;
import pe.edu.utp.service.ActividadService;
import pe.edu.utp.service.AvanceService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroActividad {
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ActividadService busquedaServiceActividad = null;

    public RegistroActividad() {

        try {
            busquedaServiceActividad = new ActividadService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }
    public static void registrarActividad(Actividades actividades) throws IOException {

        try {
            busquedaServiceActividad.newActividad(actividades);
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
            String errorMsg = String.format("IOException al registrar actividad: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }
    //Listar Avance
    public String getHtmlListarActividad() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\actividad.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\actividad_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Actividades> listaActividad = busquedaServiceActividad.getAllActividad();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Actividades actividades : listaActividad) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_actividad}", Integer.toString(actividades.getId_actividad()))
                    .replace("${id_proyecto}", actividades.getId_proyecto())
                    .replace("${nombre}", actividades.getNombre())
                    .replace("${estado}", actividades.getEstado());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${intemsActividad}", itemsHtml.toString());

        return reporteHtml;


    }

    //Listar Avance en colaborador
    public String getHtmlListarActividadColaborador() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\actividad_colaborador.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\actividad_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Actividades> listaActividad = busquedaServiceActividad.getAllActividad();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Actividades actividades : listaActividad) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_actividad}", Integer.toString(actividades.getId_actividad()))
                    .replace("${id_proyecto}", actividades.getId_proyecto())
                    .replace("${nombre}", actividades.getNombre())
                    .replace("${estado}", actividades.getEstado());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsActividadColaborador}", itemsHtml.toString());

        return reporteHtml;


    }
}