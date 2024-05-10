package pe.edu.utp;
import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.model.*;
import pe.edu.utp.service.*;
import pe.edu.utp.servlets.*;
import pe.edu.utp.util.*;
import pe.edu.utp.utils.*;
import pe.edu.utp.business.*;
import java.net.URL;


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




    public static void main( String[] args ) throws Exception {
        logger.info("Init app...");

        //String path = "src\\main\\resources\\web\\";
        String path = AppConfig.getWebDir();
        DataAccess dao = new DataAccessMariaDB(AppConfig.getConnectionStringCFN());

        busquedaUsuarioService = new UsuariosService(dao);

        JettyAdvUTP webserver = new JettyAdvUTP(8085,path);

        //Registrando Servlets

        //Proyecto
        webserver.addServlet(RegistroProyectoServlet.class, "/register_proyectos").getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        //Usuario
        webserver.addServlet(RegistroUsuarioServlet.class, "/register_usuarios");


        webserver.addServlet(ch.qos.logback.classic.ViewStatusMessagesServlet.class, "/status");


        URL myURL = new URL("http://localhost:8085/login.html");
        System.out.println("*********************************************************");
        System.out.println("CLICK AQUI PARA ABRIR LA APLICACION:" + myURL);
        System.out.println("*********************************************************");
        webserver.start();

    }

}
