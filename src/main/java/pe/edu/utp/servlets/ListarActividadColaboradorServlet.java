package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/colaborador/listar_actividad_colaborador")
public class ListarActividadColaboradorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println(App.RegActividad.getHtmlListarActividadColaborador());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
