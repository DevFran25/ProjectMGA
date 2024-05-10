package pe.edu.utp.model;

public class Colaborador {

    //Atributos
    private String dni_colaborador;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String cargo;

    //Constructor Vacio
    public Colaborador() {
    }

    public Colaborador(String dni_colaborador, String nombre, String apellidos, String telefono, String email, String cargo) {
        this.dni_colaborador = dni_colaborador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.cargo = cargo;
    }

    public String getDni_colaborador() {
        return dni_colaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "dni_colaborador='" + dni_colaborador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
