package Tickects.Modules;

import Tickects.Modelo.Incidencia;

import java.util.Date;

public class IncidenciaDataClass {

    //ATRIBUTOS
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private int estaResuelta;
    private String fechaInicio;
    private String fechaFin;
    private int idUsuario;
    private int dias;
    private String nombreUsuario;
    private String emailUsuario;
    private int idTecnico;
    private String nombreTecico;


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

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombreTecico() {
        return nombreTecico;
    }

    public void setNombreTecico(String nombreTecico) {
        this.nombreTecico = nombreTecico;
    }


    //CONSTRUCTOR
    public IncidenciaDataClass(int id, String descripcion, String solucion, int prioridad, int estaResuelta, String fechaInicio, String fechaFin, int idUsuario, int dias, String nombreUsuario, String emailUsuario, int idTecnico, String nombreTecico) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTecico = nombreTecico;
    }
    public IncidenciaDataClass(Incidencia incidencia, String nombreUsuario, String emailUsuario) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.isEstaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario =incidencia.getIdUsuario();
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTecico = nombreTecico;
    }
    public IncidenciaDataClass(Incidencia incidencia, String nombreUsuario, String emailUsuario, int idTecnico) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.isEstaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario =incidencia.getIdUsuario();
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTecico = nombreTecico;
    }
    public IncidenciaDataClass(int id) {
        this.id = id;
        this.descripcion = getDescripcion();
        this.solucion = getSolucion();
        this.prioridad = getPrioridad();
        this.estaResuelta = isEstaResuelta();
        this.fechaInicio = getFechaInicio();
        this.fechaFin = getFechaFin();
        this.idUsuario = getIdUsuario();
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTecico = nombreTecico;
    }


    public IncidenciaDataClass( String descripcion, int prioridad) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTecico = nombreTecico;
    }

    @Override
    public String toString() {
            return "╔═══════════════════════════════════════════════════════════════════════════╗\n" +
                    "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  INCIDENCIA  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n" +
                    "\n\t\tIncidencia con ID: " + id +
                    "\n\t\tComentarios del usuario: " + descripcion +
                    "\n\t\tPrioridad: " + prioridad +
                    "\n\t\tSolución: " + solucion +
                    "\n\t\tEstá resuelta: " + estaResuelta +
                    "\n\t\tFecha de inicio: " + fechaInicio +
                    "\n\t\tFecha de fin: " + fechaFin +
                    "\n\t\tDias trancurridos: " + dias +
                    "\n\t\tNombre del usuario: " + nombreUsuario +
                    "\n\t\tEmail del usuario: " + emailUsuario +
                    "\n\t\tId de tecnico: " + idTecnico +
                    "\n\t\tNombre de tecnico: " + nombreTecico +
                    "\n╚═══════════════════════════════════════════════════════════════════════════╝";

        }
        /*    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private Date fechaInicio;
    private Date fechaFin;
    private int idUsuario;
    private int dias;
    private String nombreUsuario;
    private String emailUsuario;
    private int idTecnico;
    private String nombreTecico;
*/
}
