package Tickects.Modelo;

public class Admin extends Persona{
    //ATRIBUTOS

    private String destino;
    private String msg;
    private String asunto;


    //CONTRUCTOR
    public Admin(String nombre, String apel, String clave, String mail) {
        super(nombre, apel, clave,mail);
    }


    //GETTERS & SETTERS
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


    //METHODS


    @Override
    public boolean login(String usuario, String contraseña) {
        return super.login(usuario, contraseña);
    }

    @Override
    public String toString() {
        return   "Admin{" +super.toString() +
                "destino='" + destino + '\'' +
                ", msg='" + msg + '\'' +
                ", asunto='" + asunto + '\'' +
                '}';
    }
}
