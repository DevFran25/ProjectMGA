package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Colaborador;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;


@WebServlet("/register_colaborador")
public class RegistroColaboradorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del Colaborador
        String dni_colaborador = req.getParameter("txtdni");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String telefono = req.getParameter("txtcelular");
        String email = req.getParameter("txtcorreo");
        String cargo = req.getParameter("txt_cargo");

        // Crear objeto colaborador
        try {
            Colaborador colaborador = new Colaborador(dni_colaborador, nombre, apellidos, telefono, email, cargo);

            //Registro Cliente a la bd
            App.RegColabs.registrarColaborador(colaborador);

            String filename = "src\\main\\resources\\web\\listar_colaborador.html";
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
