package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Colaborador;
import pe.edu.utp.service.ColaboradorService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroColaborador {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ColaboradorService busquedaServiceColaborador = null;

    public RegistroColaborador() {

        try {
            busquedaServiceColaborador = new ColaboradorService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarColaborador(Colaborador colaborador) throws IOException {

        try {
            busquedaServiceColaborador.newColaborador(colaborador);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e){
            System.out.println("AlreadyExistsException:" +e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("SQLException:" +e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Error al crear:" +e.getMessage());
        } catch (IOException e) {
            String errorMsg = String.format("IOException al registrar proyecto: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }
    //Listar Colaborador
    public String getHtmlListarColaborador() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\collaborators.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\colaboradores_listado.html";
        String htmlItem = TextUTP.read(filenameItems);


        // Recorrer la lista de Colaboradores
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Colaborador> listaColaborador = busquedaServiceColaborador.getAllColaboradores();

        for (Colaborador colaborador : listaColaborador) {

            //Tabla Colaboradores
            String item = htmlItem.replace("${dni_colaborador}", colaborador.getDni_colaborador())
                    .replace("${nombres}", colaborador.getNombre())
                    .replace("${apellidos}", colaborador.getApellidos())
                    .replace("${telefono}", colaborador.getTelefono())
                    .replace("${email}", colaborador.getEmail())
                    .replace("${cargo}", colaborador.getCargo());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsColaboradores}", itemsHtml.toString());

        return reporteHtml;
    }
}
