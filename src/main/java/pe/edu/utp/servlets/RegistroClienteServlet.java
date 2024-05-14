package pe.edu.utp.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.utils.TextUTP;
import java.io.IOException;

@WebServlet("/register_cliente")

public class RegistroClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del Cliente
        String identificacion  = req.getParameter("txtidentificacion");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String correo = req.getParameter("txtcorreo");
        String celular = req.getParameter("txtcelular");
        String tipo_cliente = req.getParameter("txt_tipopersona");


        // Crear objeto usuario
        try {
            Cliente cliente = new Cliente(identificacion, tipo_cliente, nombre, apellidos, correo, celular);

            //Registro Cliente a la bd
            App.RegClients.registrarCliente(cliente);

            String filename = "src\\main\\resources\\web\\listar_clientes";
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
