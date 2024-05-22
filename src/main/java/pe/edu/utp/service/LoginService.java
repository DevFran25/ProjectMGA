package pe.edu.utp.service;

import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private final Connection conn;

    public LoginService(DataAccess dao) throws SQLException, NamingException {
        this.conn = dao.getConnection();
    }

    public Map<String, String> usuarioExiste(String dni) throws SQLException {
        Map<String, String> result = null;
        String sql = "CALL ObtenerUsuarioPorDNI(?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            result = getData(rs);
        }
        return result;
    }

    public Map<String, String> findByEmail(String email) throws SQLException {
        Map<String, String> result = null;
        String sql = "CALL ObtenerUsuarioPorEmail(?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            result = getData(rs);
        }
        return result;
    }

    public Map<String, String> findByToken(String token) throws SQLException {
        Map<String, String> result = null;
        String sql = "CALL ObtenerUsuarioPorToken(?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            result = getData(rs);
        }
        return result;
    }

    public boolean updateUsuarioTokenByDni(String token, String dni) throws SQLException {
        String sql = "CALL UpdateUsuarioTokenByDNI(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, token);
        stmt.setString(2, dni);
        int rows = stmt.executeUpdate();
        return (rows > 0);
    }

    public boolean updateUsuarioTokenAndPassword(String token, String password, String id) throws SQLException {
        String sql = "CALL UpdateUsuarioTokenAndPassword(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, token);
        stmt.setString(2, password);
        stmt.setString(3, id);
        int rows = stmt.executeUpdate();
        return (rows > 0);
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
