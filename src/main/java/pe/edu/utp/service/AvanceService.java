package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Avance;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AvanceService {

    private final Connection cnn;
    public AvanceService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar un avance del proyecto
    public void newAvance(Avance ace) throws SQLException, IOException {
        String consulta = String.format("CALL registrarAvance(?, ?, ?, ?)");
        ErrorLog.log(consulta, ErrorLog.Level.INFO);
        String id_avance = String.valueOf(ace.getId_avance());

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setInt(1, ace.getId_avance());
            pstmt.setString(2, ace.getId_proyecto());
            pstmt.setString(3, ace.getDni_colaborador());
            pstmt.setString(4, ace.getProgreso());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0){
                String msg = String.format("Cuidado, no se confirma el registro del avance");
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e){
            String msg = String.format("No se puede crear el avance porque ya existe uno igual");
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // METODO PARA ACTUALIZAR AVANCE
    public void updateAvance(Avance ace) throws SQLException, IOException {
        String consulta = "UPDATE avance SET id_proyecto = ?, dni_colaborador = ?, progreso = ? WHERE id_avance = ?";
        ErrorLog.log(consulta, ErrorLog.Level.INFO);

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setInt(1, ace.getId_avance());
            pstmt.setString(2, ace.getId_proyecto());
            pstmt.setString(3, ace.getDni_colaborador());
            pstmt.setString(4, ace.getProgreso());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0) {
                String msg = "Cuidado, no se ha actualizado ningún avance.";
                throw new NotFoundException(msg);
            }
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // Metodo para listar los avances
    public List<Avance> getAllAvance() throws SQLException, NotFoundException{
        List<Avance> lista = new LinkedList<>();

        String consulta = String.format("CALL listarAvances");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){
                int id_avance = rst.getInt("id_avance");
                String id_proyecto = rst.getString("id_proyecto");
                String dni_colaborador = rst.getString("dni_colaborador");
                String progreso = rst.getString("progreso");

                Avance avance = new Avance(id_avance, id_proyecto, dni_colaborador, progreso);
                lista.add(avance);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ningun avance en este proyecto");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
}

