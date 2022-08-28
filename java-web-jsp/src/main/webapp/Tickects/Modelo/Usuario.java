package Tickects.Modelo;

import java.util.ArrayList;


public class Usuario extends Persona{

    //ATRIBUTOS
    private ArrayList<Incidencia> incidencias;
    private ArrayList<Integer> ids = new ArrayList<>();

    private int id;
    private String destino;
    private String msg;
    private String asunto;


    //CONSTRUCTOR
    public Usuario( String nombre, String apel, String clave, String email) {
        super(nombre, apel, clave, email);
        id = (int)(Math.random()*100000);
        for (int comp : ids){
            if (comp == id){
                //esta ya registrado
                id = (int)(Math.random()*100000);
            }else {
                ids.add(id);
            }
        }
        this.incidencias = new ArrayList<>();
    }

    public Usuario(int id, String nombre, String apel, String clave, String email) {
        super(nombre, apel, clave, email);
        this.id = id;
    }

    //GETTERS & SETTERS


    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }
    public Incidencia getIncidencias(int index) {
        return incidencias.get(index);
    }

    public void setIncidencias(ArrayList<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }


    //OTHERS METHODS
    public void  insertaIncidencia(){
        incidencias.add(new Incidencia());
    }
    public void  insertaIncidencia(Incidencia incidencia){
        if (incidencias == null){incidencias = new ArrayList<>();}
        incidencias.add(new Incidencia(incidencia.getDescripcion(), incidencia.getPrioridad(), incidencia.getId(), incidencia.getIdUsuario()));
    }

    public boolean deleteIncidencia(int indice){
        if (incidencias.get(indice)!= null){
            incidencias.remove(indice);
            return true;
        }
        return false;
        }

    public Incidencia buscaIncidenciaById(int id){
        return incidencias.get(id);
    }

    public ArrayList<Incidencia> buscaIncidenciaByTerm(String termino){
        ArrayList<Incidencia> resultado = new ArrayList<>();
        for (Incidencia incidencia : incidencias){
            boolean desc = incidencia.getDescripcion().equals(termino);
            boolean sol = incidencia.getSolucion().equals(termino);
            if ( desc || sol ){
                resultado.add(incidencia);
                return resultado;
            }
            return resultado;
        }
    return resultado;
    }

    public int incidenciasAbiertas(){
        int cont = 0;
        for (Incidencia incidencia : incidencias){
            if (incidencia.isEstaResuelta() == 0){
                cont++;
            }
        }return cont;
    }

    @Override
    public boolean login(String usuario, String contraseña) {
        return super.login(usuario, contraseña);
    }

    public float prioridadMediaUsuario(){
        float n = 0;
        float sum = 0;
        for (Incidencia incidencia : incidencias){
            sum+= incidencia.getPrioridad();
            n = incidencias.size();
        }
        return sum/n;
    }


    @Override
    public String toString() {
        return "Usuario{" +  super.toString() +
                "incidencias=" + incidencias +
                ", ids=" + ids +
                ", id=" + id +
                ", destino='" + destino + '\'' +
                ", msg='" + msg + '\'' +
                ", asunto='" + asunto + '\'' +
                '}';
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
