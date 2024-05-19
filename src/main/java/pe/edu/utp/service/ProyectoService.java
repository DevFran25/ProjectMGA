package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Colaborador;
import pe.edu.utp.model.Proyecto;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProyectoService {

    private final Connection cnn;

    private final ClientesService clientesService;
    public ProyectoService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
        this.clientesService = new ClientesService(dao); // instancia de ClientesService
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

    //Metodo para listar Proyectos
    public List<Proyecto> getAllProyectos() throws SQLException, NotFoundException {
        List<Proyecto> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarProyectos()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                String id_proyecto = rst.getString("id_proyecto");
                int id_cliente = rst.getInt("id_cliente");
                String dni_colaborador = rst.getString("dni_colaborador");
                String nombre = rst.getString("nombre");
                String ubicacion = rst.getString("ubicacion");
                Float costo = rst.getFloat("costo");
                String fecha_inicio = rst.getString("fecha_inicio");
                String fecha_fin= rst.getString("fecha_fin");
                String estado = rst.getString("estado");
                String foto = rst.getString("foto");

                Proyecto proyecto = new Proyecto(id_proyecto,id_cliente,dni_colaborador,nombre,ubicacion,costo,fecha_inicio,fecha_fin,estado,foto);
                lista.add(proyecto);
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

    //Metodo Combo Clientes
    public String getComboClientes() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT id_cliente, nombre FROM Cliente";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                int idCliente = rst.getInt("id_cliente");
                String nombreCliente = rst.getString("nombre");
                sb.append(String.format("<option value=\"%d\">%s</option>", idCliente, nombreCliente));
            }
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener la lista de clientes");
        }

        return sb.toString();
    }

    //Metodo Combo Colaboradores
    public String getComboColaboradores() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT dni_colaborador, nombres FROM Colaborador";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                String dni_colaborador = rst.getString("dni_colaborador");
                String nombres = rst.getString("nombres");
                sb.append(String.format("<option value=\"%s\">%s</option>", dni_colaborador, nombres));
            }
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener la lista de colaboradores");
        }

        return sb.toString();
    }

}
