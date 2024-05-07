package pe.edu.utp.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessMariaDB implements DataAccess {

    public enum TipoDA {DATASOURCE, CLASS_FORNAME};
    private final String connectionString;
    private final TipoDA tipoDA;
    private Connection conn;

    public String getConnectionString() {
        return connectionString;
    }

    public DataAccessMariaDB(String connectionString) {
        this.connectionString = connectionString;
        this.tipoDA = TipoDA.CLASS_FORNAME;
    }

    public DataAccessMariaDB(String connectionString, TipoDA tipoDA) {
        this.connectionString = connectionString;
        this.tipoDA = tipoDA;
    }

    @Override
    public Connection getConnection() throws SQLException, NamingException {
        try {
            if (tipoDA==TipoDA.DATASOURCE) {
                conn = ((DataSource) InitialContext.doLookup(connectionString)).getConnection();
            }else{
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(connectionString);
            }
        } catch (NamingException e) {
            throw new NamingException(e.getExplanation());
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    @Override
    public ResultSet querySQL(String sql) throws SQLException {
        ResultSet rst = null;
        try{
            rst = conn.createStatement().executeQuery(sql);
        }catch (SQLException e) {
            throw new SQLException(e);
        }
        return rst;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null){
            conn.close();
        }
    }

}
