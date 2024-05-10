package pe.edu.utp.model;


public class Proyecto {

    //Atributos
    private String id_proyecto;
    private int id_cliente;
    private String dni_colaborador;
    private String nombre;
    private String ubicacion;
    private float costo;
    private String fecha_inicio;
    private String fecha_fin;
    private String estado;
    private String foto;


    public Proyecto() {
    }

    public Proyecto(String id_proyecto, int id_cliente, String dni_colaborador, String nombre, String ubicacion, float costo, String fecha_inicio, String fecha_fin, String estado, String foto) {
        this.id_proyecto = id_proyecto;
        this.id_cliente = id_cliente;
        this.dni_colaborador = dni_colaborador;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.costo = costo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
        this.foto = foto;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getDni_colaborador() {
        return dni_colaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public float getCosto() {
        return costo;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public String getEstado() {
        return estado;
    }

    public String getFoto() {
        return foto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id_proyecto='" + id_proyecto + '\'' +
                ", id_cliente='" + id_cliente + '\'' +
                ", dni_colaborador='" + dni_colaborador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", costo=" + costo +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", estado='" + estado + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
