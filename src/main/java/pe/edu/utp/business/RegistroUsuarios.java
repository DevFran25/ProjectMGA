package pe.edu.utp.business;
import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.UsuariosService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class RegistroUsuarios {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static UsuariosService busquedaServiceUsuario = null;

    public RegistroUsuarios() {

        try {
            busquedaServiceUsuario = new UsuariosService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarUsuarios(Usuario usuario) throws IOException {

        try {
            busquedaServiceUsuario.newUsuario(usuario);
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
            String errorMsg = String.format("IOException al registrar usuario: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }
}
