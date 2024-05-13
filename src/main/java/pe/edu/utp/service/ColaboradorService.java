package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Colaborador;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ColaboradorService {

    private final Connection cnn;

    public ColaboradorService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void newColaborador(Colaborador cob) throws SQLException, IOException {
        String strSQL = String.format("CALL RegistrarColaborador(?, ?, ?, ?, ?, ?)");
        ErrorLog.log(strSQL, ErrorLog.Level.INFO);
        String dni_colaborador = cob.getDni_colaborador();

        try {
            PreparedStatement pstmt = cnn.prepareStatement(strSQL);
            pstmt.setString(1, cob.getDni_colaborador());
            pstmt.setString(2, cob.getNombre());
            pstmt.setString(3, cob.getApellidos());
            pstmt.setString(4, cob.getTelefono());
            pstmt.setString(5, cob.getEmail());
            pstmt.setString(6, cob.getCargo());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0) {
                String msg = String.format("Cuidado, no se tiene confirmación de que se haya agregado al colaborador con el identificador %s", dni_colaborador);
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = String.format("No se pudo crear colaborador por que ya esta registrado", dni_colaborador);
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    //Metodo para listar Colaboradores
    public List<Colaborador> getAllColaboradores() throws SQLException, NotFoundException {
        List<Colaborador> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarColaboradores()");
        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                String dni_colaborador = rst.getString("dni_colaborador");
                String nombres = rst.getString("nombres");
                String apellidos = rst.getString("apellidos");
                String telefono = rst.getString("telefono");
                String email = rst.getString("email");
                String cargo = rst.getString("cargo");

                Colaborador colaborador = new Colaborador(dni_colaborador,nombres,apellidos,telefono,email,cargo);
                lista.add(colaborador);
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
