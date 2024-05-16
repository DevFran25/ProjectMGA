package pe.edu.utp.model;

public class Avance {

    private int id_avance;
    private String id_proyecto;
    private String dni_colaborador;
    private String progreso;

    public Avance() {
    }

    public Avance(int id_avance, String id_proyecto, String dni_colaborador, String progreso) {
        this.id_avance = id_avance;
        this.id_proyecto = id_proyecto;
        this.dni_colaborador = dni_colaborador;
        this.progreso = progreso;
    }

    public int getId_avance() {
        return id_avance;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public String getDni_colaborador() {
        return dni_colaborador;
    }

    public String getProgreso() {
        return progreso;
    }

    @Override
    public String toString() {
        return "Avance{" +
                "id_avance=" + id_avance +
                ", id_proyecto=" + id_proyecto +
                ", dni_colaborador=" + dni_colaborador +
                ", progreso='" + progreso + '\'' +
                '}';
    }
}
