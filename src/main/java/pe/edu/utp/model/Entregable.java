package pe.edu.utp.model;

import java.util.Date;

public class Entregable {

    private int id_entregable;
    private String id_proyecto;
    private String nombre;
    private Date fecha;
    private String file;

    public Entregable(int id_entregable, String id_proyecto, String nombre, long fecha, String file) {
    }

    public Entregable(int id_entregable, String id_proyecto, String nombre, Date fecha, String file) {
        this.id_entregable = id_entregable;
        this.id_proyecto = id_proyecto;
        this.nombre = nombre;
        this.fecha = fecha;
        this.file = file;
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

    public java.sql.Date getFecha() {
        return (java.sql.Date) fecha;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Entregable{" +
                "id_entregable=" + id_entregable +
                ", id_proyecto=" + id_proyecto +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", file='" + file + '\'' +
                '}';
    }
}
