package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.Entregable;
import pe.edu.utp.util.AppConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@WebServlet("/registrar_entregable")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,  // 5 MB
        maxRequestSize = 1024 * 1024 * 5 * 5  // 25 MB
)
public class RegistrarEntregableServlet extends HttpServlet {

    //Función para especificar los tipos de archivos que se permiten subir
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "application/pdf", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Capturar los datos del entregable
        int id_entregable = Integer.parseInt(req.getParameter("txtentregable"));
        String id_proyecto = req.getParameter("txtproyecto");
        String nombre = req.getParameter("txtnombre");
        String fecha = req.getParameter("txtfecha");

        try {
            // Validaciones
            Validator.validateNotEmpty(String.valueOf(id_entregable), "ID del entregable");
            Validator.validateNotEmpty(nombre, "Nombre");
            Validator.validateNotEmpty(id_proyecto, "ID Proyecto");
            Validator.validateNotEmpty(fecha, "Fecha");

            Part filePart = req.getPart("txtarchivo");
            String fileName = getFileName(filePart);
            if (fileName.isEmpty()) {
                throw new IllegalArgumentException("El campo archivo no puede estar vacío");
            }
            // Guardar el archivo
            String destino = AppConfig.getFileDir();
            String filePath = destino + fileName;
            byte[] data = filePart.getInputStream().readAllBytes();
            Files.write(Paths.get(filePath), data);

            // Validar el tipo de archivo
            String fileType = filePart.getContentType();
            if (!ALLOWED_FILE_TYPES.contains(fileType)) {
                throw new IllegalArgumentException("Tipo de archivo no permitido");
            }


            // Crear el objeto Entregable y registrar el entregable
            Entregable entregable = new Entregable(id_entregable, id_proyecto, nombre, fecha, fileName);
            App.RegEntregable.registroEntregable(entregable);

            resp.sendRedirect("/listar_entregable");

        } catch (IllegalArgumentException e) {
            String filename_error = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(filename_error)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
