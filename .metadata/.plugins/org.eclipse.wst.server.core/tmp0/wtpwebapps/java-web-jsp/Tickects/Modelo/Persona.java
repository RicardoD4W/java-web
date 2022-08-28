package Tickects.Modelo;

public class Persona  {
    private int id;
    private String nombre;
    private String apel;
    private String clave;
    private String email;

    public Persona(String nombre, String apel, String clave, String email) {
        this.nombre = nombre;
        this.apel = apel;
        this.clave = clave;
        this.email = email;
    }

    public Persona() {
    }

    public Persona(int id, String nombre, String apel, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apel = apel;
        this.email = email;
    }


    public boolean login(String usuario, String contraseña){
        if (usuario.equals(nombre) && contraseña.equals(clave)) {
            return true;
        }
        return false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApel() {
        return apel;
    }

    public void setApel(String apel) {
        this.apel = apel;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apel='" + apel + '\'' +
                ", clave='" + clave + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
