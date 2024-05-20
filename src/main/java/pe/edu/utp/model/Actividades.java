package pe.edu.utp.model;

public class Actividades {

    private int id_actividad;
    private String id_proyecto;
    private String nombre;
    private String estado;

    public Actividades() {
    }

    public Actividades(int id_actividad, String id_proyecto, String nombre, String estado) {
        this.id_actividad = id_actividad;
        this.id_proyecto = id_proyecto;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Avance{" +
                "id_actividad=" + id_actividad +
                ", id_proyecto=" + id_proyecto +
                ", nombre=" + nombre +
                ", estado='" + estado + '\'' +
                '}';
    }
}