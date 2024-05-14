package pe.edu.utp.service;

import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private final Connection conn;

    public LoginService(DataAccess dao) throws SQLException, NamingException {
        this.conn = dao.getConnection();
    }

    public Map<String, String> usuarioExiste(String dni) throws SQLException {
        Map<String, String> result = null;
        String sql = "select id_usuario, u.dni_colaborador, username, password, nombres, apellidos, telefono, email, " +
                "cargo from usuario u inner join colaborador c on u.dni_colaborador = c.dni_colaborador where u.dni_colaborador = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            result = getData(rs);
        }
        return result;
    }

    private Map<String, String> getData(ResultSet rs) throws SQLException {
        Map<String, String> values = new HashMap<>();
        values.put("id_usuario", rs.getString("id_usuario"));
        values.put("dni_colaborador", rs.getString("u.dni_colaborador"));
        values.put("username", rs.getString("username"));
        values.put("password", rs.getString("password"));
        values.put("nombres", rs.getString("nombres"));
        values.put("apellidos", rs.getString("apellidos"));
        values.put("telefono", rs.getString("telefono"));
        values.put("email", rs.getString("email"));
        values.put("cargo", rs.getString("cargo"));
        return values;
    }

}
