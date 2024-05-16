
package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.model.Entregable;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.sql.Date;

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
        long fecha = req.getDateHeader("txtfecha");
        String file = req.getParameter("txtfile");

        // Crear objeto entregable
        try {
            Entregable entregable = new Entregable(id_entregable, id_proyecto, nombre, fecha, file);

            //App.RegEntregable.registrarAvance(Avance);

            String filename = "src\\main\\resources\\web\\listar_entregable";
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
