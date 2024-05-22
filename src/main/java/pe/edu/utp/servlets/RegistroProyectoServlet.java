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
import pe.edu.utp.utils.UTPBinary;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/register_proyectos")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)

public class RegistroProyectoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Captura de datos
        String id_proyecto = req.getParameter("txtid_proyecto");
        String dni_colaborador = req.getParameter("txtdni_colaborador");
        String descripcion = req.getParameter("txtnombre");
        String ubicacion = req.getParameter("txtubicacion");
        String txtCosto = req.getParameter("txtcosto");
        String txtIdCliente = req.getParameter("txtid_cliente");
        //String fecha_inicio = LocalDate.now().toString();
        String fecha_inicio = req.getParameter("txtfecha_inicio");
        String fecha_fin = req.getParameter("txtfecha_fin");
        String estado = req.getParameter("txtestado");
        String destino = AppConfig.getImgDir();

        try {
            // Validaciones
            Validator.validateNotEmpty(id_proyecto, "ID del proyecto");
            Validator.validateNotEmpty(dni_colaborador, "DNI del colaborador");
            Validator.validateNotEmpty(descripcion, "Nombre");
            Validator.validateNotEmpty(ubicacion, "Ubicación");
            Validator.validateNotEmpty(txtCosto, "Costo");
            Validator.validateNotEmpty(txtIdCliente, "ID del cliente");
            Validator.validateNotEmpty(fecha_inicio, "Fecha Inicio");
            Validator.validateNotEmpty(fecha_fin, "Fecha de fin");
            //Validator.validateNotEmpty(estado, "Estado");

            // Continuar con la lógica de negocio si todas las validaciones pasan
            float costo = Float.parseFloat(txtCosto);
            int id_cliente = Integer.parseInt(txtIdCliente);

            // Obtener la imagen y guardarla en la carpeta upload
            Part filePart = req.getPart("txtFoto");
            String foto = getFileName(filePart);
            if(foto.isEmpty()){
                throw new IllegalArgumentException("El campo foto no puede estar vacio");
            }
            String fileFoto = destino + foto;
            byte[] data = filePart.getInputStream().readAllBytes();
            UTPBinary.echobin(data, fileFoto);

            // Crear el objeto Proyecto y registrar el proyecto
            Proyecto proyecto = new Proyecto(id_proyecto, id_cliente, dni_colaborador, descripcion, ubicacion, costo, fecha_inicio, fecha_fin, estado, foto);
            App.RegProyects.registrarProyectos(proyecto);

            resp.sendRedirect("/proyectos");

        } catch (IllegalArgumentException e) {
            // Leer el HTML de error y reemplazar el marcador de posición con el mensaje de error
            String errorPagePath = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }

    // Método para obtener el nombre del archivo
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
