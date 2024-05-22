package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.service.UsuariosService;
import pe.edu.utp.util.Errors;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/reestablecer")
public class ReestablecerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBusiness loginBusiness = new LoginBusiness();
        String token = req.getParameter("token") != null ? req.getParameter("token") : null ;
        PrintWriter out = resp.getWriter();

        Map<String, String> usuario = loginBusiness.findByToken(token);

        if( token == null || usuario == null ){
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }

        String filename = "src\\main\\resources\\web\\reestablecer.html";
        String html = TextUTP.read(filename);

        html = html.replace("${id}", usuario.get("id_usuario"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        LoginBusiness loginBusiness = new LoginBusiness();
        String token = req.getParameter("token") != null ? req.getParameter("token") : null ;

        String password = req.getParameter("password");
        String id = req.getParameter("id");

        if(password.isBlank() ){
            return;
        }

        String newToken = "";
        boolean update = loginBusiness.updateUsuarioTokenAndPassword(newToken, UsuariosService.md5(password), id);

        if(update){
            resp.sendRedirect("http://localhost:8085/login?success=UPDATED_PASSWORD");

        }else{
            resp.sendRedirect("http://localhost:8085/reestablecer?token="+token);
        }

    }

}
