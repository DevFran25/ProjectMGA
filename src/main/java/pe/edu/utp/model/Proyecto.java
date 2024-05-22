package pe.edu.utp.model;
import java.util.Comparator;
import java.util.Objects;

public class Proyecto implements Comparable<Proyecto>  {

    public static final Comparator<Proyecto> PROYECTO_COMPARATOR_NATURAL_ORDER =
            Comparator.comparing(Proyecto::getId_proyecto).thenComparing(Proyecto::getDni_colaborador).
                    thenComparing(Proyecto::getNombre).thenComparing(Proyecto::getId_cliente);
    @Override
    public int compareTo(Proyecto o) {
        return PROYECTO_COMPARATOR_NATURAL_ORDER.compare(this,o);
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto proyecto)) return false;
        return getId_cliente() == proyecto.getId_cliente() && Objects.equals(getId_proyecto(), proyecto.getId_proyecto()) && Objects.equals(getDni_colaborador(), proyecto.getDni_colaborador()) && Objects.equals(getNombre(), proyecto.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_proyecto(), getId_cliente(), getDni_colaborador(), getNombre());
    }


}
