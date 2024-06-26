package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Colaborador;
import pe.edu.utp.util.AppConfig;
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
            // Validaciones
            Validator.validateNotEmpty(dni_colaborador, "Dni Colaborador");
            Validator.validateNotEmpty(nombre, "Nombre");
            Validator.validateNotEmpty(apellidos, "Apellido");
            Validator.validateNotEmpty(telefono, "Telefono");
            Validator.validateNotEmpty(email, "Correo");
            Validator.validateNotEmpty(cargo, "Cargo");

            Colaborador colaborador = new Colaborador(dni_colaborador, nombre, apellidos, telefono, email, cargo);

            //Registro Cliente a la bd
            App.RegColabs.registrarColaborador(colaborador);

            resp.sendRedirect("/listar_colaborador");


        } catch (IllegalArgumentException e) {
            String filename_error = AppConfig.getErrorTemplate();
            String html_error = TextUTP.read(filename_error);
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }
    }
}
