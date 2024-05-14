package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClientesService {

    private final Connection cnn;

    public ClientesService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void newClientes(Cliente cli) throws SQLException, IOException {
        String strSQL = String.format("CALL RegistrarCliente(?, ?, ?, ?, ?, ?)");
        ErrorLog.log(strSQL, ErrorLog.Level.INFO);
        String identificacion = cli.getIdentificacion();

        try {
            PreparedStatement pstmt = cnn.prepareStatement(strSQL);
            pstmt.setString(1, cli.getIdentificacion());
            pstmt.setString(2, cli.getTipo_cliente());
            pstmt.setString(3, cli.getNombre());
            pstmt.setString(4, cli.getApellidos());
            pstmt.setString(5, cli.getEmail());
            pstmt.setString(6, cli.getCelular());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0) {
                String msg = String.format("Cuidado, no se tiene confirmación de que se haya agregado al cliente con el identificador %s", identificacion);
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = String.format("No se pudo crear proyecto por que ya esta registrado", identificacion);
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    //Metodo para listar Clientes
    public List<Cliente> getAllClientes() throws SQLException, NotFoundException {
        List<Cliente> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarClientes()");
        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                String identificacion = rst.getString("identificacion");
                String tipo_cliente = rst.getString("tipo_cliente");
                String nombre = rst.getString("nombre");
                String apellidos = rst.getString("apellidos");
                String email = rst.getString("email");
                String celular = rst.getString("celular");

                Cliente cliente = new Cliente(identificacion,tipo_cliente,nombre,apellidos,email,celular);
                lista.add(cliente);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ninguna cuenta en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
}
