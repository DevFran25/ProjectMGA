package pe.edu.utp;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.filters.AdminFilter;
import pe.edu.utp.filters.ColaboradorFilter;
import pe.edu.utp.filters.LoginFilter;
import pe.edu.utp.service.*;
import pe.edu.utp.servlets.*;
import pe.edu.utp.util.*;
import pe.edu.utp.utils.*;
import pe.edu.utp.business.*;
import java.net.URL;
import java.util.EnumSet;


/**
 * Hello UTP!
 *
 */
public class App
{

    public static final Logger logger = LoggerFactory.getLogger(App.class);
    public static UsuariosService busquedaUsuarioService = null;
    public static RegistroUsuarios RegUsers = new RegistroUsuarios();
    public static RegistroProyectos RegProyects = new RegistroProyectos();
    public static RegistroClientes RegClients = new RegistroClientes();
    public static RegistroColaborador RegColabs = new RegistroColaborador();
    public static RegistroAvance RegAvance = new RegistroAvance();
    public static RegistroEntregable RegEntregable = new RegistroEntregable();
    public static RegistroActividad RegActividad = new RegistroActividad();


    public static void main( String[] args ) throws Exception {
        logger.info("Init app...");

        //String path = "src\\main\\resources\\web\\";
        String path = AppConfig.getWebDir();
        DataAccess dao = new DataAccessMariaDB(AppConfig.getConnectionStringCFN());

        busquedaUsuarioService = new UsuariosService(dao);

        JettyAdvUTP webserver = new JettyAdvUTP(8085,path);

        //Registrando Servlets

        //Index
        webserver.addServlet(ReportesServlet.class, "/inicio");

        //Proyecto
        webserver.addServlet(RegistroProyectoServlet.class, "/register_proyectos").getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));
        webserver.addServlet(ListarProyectosServlet.class, "/listar_proyecto");
        webserver.addServlet(DetalleProyectoServlet.class, "/detalle_proyecto");

        // COLABORADOR
        webserver.addServlet(ListarProyectosColaboradorServlet.class, "/colaborador/listar_proyecto_colaborador");
        webserver.addServlet(ListarProyectoDetalleColaborador.class, "/detalle_proyecto_colaborador");


        //Usuario
        webserver.addServlet(RegistroUsuarioServlet.class, "/register_usuarios");
        webserver.addServlet(ComboFor_Usuarios.class, "/add_usuarios");
        
        //Cliente
        webserver.addServlet(ListarClienteServlet.class,"/listar_clientes");
        webserver.addServlet(RegistroClienteServlet.class, "/register_cliente");
        webserver.addServlet(CombosFor_AddProject.class,"/add_project");

        //Colaborador
        webserver.addServlet(ListarColaboradorServlet.class,"/listar_colaborador");
        webserver.addServlet(RegistroColaboradorServlet.class, "/register_colaborador");

        // Avance
        webserver.addServlet(ListarAvanceServlet.class, "/listar_avance");
        webserver.addServlet(RegistrarAvanceServlet.class, "/registrar_avance");
        webserver.addServlet(ComboFor_AddAvance.class, "/add_avance");

        // COLABORADOR
        webserver.addServlet(ListarColaboradorAvanceServlet.class, "/colaborador/listar_avance_colaborador");


        // Entregable
        webserver.addServlet(ListarEntregableServlet.class, "/listar_entregable");
        webserver.addServlet(RegistrarEntregableServlet.class, "/registrar_entregable").getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\documentos"));;
        webserver.addServlet(ComboFor_AddEntregable.class, "/add_entregable");

        //Actividad
        webserver.addServlet(ListarActividadServlet.class, "/listar_actividad");
        webserver.addServlet(RegistrarActividadServlet.class, "/registrar_actividad");
        webserver.addServlet(ComboFor_AddActividad.class, "/add_actividad");

        // COLABORADOR
        webserver.addServlet(ListarActividadColaboradorServlet.class, "/colaborador/listar_actividad_colaborador");



        webserver.addServlet(LoginServlet.class, "/login");
        webserver.addServlet(LogoutServlet.class, "/logout");
        webserver.addServlet(ColaboradorServlet.class, "/colaborador");
        webserver.addServlet(AdminServlet.class, "");

        webserver.addServlet(ProyectoServlet.class, "/proyectos");
        webserver.addServlet(OlvideServlet.class, "/olvide");
        webserver.addServlet(ReestablecerServlet.class, "/reestablecer");

        webserver.addServlet(ch.qos.logback.classic.ViewStatusMessagesServlet.class, "/status");

        webserver.addFilter(AdminFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        webserver.addFilter(LoginFilter.class, "/login/*", EnumSet.of(DispatcherType.REQUEST));
        webserver.addFilter(ColaboradorFilter.class, "/colaborador/*", EnumSet.of(DispatcherType.REQUEST));


        URL myURL = new URL("http://localhost:8085");
        System.out.println("*********************************************************");
        System.out.println("CLICK AQUI PARA ABRIR LA APLICACION:" + myURL);
        System.out.println("*********************************************************");
        webserver.start();
    }

}
