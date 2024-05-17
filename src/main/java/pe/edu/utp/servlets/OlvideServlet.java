package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.util.Errors;
import pe.edu.utp.utils.Email;
import pe.edu.utp.utils.TextUTP;
import pe.edu.utp.utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/olvide")
public class OlvideServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String filename = "src\\main\\resources\\web\\olvide.html";
        String html = TextUTP.read(filename);

        String error = req.getParameter("error") != null  ? req.getParameter("error") : null;
        String success = req.getParameter("success") != null  ? req.getParameter("success") : null;

        if( error != null){
            html = html.replace("${validate}", "<p class='p-2 px-4 bg-[#F8E9E8] text-[#B7342C] mb-4 rounded-md'>"+Errors.get(error)+"</p>");
        }else{
            html = html.replace("${validate}", "");
        }

        if( success != null){
            html = html.replace("${success}",
                    "<p class='p-2 px-4 bg-[#E3F6ED] text-[#346D53] mb-4 rounded-md'>Hemos enviado las instrucciones a tu email</p>");
        }else{
            html = html.replace("${success}", "");
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBusiness loginBusiness = new LoginBusiness();
        String email = req.getParameter("email");

        if( email.isBlank() ){
            String url = "http://localhost:8085/olvide?error=EMAIL_EMPTY";
            resp.sendRedirect(url);
            return;
        }

        Map<String, String> usuario = loginBusiness.findByEmail(email);
        if( usuario == null){
            String url = "http://localhost:8085/olvide?error=NOT_ACCOUNT_EXISTS";
            resp.sendRedirect(url);
            return;
        }

        String token = Utils.crearToken();
        Map<String, String> fields = new HashMap<>();
        fields.put("token", token);
        Map<String, String> where = new HashMap<>();
        where.put("dni_colaborador", usuario.get("dni_colaborador"));
        boolean update = loginBusiness.updateBy("usuario", fields, where);

        if(update){

            Email emailSender = new Email(usuario.get("email"), usuario.get("nombres"), token);
            emailSender.enviarInstrucciones();

            String url = "http://localhost:8085/olvide?success=exito";
            resp.sendRedirect(url);

        }else{
            String url = "http://localhost:8085/olvide?error=FAIL";
            resp.sendRedirect(url);
        }

    }

}
