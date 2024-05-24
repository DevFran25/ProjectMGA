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

    public void actualizarEstadoProyecto(String idProyecto) throws SQLException, IOException {
        try {
            busquedaServiceProyecto.actualizarEstadoProyecto(idProyecto);
            System.out.println("Estado del proyecto actualizado correctamente.");
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
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

    public String getHtmlDetalleProyecto(String idProyecto) throws IOException, SQLException {
        // Cargar la página de detalle del proyecto
        String filename = "src\\main\\resources\\web\\project.html";
        String html = TextUTP.read(filename);

        // Obtener el proyecto por su ID
        Proyecto proyecto = busquedaServiceProyecto.getProyectoById(idProyecto);
        if (proyecto == null) {
            throw new SQLException("Proyecto no encontrado con ID: " + idProyecto);
        }

        // Reemplazar los placeholders en la plantilla con los datos del proyecto
        String resultHtml = html.replace("${id_proyecto}", proyecto.getId_proyecto())
                .replace("${dni_colaborador}", proyecto.getDni_colaborador())
                .replace("${id_cliente}", Integer.toString(proyecto.getId_cliente()))
                .replace("${nombre}", proyecto.getNombre())
                .replace("${ubicacion}", proyecto.getUbicacion())
                .replace("${costo}", Float.toString(proyecto.getCosto()))
                .replace("${fecha_inicio}", proyecto.getFecha_inicio())
                .replace("${fecha_fin}", proyecto.getFecha_fin())
                .replace("${estado}", proyecto.getEstado())
                .replace("${foto}", proyecto.getFoto());

        return resultHtml;
    }

    //Combos para add_proyecto
    public String getHtmlAddProject() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        //AppConfig.getErrorTemplate();
        String filename = "src\\main\\resources\\web\\addproject.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de clientes
        String comboClientes = busquedaServiceProyecto.getComboClientes();

        //Obtener las opciones del combo de colaboradores
        String comboColaboradores = busquedaServiceProyecto.getComboColaboradores();

        // Reemplazar
        String resultHtml = html.replace("${comboClientes}",comboClientes)
                .replace("${comboColaboradores}",comboColaboradores);

        return resultHtml;
    }

    //Combos para add_avance
    public String getHtmlAvance() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        String filename = "src\\main\\resources\\web\\addavance.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de proyectos
        String comboProyectos = busquedaServiceProyecto.getComboProyectos();

        //Obtener las opciones del combo de colaboradores
        String comboColaboradores = busquedaServiceProyecto.getComboColaboradores();

        // Reemplazar
        String resultHtml = html.replace("${comboProyectos}",comboProyectos)
                .replace("${comboColaboradores}",comboColaboradores);

        return resultHtml;
    }

    public String getHmtlActividad() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        String filename = "src\\main\\resources\\web\\addactivite.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de proyectos
        String comboProyectos = busquedaServiceProyecto.getComboProyectos();

        // Reemplazar
        String resultHtml = html.replace("${comboProyectos}",comboProyectos);

        return resultHtml;
    }


    //Combo para add_entregable
    public String getHtmlEntregable() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar proyecto
        String filename = "src\\main\\resources\\web\\addentregable.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de proyectos
        String comboProyectos = busquedaServiceProyecto.getComboProyectos();

        // Reemplazar
        String resultHtml = html.replace("${comboProyectos}",comboProyectos);

        return resultHtml;
    }

    public String getHtmlUsuarios() throws IOException, SQLException {
        // Cargar la pagina del user
        String filename = "src\\main\\resources\\web\\register-nuevo.html";
        String html = TextUTP.read(filename);

        //Obtener las opciones del combo de colaboradores
        String comboColaboradores = busquedaServiceProyecto.getComboColaboradores();

        // Reemplazar
        String resultHtml = html.replace("${comboColaboradores}",comboColaboradores);

        return resultHtml;
    }

    //Reportes
    public String getTotal() throws SQLException, IOException {
        String filename = "src\\main\\resources\\web\\index.html";
        String html = TextUTP.read(filename);

        int totalClientes = busquedaServiceProyecto.getTotalClientes();
        int totalProyectos = busquedaServiceProyecto.getTotalProyectos();
        int totalEntregables = busquedaServiceProyecto.getTotalEntregables();
        double totalPresupuesto = busquedaServiceProyecto.getTotalCostoProyectos();
        List<String> proyectosConDiasRestantes = busquedaServiceProyecto.getProyectosConDiasRestantes();


        // Se convierten a cadenas
        String reportClientes = String.valueOf(totalClientes);
        String reportProyectos = String.valueOf(totalProyectos);
        String reportEntregables = String.valueOf(totalEntregables);
        String reportPresupuesto = String.valueOf(totalPresupuesto);

        // Reemplazar en el HTML
        String resultHtml = html.replace("${TotalClientes}", reportClientes);
        resultHtml = resultHtml.replace("${TotalProyectos}", reportProyectos);
        resultHtml = resultHtml.replace("${TotalEntregables}", reportEntregables);
        resultHtml = resultHtml.replace("${TotalPresupuesto}", reportPresupuesto);

        // Agregar información de proyectos con días restantes
        StringBuilder proyectosHtml = new StringBuilder();
        for (String proyecto : proyectosConDiasRestantes) {
            proyectosHtml.append("<li style=\"text-align:left\">").append(proyecto).append("</li>");
        }
        resultHtml = resultHtml.replace("${Alertas}", proyectosHtml.toString());

        return resultHtml;
    }


    // PARA COLABORADOR

    //Listar Proyectos de colaborador
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

    public String getHtmlDetalleProyectoColaborador(String idProyecto) throws IOException, SQLException {
        // Cargar la página de detalle del proyecto
        String filename = "src\\main\\resources\\web\\proyectos_colaborador_info.html";
        String html = TextUTP.read(filename);

        // Obtener el proyecto por su ID
        Proyecto proyecto = busquedaServiceProyecto.getProyectoById(idProyecto);
        if (proyecto == null) {
            throw new SQLException("Proyecto no encontrado con ID: " + idProyecto);
        }

        // Reemplazar los placeholders en la plantilla con los datos del proyecto
        String resultHtml = html.replace("${id_proyecto}", proyecto.getId_proyecto())
                .replace("${dni_colaborador}", proyecto.getDni_colaborador())
                .replace("${id_cliente}", Integer.toString(proyecto.getId_cliente()))
                .replace("${nombre}", proyecto.getNombre())
                .replace("${ubicacion}", proyecto.getUbicacion())
                .replace("${costo}", Float.toString(proyecto.getCosto()))
                .replace("${fecha_inicio}", proyecto.getFecha_inicio())
                .replace("${fecha_fin}", proyecto.getFecha_fin())
                .replace("${estado}", proyecto.getEstado())
                .replace("${foto}", proyecto.getFoto());

        return resultHtml;
    }

}
