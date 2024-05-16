package pe.edu.utp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.util.Errors;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String filename = "src\\main\\resources\\web\\login.html";
        String html = TextUTP.read(filename);

        String error = req.getParameter("error") != null  ? req.getParameter("error") : null;

        if( error != null){
            html = html.replace("${validate}", Errors.get(error));
        }else{
            html = html.replace("${validate}", "");
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);
        // req.getRequestDispatcher("/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBusiness loginBusiness = new LoginBusiness();
        String dni = req.getParameter("dni");
        String password = req.getParameter("password");

        if( dni.equals("") || password.equals("") ){
            String url = "http://localhost:8085/login?error=EMPTY_FIELDS";
            resp.sendRedirect(url);
            return;
        }

        Map<String, String> usuario = loginBusiness.existeUsuario(dni);
        if( usuario == null ){
            String url = "http://localhost:8085/login?error=NOT_USER_EXISTS";
            resp.sendRedirect(url);
            return;
        }

        boolean checkpassword = usuario.get("password").equals(password);
        if(!checkpassword){
            //PrintWriter out = resp.getWriter();
            //out.println("Contraseña incorrecta");
            String url = "http://localhost:8085/login?error=INCORRECT_PASS";
            resp.sendRedirect(url);
        }else{

            HttpSession session = req.getSession();
            session.setAttribute("id_usuario", usuario.get("id_usuario"));
            session.setAttribute("dni_colaborador", usuario.get("dni_colaborador"));
            session.setAttribute("nombres", usuario.get("nombres"));
            session.setAttribute("username", usuario.get("username"));
            session.setAttribute("apellidos", usuario.get("apellidos"));
            session.setAttribute("email", usuario.get("email"));
            session.setAttribute("cargo", usuario.get("cargo"));
            session.setAttribute("telefono", usuario.get("telefono"));

            String cargo = usuario.get("cargo");
            switch (cargo) {
                case "colaborador" -> resp.sendRedirect("/colaborador");
                case "jefe" -> resp.sendRedirect("/jefe");
                case "admin" -> resp.sendRedirect("/");
            }

        }

    }
}
