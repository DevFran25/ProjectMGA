package pe.edu.utp.util;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataAccess {
    public Connection getConnection() throws SQLException, NamingException;
    public ResultSet querySQL(String sql) throws SQLException;
    public void closeConnection() throws SQLException;
}
