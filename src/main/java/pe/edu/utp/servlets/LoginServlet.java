package pe.edu.utp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.service.LoginService;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = "src\\main\\resources\\web\\login.html";
        String html = TextUTP.read(filename);

        String error = req.getParameter("error") != null ? req.getParameter("error") : "";

        if(!error.equals("")){
            html.replace("${validate}", "El usuario no existe");
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);
        //req.getRequestDispatcher("/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBusiness loginBusiness = new LoginBusiness();
        String dni = req.getParameter("dni");
        String password = req.getParameter("password");

        Map<String, String> usuario = loginBusiness.existeUsuario(dni);
        if( usuario == null ){
            String url = "http://localhost:8085/login?error=USER_NOT_EXISTS";
            resp.sendRedirect(url);
            return;
        }

        boolean checkpassword = usuario.get("password").equals(password);
        if(!checkpassword){
            PrintWriter out = resp.getWriter();
            out.println("Contraseña incorrecta");
            return;
        }


        PrintWriter out = resp.getWriter();
        out.println(usuario);


    }
}
