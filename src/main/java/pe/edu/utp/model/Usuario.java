package pe.edu.utp.model;

public class Usuario {

    //Atributos
    private String dni_colaborador;
    private String username;
    private String password;


    //Constructor Vacio
    public Usuario() {
    }


    //Constructor Lleno
    public Usuario(String dni_colaborador, String username, String password) {
        this.dni_colaborador = dni_colaborador;
        this.username = username;
        this.password = password;
    }

    //Get

    public String getDni_colaborador() {
        return dni_colaborador;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    // To String
    @Override
    public String toString() {
        return "Usuario{" +
                ", dni_colaborador='" + dni_colaborador + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
