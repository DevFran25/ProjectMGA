package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/listar_entregable")
public class ListarEntregableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println(App.RegEntregable.getHtmlListarEntregable());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
