package Tickects.Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Incidencia {

    //ATRIBUTOS
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private int estaResuelta;
    private String fechaInicio;
    private String fechaFin;
    private int idUsuario;

    public Incidencia(int id, String descripccion, String solucion, int prioridad, int estaResuelta, String fechaInicio, String fechaFin, int id_usuario, int id_tecnico) {
        this.id = id;
        this.descripcion = descripccion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = id_usuario;
        this.idTecnico = id_tecnico;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    private int idTecnico;

    private ArrayList<Integer> ids = new ArrayList<>();


    //CONSTRUCTOR
    public Incidencia() {
        id = (int) (Math.random() * (100000));
        for (int comp : ids) {
            if (comp == id) {
                //esta ya registrado
                id = (int) (Math.random() * (100000));
            } else {
                ids.add(id);
            }
        }
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        Date date = new Date();
        this.fechaInicio = (date.getYear()+1900)+"-0"+(date.getMonth()+1)+"-"+(date.getDay()+22);
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
    }

    public Incidencia(String descripcion, int prioridad, int id, int idUsuario) {
        this.id = (int) (Math.random() * (100000));
        for (int comp : ids) {
            if (comp == id) {
                //esta ya registrado
                id = (int) (Math.random() * (100000));
            } else {
                ids.add(id);
            }
        }
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta; //2022-05-25
        Date date = new Date();
        this.fechaInicio = (date.getYear()+1900)+"-0"+(date.getMonth()+1)+"-"+(date.getDay()+22);
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
    }

    //GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int isEstaResuelta() {
        return estaResuelta;
    }

    public void setEstaResuelta(int estaResuelta) {
        this.estaResuelta = estaResuelta;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //METHODS fecha inicio fecha fin
    /*
    public int diasAbierta() {
        Date hoy = new Date();
        return hoy.getDay() - fechaInicio;
    }

    public int diasResolverla() {
        return fechaFin.getDay() - fechaInicio.getDay();
    }
*/
    @Override
    public String toString() {
        return "\n╔═══════════════════════════════════════════════════════════════════════════╗\n" +
                "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  INCIDENCIA  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n" +
                "\n\t\tIncidencia con ID: " + id +
                "\n\t\tComentarios del usuario: " + descripcion +
                "\n\t\tPrioridad: " + prioridad +
                "\n\t\tSolución: " + solucion +
                "\n\t\tEstá resuelta: " + estaResuelta +
                "\n\t\tFecha de inicio: " + fechaInicio +
                "\n\t\tFecha de fin: " + fechaFin +
                "\n╚═══════════════════════════════════════════════════════════════════════════╝\n";

    }
}
