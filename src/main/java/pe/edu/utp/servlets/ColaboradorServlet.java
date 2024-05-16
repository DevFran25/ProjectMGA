package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.utp.utils.Log;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/colaborador")
public class ColaboradorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Log.print("Pagina Admin", resp);
        String filename = "src\\main\\resources\\web\\colaborador.html";
        String html = TextUTP.read(filename);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);
        */

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        out.println("ID usuario: " + session.getAttribute("id_usuario"));
        out.println("DNI colaborador: " + session.getAttribute("dni_colaborador"));
        out.println("Nombres: " + session.getAttribute("nombres"));
        out.println("Username: " + session.getAttribute("username"));
        out.println("Apellidos: " + session.getAttribute("apellidos"));
        out.println("Email: " + session.getAttribute("email"));
        out.println("Cargo: " + session.getAttribute("cargo"));
        out.println("Telefono: " + session.getAttribute("telefono"));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
