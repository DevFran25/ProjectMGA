package pe.edu.utp.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.utils.TextUTP;
import pe.edu.utp.utils.UTPBinary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@WebServlet("/register_cliente")

public class RegistroClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Captura de datos
        String identificacion = req.getParameter("txtidentificacion");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String correo = req.getParameter("txtcorreo");
        String celular = req.getParameter("txtcelular");
        String tipo_cliente = req.getParameter("txt_tipopersona");


        try {
            // Validaciones
            Validator.validateNotEmpty(identificacion, "Identificación");
            Validator.validateNotEmpty(nombre, "Nombre");
            Validator.validateNotEmpty(apellidos, "Apellido");
            Validator.validateNotEmpty(correo, "Correo");
            Validator.validateNotEmpty(celular, "Celular");
            Validator.validateNotEmpty(tipo_cliente, "tipo Cliente");

            Cliente cliente = new Cliente(identificacion, nombre, apellidos, correo, celular, tipo_cliente);
            App.RegClients.registrarCliente(cliente);

            resp.sendRedirect("/cliente");

        } catch (IllegalArgumentException e) {
            // Leer el HTML de error y reemplazar el marcador de posición con el mensaje de error
            String errorPagePath = "src\\main\\resources\\templates\\error.html";
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }
}
