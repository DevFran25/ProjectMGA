package pe.edu.utp.model;

import java.util.Date;

public class Entregable {

    private int id_entregable;
    private String id_proyecto;
    private String nombre;
    private String fecha;
    private String archivo;

    public Entregable() {
    }

    public Entregable(int id_entregable, String id_proyecto, String nombre, String fecha, String archivo) {
        this.id_entregable = id_entregable;
        this.id_proyecto = id_proyecto;
        this.nombre = nombre;
        this.fecha = fecha;
        this.archivo = archivo;
    }

    public int getId_entregable() {
        return id_entregable;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getArchivo() {
        return archivo;
    }

    @Override
    public String toString() {
        return "Entregable{" +
                "id_entregable=" + id_entregable +
                ", id_proyecto='" + id_proyecto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", archivo='" + archivo + '\'' +
                '}';
    }
}
