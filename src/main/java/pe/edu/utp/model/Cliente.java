package pe.edu.utp.model;

public class Cliente {

    //Atributos
    private String identificacion;
    private String tipo_cliente;
    private String nombre;
    private String apellidos;
    private String email;
    private String celular;

    private int idCliente;

    //Constructor Vacio
    public Cliente() {
    }


    //Constructor Lleno
    public Cliente(String identificacion, String tipo_cliente, String nombre, String apellidos, String email, String celular) {
        this.identificacion = identificacion;
        this.tipo_cliente = tipo_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.celular = celular;
    }

    //Get

    public int getIdCliente() {
        return idCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "identificacion='" + identificacion + '\'' +
                ", tipo_cliente='" + tipo_cliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", idCliente=" + idCliente +
                '}';
    }
}
