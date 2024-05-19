package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/detalle_proyecto")
public class DetalleProyectoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idProyecto = req.getParameter("idProyecto");
            if (idProyecto == null || idProyecto.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontro el proyecto");
                return;
            }
            resp.getWriter().println(App.RegProyects.getHtmlDetalleProyecto(idProyecto));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}