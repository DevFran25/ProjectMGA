package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.TextUTP;
import pe.edu.utp.utils.UTPBinary;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/register_usuarios")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)

public class RegistroProyectoServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //Captura de datos
        String id_proyecto = req.getParameter("txtid_proyecto");
        String id_cliente = req.getParameter("txtid_cliente");
        String dni_colaborador = req.getParameter("txtdni_colaborador");
        String nombre = req.getParameter("txtnombre");
        String ubicacion = req.getParameter("txtubicacion");
        double costo = Double.parseDouble(req.getParameter("txtcosto"));
        String fecha_inicio = LocalDate.now().toString();
        String fecha_fin = req.getParameter("txtfecha_fin");
        String estado = req.getParameter("txtestado");
        //String foto = req.getParameter("txtfoto");


        // AppConfig + Metodo
        String destino = AppConfig.getImgDir();

        try {
            //Obtener la imagen y guardarla en la carpeta upload
            Part filePart = req.getPart("txtFoto");
            String foto = getFileName(filePart);
            String fileFoto = destino + foto;
            byte[] data = filePart.getInputStream().readAllBytes();
            UTPBinary.echobin(data, fileFoto);

            Proyecto proyecto = new Proyecto(id_proyecto, id_cliente, dni_colaborador, nombre, ubicacion, costo, fecha_inicio, fecha_fin,estado, foto);

            App.RegProyects.registrarProyectos(proyecto);

            String filename = "src\\main\\resources\\web\\proyecto.html";
            String html = TextUTP.read(filename);
            resp.getWriter().println(html);

        } catch(IllegalArgumentException e){
                String filename_error = AppConfig.getErrorTemplate();
                String html_error = TextUTP.read(filename_error);
                resp.getWriter().println(html_error.replace("${error}", e.getMessage()));

        }
    }

    // From: https://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
