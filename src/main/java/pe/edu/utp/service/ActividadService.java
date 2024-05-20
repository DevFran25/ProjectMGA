package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Actividades;
import pe.edu.utp.model.Avance;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ActividadService {

    private final Connection cnn;
    public ActividadService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar una actividad del proyecto
    public void newActividad(Actividades ace) throws SQLException, IOException {
        String consulta = String.format("CALL registrarActividad(?, ?, ?, ?)");
        ErrorLog.log(consulta, ErrorLog.Level.INFO);
        String id_avance = String.valueOf(ace.getId_actividad());

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setInt(1, ace.getId_actividad());
            pstmt.setString(2, ace.getId_proyecto());
            pstmt.setString(3, ace.getNombre());
            pstmt.setString(4, ace.getEstado());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0){
                String msg = String.format("Cuidado, no se confirma el registro de la actividad");
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e){
            String msg = String.format("No se puede crear la actividad porque ya existe uno igual");
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }



    // Metodo para listar las actividades
    public List<Actividades> getAllActividad() throws SQLException, NotFoundException{
        List<Actividades> lista = new LinkedList<>();

        String consulta = String.format("CALL listarActividades");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){
                int id_actividad = rst.getInt("id_actividad");
                String id_proyecto = rst.getString("id_proyecto");
                String nombre = rst.getString("nombre");
                String estado = rst.getString("estado");

                Actividades actividad = new Actividades(id_actividad, id_proyecto, nombre, estado);
                lista.add(actividad);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ninguna actividad en este proyecto");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

    /*//Metodo Combo Avances
    public String getComboAvances() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT id_avance, id_proyecto, dni_colaborador FROM Avance";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                int id_avance = rst.getInt("id_avance");
                String id_proyecto = rst.getString("id_proyecto");
                String dni_colaborador = rst.getString("id_colaborador");
                sb.append(String.format("<option value=\"%d\">%s</option>", id_avance, id_proyecto, dni_colaborador));
            }
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener la lista de avances");
        }

        return sb.toString();
    }*/
}