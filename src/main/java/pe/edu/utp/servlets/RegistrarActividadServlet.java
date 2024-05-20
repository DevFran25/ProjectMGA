package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Actividades;
import pe.edu.utp.model.Avance;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;

@WebServlet("/registrar_actividad")
public class RegistrarActividadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del avance
        int id_actividad  = Integer.parseInt(req.getParameter("txtactividad"));
        String id_proyecto = req.getParameter("txtproyecto");
        String nombre = req.getParameter("txtnombre");
        String estado = req.getParameter("txtestado");

        // Crear objeto avance
        try {
            Actividades actividades = new Actividades(id_actividad, id_proyecto, nombre, estado);

            App.RegActividad.registrarActividad(actividades);

            resp.sendRedirect("/listar_actividad");


        } catch (IllegalArgumentException e) {
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }


    }
}
