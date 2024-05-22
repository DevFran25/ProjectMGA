package pe.edu.utp.utils;

import pe.edu.utp.model.Proyecto;
import pe.edu.utp.service.LoginService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Paginacion {

    //Conexion a BD
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    Connection conn = null;

    //Variables de Paginacion
    private Integer nroRegistros;
    private Integer registrosPorPagina;
    private Integer indice;
    private Integer paginaActual;
    private Integer totalPaginas;

    public Paginacion(Integer paginaActual, Integer registrosPorPagina){
        try {
            conn = dao.getConnection();
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
        this.registrosPorPagina = registrosPorPagina;
        this.indice = 0;
        this.paginaActual = paginaActual;

        this.calcularPaginas();
    }

    public List<Proyecto> obtenerProyectos(){
        List<Proyecto> proyectos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rst = null;
        String strSQL = String.format("CALL paginacionProyectos(?, ?)");
        try {
            stmt = conn.prepareStatement(strSQL);
            stmt.setInt(1, this.indice);
            stmt.setInt(2, this.registrosPorPagina);
            rst = stmt.executeQuery();

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
                proyectos.add(proyecto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return proyectos;
    }

    public void calcularPaginas(){
        String sql = "CALL verTotalProyectos()";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                nroRegistros = rs.getInt("Total de Proyectos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.totalPaginas = (int) Math.ceil((double) this.nroRegistros / this.registrosPorPagina);
        this.indice = ( this.paginaActual - 1 ) * this.registrosPorPagina;
    }

    @Override
    public String toString() {
        return "Paginacion{" +
                "nroRegistros=" + nroRegistros +
                ", registrosPorPagina=" + registrosPorPagina +
                ", indice=" + indice +
                ", paginaActual=" + paginaActual +
                ", totalPaginas=" + totalPaginas +
                '}';
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }
}
