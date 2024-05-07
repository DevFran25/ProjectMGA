package pe.edu.utp.utils;

import jakarta.servlet.Servlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;

import java.nio.file.Paths;

public class JettyAdvUTP {

    private Server server;
    private int port;
    private String static_path;
    private ServletContextHandler context;

    public JettyAdvUTP(int port, String static_path) throws Exception {
        this.port = port;
        this.static_path = static_path;
        this.server = new Server(port);
        Connector connector = new ServerConnector(this.server);
        this.server.addConnector(connector);
        this.context = getContextWebApp(this.port, this.static_path);
        server.setHandler(context);

    }

    private static ServletContextHandler getContextWebApp(int port, String path){

        // URL: https://xy2401.com/local-docs/java/jetty.9.4.24.v20191120/embedding-jetty.html
        // Crear ServletContextHandler con contextPath.
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Resource baseResource = new PathResource(Paths.get(System.getProperty("java.io.tmpdir")));
        context.setBaseResource(baseResource);

        // Agregar DefaultServlet para atender contenido estático
        ServletHolder servletHolderStatic = context.addServlet(DefaultServlet.class, "/");
        // Configurar DefaultServlet para indicarle la ruta de los archivos estáticos
        servletHolderStatic.setInitParameter("resourceBase", path);
        servletHolderStatic.setAsyncSupported(true);

        return context;
    }

    public ServletHolder addServlet(Class<? extends Servlet> servlet, String path){
        return context.addServlet(servlet, path);
    }

    public void start() throws Exception {
        // Link the context to the server.
        this.server.setHandler(this.context);
        this.server.start();
    }

}
