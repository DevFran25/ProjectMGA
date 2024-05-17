package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.utils.Paginacion;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/proyectos")
public class ProyectoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Integer paginaActual = 1;

        String param = req.getParameter("page");
        if (param != null) {
            try {
                paginaActual = Integer.parseInt(req.getParameter("page"));
                if (paginaActual <= 0) {
                    resp.sendRedirect(req.getContextPath() + "/proyectos?page=1");
                }
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/proyectos?page=1");
            }
        }

        Paginacion paginacion = new Paginacion(paginaActual, 10);
        List<Proyecto> proyectos = paginacion.obtenerProyectos();

        if (paginaActual > paginacion.getTotalPaginas()) {
            resp.sendRedirect(req.getContextPath() + "/proyectos?page=1");
            return;
        }

        /*
        out.println(paginacion);

        for( Proyecto pr : proyectos ){
            out.println(pr.getNombre());
        }
         */

        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\projects.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\proyecto_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Cargar plantilla para los item
        String paginacionItems = "src\\main\\resources\\templates\\paginacion.html";
        String paginacionItem = TextUTP.read(paginacionItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();
        StringBuilder paging = new StringBuilder();

        // Listar

        for (Proyecto proyecto : proyectos) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_proyecto}", proyecto.getId_proyecto())
                    .replace("${dni_colaborador}", proyecto.getDni_colaborador())
                    .replace("${id_cliente}", Integer.toString(proyecto.getId_cliente()))
                    .replace("${nombre}", proyecto.getNombre())
                    .replace("${ubicacion}", proyecto.getUbicacion())
                    .replace("${costo}", Float.toString(proyecto.getCosto()))
                    .replace("${fecha_inicio}", proyecto.getFecha_inicio())
                    .replace("${fecha_fin}", proyecto.getFecha_fin())
                    .replace("${estado}", proyecto.getEstado())
                    .replace("${Foto}", proyecto.getFoto());
            itemsHtml.append(item);
        }

        for( int i = 0; i < paginacion.getTotalPaginas(); i++ ){
            String item = paginacionItem.replace("${paginaActual}", String.valueOf(i+1));
            paging.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${paginacion}", paging.toString());

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        out.println(reporteHtml);


    }

}
