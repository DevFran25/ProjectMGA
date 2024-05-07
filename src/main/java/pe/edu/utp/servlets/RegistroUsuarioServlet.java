package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.*;
import pe.edu.utp.utils.TextUTP;
import java.io.IOException;



@WebServlet("/register_usuarios")

public class RegistroUsuarioServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del Usuario
        String dni_colaborador  = req.getParameter("comboDni");
        String username = req.getParameter("comboRol");
        String password = req.getParameter("txtpassword");


        // Crear objeto usuario
        try {
            Usuario usuario = new Usuario(dni_colaborador, username, password);

            //Registro Usuario a la bd
            App.RegUsers.registrarUsuarios(usuario);

            String filename = "src\\main\\resources\\web\\register-nuevo.html";
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
