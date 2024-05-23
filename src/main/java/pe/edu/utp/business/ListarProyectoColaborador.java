package pe.edu.utp.business;

import pe.edu.utp.model.Proyecto;
import pe.edu.utp.service.ProyectoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarProyectoColaborador {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ProyectoService busquedaServiceProyecto = null;

    public ListarProyectoColaborador() {

        try {
            busquedaServiceProyecto = new ProyectoService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    //Listar Proyectos
    public String getHtmlListarProyectosColaborador() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\proyectos_colaborador.html";
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
        String reporteHtml = html.replace("${itemsProyectoColaborador}", itemsHtml.toString());

        return reporteHtml;
    }
}
