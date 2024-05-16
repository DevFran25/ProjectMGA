package pe.edu.utp.business;

import pe.edu.utp.service.ClientesService;
import pe.edu.utp.service.LoginService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class LoginBusiness {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    private static LoginService loginService = null;

    public LoginBusiness(){
        try {
            loginService = new LoginService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public Map<String, String> existeUsuario(String dni) {
        Map<String, String> existe = null;
        try {
            existe = loginService.usuarioExiste(dni);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existe == null ? null : existe;
    }

}
