package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Avance;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;

@WebServlet("/registrar_avance")
public class RegistrarAvanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del avance
        int id_avance  = Integer.parseInt(req.getParameter("txtavance"));
        String id_proyecto = req.getParameter("txtproyecto");
        String dni_colaborador = req.getParameter("txtcolaborador");
        String progreso = req.getParameter("txtprogreso");

        // Crear objeto avance
        try {
            // Validaciones
            Validator.validateNotEmpty(dni_colaborador, "Dni Colaborador");
            Validator.validateNotEmpty(String.valueOf(id_avance), "Id Avance");
            Validator.validateNotEmpty(dni_colaborador, "Dni Colaborador");
            Validator.validateNotEmpty(progreso, "Progreso");

            Avance avance = new Avance(id_avance, id_proyecto, dni_colaborador, progreso);

            App.RegAvance.registrarAvance(avance);

            resp.sendRedirect("/listar_avance");


        } catch (IllegalArgumentException e) {
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }


    }
}