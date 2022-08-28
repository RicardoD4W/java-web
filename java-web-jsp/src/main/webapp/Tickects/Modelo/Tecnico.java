package Tickects.Modelo;

import java.util.ArrayList;

public class Tecnico extends Persona {

    //ATRIBUTOS
    private ArrayList<Incidencia> incidencias;
    private ArrayList<Integer>    ids = new ArrayList<>();

    private int id;
    private String destino;
    private String msg;
    private String asunto;



    //CONSTRUCTOR
    public Tecnico( String nombre, String apel, String clave, String email) {
       super(nombre,apel,clave,email);
        this.id = (int)(Math.random()*100000);
        for (int comp : getIds()){
            if (comp == id){
                //esta ya registrado
                this.id = (int)(Math.random()*100000);
            }else {
                getIds().add(id);
            }
        }
        this.incidencias = new ArrayList<>();
    }

    public Tecnico( Tecnico t1) {
        super(t1.getNombre(), t1.getApel(),t1.getClave(),t1.getEmail());
        this.id = t1.getId();
        this.incidencias = t1.getIncidencias();
        this.ids = t1.getIds();
    }

    public Tecnico(int id, String nombre, String apel, String clave, String email) {
        super(nombre, apel, clave, email);
        this.id = (int)(Math.random()*100000);
    }

    //GETTERS & SETTERS


    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(ArrayList<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    @Override
    public int getId() {
        return id;
    }
    public void  insertaIncidencia(Incidencia incidencia){
        incidencias.add(new Incidencia(incidencia.getDescripcion(), incidencia.getPrioridad(), incidencia.getId(), incidencia.getIdUsuario()));
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    //METHODS
    public Incidencia buscaIncidenciaById(int id){return new Incidencia();}

    public ArrayList<Incidencia> buscaIncidenciaByTerm(String id){return new ArrayList<Incidencia>();}

    public int incidenciasAbiertas(){
        int cont = 0;
        for (Incidencia incidencia : incidencias){
            if (incidencia.isEstaResuelta() == 0){
                cont++;
            }
        }return cont;
    }

    public int incidenciasCerradas(){
        int cont = 0;
        for (Incidencia incidencia : incidencias){
            if (incidencia.isEstaResuelta() == 1){
                cont++;
            }
        }return cont;
    }

    @Override
    public boolean login(String usuario, String contraseña) {
        return super.login(usuario, contraseña);
    }

    public float prioridadMediaTecnico(){
        float n = 0;
        float sum = 0;
        for (Incidencia incidencia : incidencias){
            sum+= incidencia.getPrioridad();
            n = incidencias.size();
        }
        return sum/n;
    }

    public void asignaIncidencia(Incidencia incidencia){
        incidencias.add(incidencia);
    }

    public boolean cierraIncidencia(int index , String solucion){
        if (incidencias.get(index) != null){
            incidencias.get(index).setSolucion(solucion);
            incidencias.get(index).setEstaResuelta(1);
        }
        return false;
    }


    @Override
    public String toString() {
        return "Tecnico{" + super.toString() +
                "incidencias=" + incidencias +
                ", ids=" + ids +
                ", id=" + id +
                ", destino='" + destino + '\'' +
                ", msg='" + msg + '\'' +
                ", asunto='" + asunto + '\'' +
                '}';
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}
