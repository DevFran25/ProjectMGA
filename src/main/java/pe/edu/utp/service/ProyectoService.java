package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ProyectoService {

    private final Connection cnn;

    public ProyectoService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Método para Registrar Proyectos
    public void newProyectos(Proyecto proy) throws SQLException, IOException {
        String strSQL = String.format("CALL RegistrarProyecto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ErrorLog.log(strSQL, ErrorLog.Level.INFO);
        String idProyecto = proy.getId_proyecto();

        try {
            PreparedStatement pstmt = cnn.prepareStatement(strSQL);
            pstmt.setString(1, proy.getId_proyecto());
            pstmt.setInt(2, proy.getId_cliente());
            pstmt.setString(3, proy.getDni_colaborador());
            pstmt.setString(4, proy.getNombre());
            pstmt.setString(5, proy.getUbicacion());
            pstmt.setDouble(6, proy.getCosto());
            pstmt.setString(7, proy.getFecha_inicio());
            pstmt.setString(8, proy.getFecha_fin());
            pstmt.setString(9, proy.getEstado());
            pstmt.setString(10, proy.getFoto());


            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0) {
                String msg = String.format("Cuidado, no se tiene confirmación de que se haya agregado al proyecto con el identificador %s", idProyecto);
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = String.format("No se pudo crear proyecto por que ya esta registrado", idProyecto);
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }
}
