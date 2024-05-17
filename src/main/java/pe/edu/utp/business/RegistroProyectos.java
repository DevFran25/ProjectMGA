package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.*;
import pe.edu.utp.service.ProyectoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroProyectos {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ProyectoService busquedaServiceProyecto = null;

    public RegistroProyectos() {

        try {
            busquedaServiceProyecto = new ProyectoService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarProyectos(Proyecto proyecto) throws IOException {

        try {
            busquedaServiceProyecto.newProyectos(proyecto);
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
            String errorMsg = String.format("IOException al registrar proyecto: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }

    //Listar Proyectos
    public String getHtmlListarProyectos() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\projects.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\proyecto_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Proyecto> listaProyectos = busquedaServiceProyecto.getAllProyectos();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();


        for (Proyecto proyecto : listaProyectos) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_proyecto}", proyecto.getId_proyecto())
                    .replace("${dni_colaborador}", proyecto.getDni_colaborador())
                    .replace("${id_cliente}", Integer.toString(proyecto.getId_cliente()))
                    .replace("${nombre}", proyecto.getNombre())
                    .replace("${ubicacion}", proyecto.getUbicacion())
                    .replace("${costo}", Float.toString(proyecto.getCosto()))
                    .replace("${fecha_inicio}", proyecto.getFecha_inicio())
                    .replace("${fecha_fin}", proyecto.getFecha_fin())
                    .replace("${estado}", proyecto.getEstado())
                    .replace("${Foto}", proyecto.getFoto());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString());

        return reporteHtml;


    }

    public String gitgetHtmlAddProject() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        String filename = "src\\main\\resources\\web\\addproject.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de clientes
        String comboClientes = busquedaServiceProyecto.getComboClientes();

        // Reemplazar el marcador de posición con las opciones del combo
        String resultHtml = html.replace("${comboClientes}", comboClientes);

        return resultHtml;
    }
}
