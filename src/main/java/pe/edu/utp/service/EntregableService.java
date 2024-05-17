package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Entregable;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntregableService {

    private final Connection cnn;
    public EntregableService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar un entregable del proyecto
    public void newEntregable(Entregable ent) throws SQLException, IOException {
        String consulta = String.format("CALL registrarEntregable(?, ?, ?, ?, ?)");
        ErrorLog.log(consulta, ErrorLog.Level.INFO);
        String id_entregable = String.valueOf(ent.getId_entregable());

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setInt(1, ent.getId_entregable());
            pstmt.setString(2, ent.getId_proyecto());
            pstmt.setString(3, ent.getNombre());
            pstmt.setDate(4, ent.getFecha());
            pstmt.setString(5, ent.getFile());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0){
                String msg = String.format("Cuidado, no se confirma el registro del entregable");
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e){
            String msg = String.format("No se puede crear el entregable porque ya existe uno igual");
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // Metodo para listar los entregables
    public List<Entregable> getAllEntregable() throws SQLException, NotFoundException {
        List<Entregable> lista = new LinkedList<>();

        String consulta = String.format("CALL listarEntregable");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){
                int id_entregable = rst.getInt("id_entregable");
                String id_proyecto = rst.getString("id_proyecto");
                String nombre = rst.getString("nombre");
                Date fecha = rst.getDate("fecha");
                String file = rst.getString("file");

                Entregable entregable = new Entregable(id_entregable, id_proyecto, nombre, fecha, file);
                lista.add(entregable);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ningun entregable en este proyecto");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
}