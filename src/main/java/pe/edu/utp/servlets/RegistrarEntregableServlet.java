
package pe.edu.utp.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Entregable;
import pe.edu.utp.utils.TextUTP;
import java.io.IOException;

@WebServlet ("/registrar_entregable")
public class RegistrarEntregableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del entregable
        int id_entregable  = Integer.parseInt(req.getParameter("txtentregable"));
        String id_proyecto = req.getParameter("txtproyecto");
        String nombre = req.getParameter("txtnombre");
        String fecha = req.getParameter("txtfecha");
        String archivo = req.getParameter("txtarchivo");


        // Crear objeto entregable
        try {
            Entregable entregable = new Entregable(id_entregable, id_proyecto, nombre, fecha, archivo);

            //Registro Entregable a la bd
            App.RegEntregable.registroEntregable(entregable);

            String filename = "src\\main\\resources\\web\\entregable.html";
            String html = TextUTP.read(filename);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            resp.getWriter().println(html);


        } catch (IllegalArgumentException e) {
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }
    }
}
