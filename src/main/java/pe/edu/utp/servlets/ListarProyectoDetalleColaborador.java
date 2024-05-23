package pe.edu.utp.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;

@WebServlet("/detalle_proyecto_colaborador")
public class ListarProyectoDetalleColaborador extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idProyecto = req.getParameter("idProyecto");
            if (idProyecto == null || idProyecto.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontró el proyecto");
                return;
            }
            String action = req.getParameter("action");
            if ("updateEstado".equals(action)) {
                actualizarEstadoProyecto(idProyecto);
                resp.sendRedirect(req.getContextPath() + "/detalle_proyecto_colaborador?idProyecto=" + idProyecto);
                return;
            }
            resp.getWriter().println(App.RegProyects.getHtmlDetalleProyectoColaborador(idProyecto));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void actualizarEstadoProyecto(String idProyecto) throws SQLException, IOException {
        try {
            App.RegProyects.actualizarEstadoProyecto(idProyecto);
            System.out.println("Estado del proyecto actualizado correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del proyecto.", e);
        }
    }
    
}
