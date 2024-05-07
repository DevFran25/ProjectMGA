package pe.edu.utp.service;
import pe.edu.utp.exceptions.*;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsuariosService {

    private final Connection cnn;

    //Constructor
    public UsuariosService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }


    // Método para Registrar Usuarios
    public void newUsuario(Usuario user) throws SQLException, IOException {
        String strSQL = String.format("CALL RegistrarUsuario(?, ?, ?)");
        ErrorLog.log(strSQL, ErrorLog.Level.INFO);
        String dni = user.getDni_colaborador();

        try {
            PreparedStatement pstmt = cnn.prepareStatement(strSQL);
            pstmt.setString(1, user.getDni_colaborador());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, md5(user.getPassword()));


            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0) {
                String msg = String.format("Cuidado, no se tiene confirmación de que se haya agregado al usuario con el DNI %s", dni);
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = String.format("No se pudo crear cuenta de user por que ya esta registrado", dni);
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }


    // MD5
    public static String md5(String data) throws IOException {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest msg = (MessageDigest) md.clone();
            msg.update(data.getBytes());
            return byteArrayToHex(msg.digest());
        } catch (CloneNotSupportedException | NoSuchAlgorithmException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            return data;
        }
    }
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }



}
