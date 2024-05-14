package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.service.ClientesService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroClientes {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ClientesService busquedaServiceClientes = null;

    public RegistroClientes() {

        try {
            busquedaServiceClientes = new ClientesService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarCliente(Cliente cliente) throws IOException {

        try {
            busquedaServiceClientes.newClientes(cliente);
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

    //Listar Clientes
    public String getHtmlListarClientes() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\clients.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\cliente_listado.html";
        String htmlItem = TextUTP.read(filenameItems);


        // Recorrer la lista de Clientes
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Cliente> listaClientes = busquedaServiceClientes.getAllClientes();

        for (Cliente cliente : listaClientes) {

            //Tabla Clientes
            String item = htmlItem.replace("${identificacion}", cliente.getIdentificacion())
                    .replace("${tipo_cliente}", cliente.getTipo_cliente())
                    .replace("${nombre}",cliente.getNombre())
                    .replace("${apellidos}", cliente.getApellidos())
                    .replace("${email}", cliente.getEmail())
                    .replace("${celular}", cliente.getCelular());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsClientes}", itemsHtml.toString());

        return reporteHtml;
    }
}
