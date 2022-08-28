package Tickects;

import Tickects.DAO.DAOManager;
import Tickects.DAO.Incidencia.DAOIncidencia;
import Tickects.DAO.Incidencia.DAOIncidenciaSQL;
import Tickects.DAO.Persona.DAOPersona;
import Tickects.DAO.Persona.DAOPersonaSQL;
import Tickects.Modelo.*;
import Tickects.Modules.EnviarTelegram;
import Tickects.Modules.IncidenciaDataClass;
import Tickects.Modules.Lectura_ficheros;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class GestionApp {

    //ATRIBUTOS
    private ArrayList<Usuario> usuarios;
    private ArrayList<Tecnico> tecnicos;
    private ArrayList<Admin> admins;
    DAOManager dao = DAOManager.getSinglentonInstance();
    private ArrayList<IncidenciaDataClass> incidenciaDataClasses;
    DAOIncidenciaSQL daoIncidenciaSQL = new DAOIncidenciaSQL();
    DAOPersonaSQL daoPersonaSQL = new DAOPersonaSQL();

    //CONSTRUCTOR
    public GestionApp() {
        usuarios = new ArrayList<>();
        tecnicos = new ArrayList<>();
        admins = new ArrayList<>();


        if (dao == null) System.out.println("El singlenton funciona");
        try{
            dao.open();
            System.out.println("Conexión establecida");
        }catch (Exception e){
            System.out.println("Error de conexión en la BBDD");
        }

    }

    //GETTERS & SETTERS
    public ArrayList<IncidenciaDataClass> getIncidenciaDataClasses() {
        return incidenciaDataClasses;
    }

    public void setIncidenciaDataClasses(ArrayList<IncidenciaDataClass> incidenciaDataClasses) {
        this.incidenciaDataClasses = incidenciaDataClasses;
    }
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(ArrayList<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }


    //METHODS
    public boolean insertaUsuario(String nombre, String apel, String clave, String mail){
        usuarios.add(new Usuario(nombre, apel, clave, mail));
        Usuario usuario = new Usuario(nombre, apel, clave, mail);
        daoPersonaSQL.insertU(usuario, dao);
        return true;
    }
    public boolean insertaUsuario(Usuario usuario){
        usuarios.add(new Usuario(usuario.getNombre(), usuario.getApel(), usuario.getClave(), usuario.getEmail()));
        return true;
    }

    public boolean insertaTecnico(String nombre, String apel, String clave, String mail){
        tecnicos.add(new Tecnico(nombre, apel, clave, mail));
        Tecnico tecnico = new Tecnico(nombre,apel,clave,mail);
        daoPersonaSQL.insertT(tecnico, dao);


        EnviarTelegram.enviaMensajeTelegram("Nuevo tecnico dado de alta en el sistema: "+nombre+" "+apel+" clave: "+clave+" mail: "+mail);
        return true;
    }
    public boolean insertaAdmin(String nombre, String apel, String clave, String mail){
        admins.add(new Admin(nombre,apel,clave,mail));
        Admin admin = new Admin(nombre,apel,clave,mail);
        daoPersonaSQL.insertA(admin, dao);
        return true;
    }

    public boolean insertaIncidencia(String descripcion ,int prioridad, Usuario usuario, int idUsuario){
        if (usuario.getIncidencias() == null){usuario.setIncidencias(new ArrayList<>());}
        usuario.getIncidencias().add(new Incidencia(descripcion, prioridad, generaIdIncidenciancidencia(), idUsuario));
        Incidencia incidencia = new Incidencia(descripcion, prioridad, generaIdIncidenciancidencia(), idUsuario);

        daoIncidenciaSQL.insert(incidencia,dao);
        return true;
    }
    public boolean insertaIncidencia(Incidencia incidencia , Usuario usuario){
        usuario.getIncidencias().add(new Incidencia(incidencia.getDescripcion(), incidencia.getPrioridad(), generaIdIncidenciancidencia(), usuario.getId()));
        return true;
    }
    /*
    * data class como clase copia
    * */

    public ArrayList<IncidenciaDataClass> buscaTodasLasInciddenciasAbiertas(){ //consultar por  user, almacenar en dataclass
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new ArrayList<>();
        for (Usuario usuario: usuarios){
            for (Incidencia incidencia:usuario.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0){
                    incidenciaDataClasses1.add(new IncidenciaDataClass(incidencia, usuario.getNombre(), usuario.getEmail()));
                }
            }
        }
        return incidenciaDataClasses1;
    }

    public int incidenciasAbiertas(){ //si esta resulta = false esta abierta
        int cont = 0, cont1=0;

       /* for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (!incidencia.isEstaResuelta()) cont++;
            }
        }*/

        for (Usuario usuario: usuarios){
            if (usuario.getIncidencias() == null){usuario.setIncidencias(new ArrayList<>());}
            for (Incidencia incidencia: usuario.getIncidencias()){
                if (incidencia.isEstaResuelta()==0) cont1++;
            }
        }
        return Math.abs(cont1);
    }



    public int incidenciasAbiertasAsignadas(){//si esta en tecnico, esta asignada
        int cont = 0;//!tecnicos.get().getIncidencias().get(i.isEstaResuelta() && tecnicos.get().getIncidencias()!=null)

        for (Tecnico tecnico: tecnicos){
            if (tecnico.getIncidencias() == null){tecnico.setIncidencias(new ArrayList<>());}
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0)  cont++;
            }
        }

        return cont;
    }

    public int incidenciasCerradas(){
        int cont = 0;
        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 1){
                    cont++;
                }
            }

        }
        return cont;
    }
    public ArrayList<Incidencia> incidenciasCerradas(Tecnico tecnico){
        ArrayList<Incidencia> incidencias = new  ArrayList<>();

        for (Incidencia incidencia : tecnico.getIncidencias()){
           if (incidencia.isEstaResuelta() == 1){
               incidencias.add(incidencia);
           }
        }
        return incidencias;
    }

    public ArrayList<IncidenciaDataClass> incidenciasCerradasResueltas(){
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new  ArrayList<>();

        for (Tecnico tecnico : tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 1){
                    incidenciaDataClasses1.add(new IncidenciaDataClass(incidencia, tecnico.getNombre(), tecnico.getEmail()));
                }
            }
        }

        return incidenciaDataClasses1;
    }

    public ArrayList<Incidencia> incidenciasAsignadasNoResueltas(Tecnico tecnico){
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        for (Incidencia incidencia: tecnico.getIncidencias()){
            if (incidencia.isEstaResuelta() == 0){
                incidencias.add(incidencia);
            }
        }
        return incidencias;
    }

    public void enviarCSV(String destino, String asunto) throws MessagingException {
        ArrayList<Incidencia> incidencias = new ArrayList<>();

        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0){
                    incidencias.add(incidencia);
                }
            }
        }
        try {//escribir scv
            BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"));
            for (int i = 0; i<= incidencias.size()-1; i++){
                bw.write(String.valueOf(incidencias.get(i)) +";");
            }
            bw.close();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }


        boolean resultado = false;

        //direccion q remite
        String emisor = Lectura_ficheros.leerFichero("emisor.txt");
        String username = Lectura_ficheros.leerFichero("emisor.txt");
        String password = Lectura_ficheros.leerFichero("password.txt");

        //server host
        String host = Lectura_ficheros.leerFichero("host.txt");

        //datos server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        //sesion in
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource("./data.csv")));
            adjunto.setFileName("data.csv");
            MimeMultipart multiParte = new MimeMultipart();

            BodyPart texto = new MimeBodyPart();
            texto.setText("Datos de incidencias adjuntos");

            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

        MimeMessage message = new MimeMessage(session);

// Se rellena el From
        message.setFrom(new InternetAddress(emisor));

// Se rellenan los destinatarios
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));

// Se rellena el subject
        message.setSubject(asunto);

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect(emisor,password);
        t.sendMessage(message,message.getAllRecipients());
        t.close();


    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciasAsignadas(int i){ //consultar por tecnico almacenar en dataclass
        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia:tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0){
                    incidenciaDataClasses.add(new IncidenciaDataClass(incidencia, tecnico.getNombre(), tecnico.getEmail()));
                }
            }
        }
        return incidenciaDataClasses;
    }

    private int generaIdIncidenciancidencia(){
        return  (int)(Math.random()*100000);
    }

    private int generaIdUsser(){
        return (int)(Math.random()*100000);
    }

    public Usuario buscaUsuario(String nombre){
        for(Usuario usuario : usuarios){
            if (usuario.getNombre().equals(nombre)){
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscaUsuario(int id){
        for(Usuario usuario : usuarios){
            if (usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }

    public Tecnico buscaTecnico(String nombre){
        for(Tecnico tecnico: tecnicos){
            if (tecnico.getNombre().equals(nombre)){
                return tecnico;
            }
        }
        return null;
    }
    public Admin buscaAdmin(String nombre){
        for(Admin admin : admins){
            if (admin.getNombre().equals(nombre)){
                return admin;
            }
        }
        return null;
    }

    public Usuario buscaUsuarioById(int id){
        for (Usuario usuario : usuarios){
            if (usuario.getId()== id){
                return usuario;
            }
        }
        return null;
    }

    public int login(String usuario, String contraseña){
        for (Admin admin: admins){
            if (admin.login(usuario,contraseña)){
                return 0;
            }
        }

        for (Usuario uusuario: usuarios){
            if (uusuario.login(usuario,contraseña)){
                return 1;
            }
        }

        for (Tecnico tecnico: tecnicos){
            if (tecnico.login(usuario,contraseña)){
                return 2;
            }
        }
        return -1;
    }

    public Object buscaUsser(String parameter){ //nombre, apellido , clave, email
        for (Usuario usuario: usuarios){
            if (usuario.getNombre().equals(parameter)){
                return usuario;
            }
            if (usuario.getApel().equals(parameter)){
                return usuario;
            }
            if (usuario.getClave().equals(parameter)){
                return usuario;
            }
            if (usuario.getEmail().equals(parameter)){
                return usuario;
            }
        }
        return null;
    }

    public void cargarIncidencias() {
        Gson gson = new Gson();

        String recoverJson = (Lectura_ficheros.leerFichero("data.json"));

        if (recoverJson!=null){
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            usuarios = gson.fromJson(recoverJson, userListType);
        }
        }

    public void cargarDatos() {
        usuarios = daoPersonaSQL.readALLU(dao,"usuario");
        tecnicos = daoPersonaSQL.readALLT(dao,"tecnico");
        admins =  daoPersonaSQL.readALLA(dao,"admin");

        ArrayList<Incidencia> incidencias =  daoIncidenciaSQL.readALL(dao);
        for (Incidencia incidencia : incidencias){
            for (Usuario usuario : usuarios){
                if (incidencia.getIdUsuario() == usuario.getId()){
                    usuario.insertaIncidencia(incidencia);
                }
            }

            for (Tecnico tecnico: tecnicos){
                if (incidencia.getIdTecnico() == tecnico.getId()){
                    tecnico.insertaIncidencia(incidencia);
                }
            }
        }
    }


    public void cargarDatos2() {
        usuarios = daoPersonaSQL.readALLU(dao,"usuario");
        tecnicos = daoPersonaSQL.readALLT(dao,"tecnico");
        admins =  daoPersonaSQL.readALLA(dao,"admin");

        ArrayList<Incidencia> incidencias =  daoIncidenciaSQL.readALL(dao);
        for (Incidencia incidencia : incidencias){
            for (Usuario usuario : usuarios){
                if (incidencia.getIdUsuario() == usuario.getId()){
                    usuario.insertaIncidencia(incidencia);
                }
            }

            for (Tecnico tecnico: tecnicos){
                if (incidencia.getIdTecnico() == tecnico.getId()){
                    tecnico.insertaIncidencia(incidencia);
                }
            }
        }



    }


    public void parche(String usuario){
        if (buscaUsuario(usuario).getIncidencias() == null){
            buscaUsuario(usuario).setIncidencias(new ArrayList<>());
        }
    }

        public void actualizarClave(Usuario usuario, String clave){
        daoPersonaSQL.update(dao,clave,usuario.getId(),"usuario");
        }

    public void actualizarClave(Tecnico tecnico, String clave){
        daoPersonaSQL.update(dao,clave,tecnico.getId(),"tecnico");
    }

        public void escribirAjson(){
        Gson gson = new Gson();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("data.json"));
                bw.write(gson.toJson(usuarios));
                bw.close();
                System.out.println("Cambios guardados con éxito");
            } catch (IOException e) {
                System.out.println("No se ha podido guardar, contacte con soporte");
            }
        }

    public String recoveryLogs(){
        String texto = "";
         texto = Lectura_ficheros.leerFichero("log.dat");

         return texto;
    }


public String exportJson(){

        Gson gson = new Gson();
        String json="";

        for (int i = 0; i<= usuarios.size()-1; i++){//datos en json aqui
             json += gson.toJson(usuarios.get(i));
             if (i-1>=0){
             }else {json += ",";}

        }


        return json;
}

public String mostrarPropiedades(String nombreFichero){
    String texto = "";
    String mensaje = "";

    //Declarar una variable BufferedReader
    BufferedReader br = null;
    try {

        br = new BufferedReader(new FileReader(nombreFichero));

        texto = br.readLine();

        while(texto != null)
        {
            //Hacer lo que sea con la línea leída
            mensaje += texto + "\n";

            //Leer la siguiente línea
            texto = br.readLine();
        }
    }
    catch (FileNotFoundException e) {
        System.out.println("Error: Fichero no encontrado");
        System.out.println(e.getMessage());
    }
    catch(Exception e) {
        System.out.println("Error de lectura del fichero");
        System.out.println(e.getMessage());
    }
    finally {
        try {
            if(br != null)
                br.close();
        }
        catch (Exception e) {
            System.out.println("Error al cerrar el fichero");
            System.out.println(e.getMessage());
        }
    }

    return mensaje;
}


    public float prioridadMediaApp(){
        int contUsers = 0;
        int contIncidencias = 0;

        for (Usuario usuario : usuarios){
            for (Incidencia incidencia : usuario.getIncidencias()){
                contUsers+= incidencia.getPrioridad();
                contIncidencias++;
            }
        }
        return (float)contUsers/contIncidencias;
    }

    public Incidencia buscaIncidencia(int idIncidencia){
        for (Usuario usuario: usuarios){
            if (usuario.getIncidencias() == null){usuario.setIncidencias(new ArrayList<>());}
            for(Incidencia incidencia: usuario.getIncidencias()){
                if (incidencia.getId()==idIncidencia){
                    return incidencia;
                }
            }
        }
        for(Tecnico tecnico: tecnicos){
            if (tecnico.getIncidencias() == null) { tecnico.setIncidencias(new ArrayList<>());}
            for(Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.getId()==idIncidencia){
                    return incidencia;
                }
            }
        }

        return null;
    }

    public Tecnico buscaTecnico(int id){
        for (Tecnico tecnico: tecnicos){
            if (tecnico.getId()==id){
                return tecnico;
            }
        }
        return null;
    }

    public boolean estaIncidenciaAsignada(int i){
        for (Usuario usuario: usuarios){
            for(Incidencia incidencia: usuario.getIncidencias()){
                if (incidencia.getId()==i && incidencia.isEstaResuelta() == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public Usuario buscaUsuarioByIncidencia(int idIncidencia){
        for (Usuario usuario: usuarios){
            for (Incidencia incidencia: usuario.getIncidencias()){
                if (incidencia.getId()==idIncidencia){
                    return usuario;
                }
            }
        }
        return null;
    }

    public boolean asignaIncidencia(Incidencia incidencia, int idUsuario){
        buscaUsuarioById(idUsuario).getIncidencias().add(incidencia);
        return true;
    }
    public boolean asignaIncidenciaaTecnico(Incidencia incidencia, Tecnico tecnico){
        if (incidencia!= null && tecnico!= null){
            tecnico.asignaIncidencia(incidencia);
            String sentencia = "UPDATE incidencia SET id_tecnico = "+tecnico.getId()+" WHERE id = "+incidencia.getId()+" ;";
            String sentencia2 = "UPDATE tecnico SET id_incidencia ="+incidencia.getId()+" WHERE id = "+tecnico.getId()+" ;";
            daoIncidenciaSQL.litreal(dao, sentencia);
            daoIncidenciaSQL.litreal(dao, sentencia2);
            System.out.println("Incidencia asignada con éxito");
            EnviarTelegram.enviaMensajeTelegram("nueva incidencia asignada al tecnico" + tecnico.getNombre());
            return true;
        }
        return false;
    }

    public boolean cierraIncidencia(int idIncidencia , String solución){
        buscaIncidencia(idIncidencia).setSolucion(solución);
        buscaIncidencia(idIncidencia).setEstaResuelta(1);

        String sentencia = "UPDATE incidencia SET solucion = '"+solución+"' WHERE id ="+idIncidencia+";";
        String sentencia2 = "UPDATE incidencia SET estaResuelta = true WHERE id ="+idIncidencia+";";
        daoIncidenciaSQL.litreal(dao, sentencia);
        daoIncidenciaSQL.litreal(dao, sentencia2);
        return true;
    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciaByTerm(String termino){
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new ArrayList<>();

        for (Usuario usuario: usuarios){
            for (Incidencia incidencia: usuario.getIncidencias()){
                if (incidencia.getDescripcion().equals(termino)){
                    incidenciaDataClasses1.add((new IncidenciaDataClass(incidencia, usuario.getNombre(), usuario.getEmail(), usuario.getId())));
                }
                try{
                    if (!incidencia.getSolucion().equals("") || incidencia.getSolucion()!= null){
                        if (incidencia.getSolucion().equals(termino)){
                            incidenciaDataClasses1.add((new IncidenciaDataClass(incidencia, usuario.getNombre(), usuario.getEmail(), usuario.getId())));
                        }
                    }
                }catch (Exception e){}

                if (incidencia.getPrioridad()== Integer.parseInt(termino)){
                    incidenciaDataClasses1.add((new IncidenciaDataClass(incidencia, usuario.getNombre(), usuario.getEmail(), usuario.getId())));
                }
            }

        }
        return incidenciaDataClasses1;
    }

    public int incidenciasAsignadas (String nombre){
        int numIncidendias = 0;

        for (Tecnico tecnico: tecnicos){
            if (tecnico.getIncidencias() == null){
                tecnico.setIncidencias(new ArrayList<Incidencia>());
            }
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0){
                    numIncidendias++;
                }

            }
        }
        return numIncidendias;
    }


    public ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertasByTecnico(int idTecnico){
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new ArrayList<>();
        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 0){
                    incidenciaDataClasses1.add((new IncidenciaDataClass(incidencia, tecnico.getNombre(), tecnico.getEmail(), idTecnico)));
                }
            }
        }
        return null;
    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradasByTecnico(int i){
        return null;
    }

    public float prioridadMediaTecnico (Tecnico tecnico){
        int cont = 0, ic = 0;
        for (Incidencia incidencia : tecnico.getIncidencias()){
            cont += incidencia.getPrioridad();
            ic++;
        }
        float media;
        try {
            media = cont/ic;
        }catch (ArithmeticException e){
             media = 0.0f;
        }


        return  media;
    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradas(){
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new ArrayList<>();
        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                if (incidencia.isEstaResuelta() == 1){
                    incidenciaDataClasses1.add(new IncidenciaDataClass(incidencia,tecnico.getNombre(), tecnico.getEmail()));
                }
            }
        }
        return incidenciaDataClasses1;
    }
    public void borrarTecnico(int idTecnico){
        Tecnico tecnicoaBorrar = null;
        for (Tecnico tecnico: tecnicos){
            if (tecnico.getId()==idTecnico){
                tecnicoaBorrar = new Tecnico(tecnico);
            }
        }
        //tecnicos.remove(tecnicoaBorrar);
        for (Tecnico tecnico: tecnicos){
            if (tecnico.equals(tecnicoaBorrar)){
                tecnicos.remove(tecnico);
                daoPersonaSQL.delete(dao,"id",Integer.toString(idTecnico),"tecnico");
            }
        }
    }



    public Incidencia mostrarIncidencias(Usuario usuario){
        for (Incidencia incidencia: usuario.getIncidencias()){
            return incidencia;
        }
        return null;
    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciasSinAsignar(){
        ArrayList<IncidenciaDataClass> incidenciaDataClasses1 = new ArrayList<>();
        ArrayList<Integer> idIncidenciaUser = new ArrayList<>();
        ArrayList<Integer> idIncidenciaTec = new ArrayList<>(); //estructiras auxiliares

        for (Usuario usuario: usuarios){
            for (Incidencia incidencia: usuario.getIncidencias()){
                idIncidenciaUser.add(incidencia.getId());
            }
        }
        for (Tecnico tecnico: tecnicos){
            for (Incidencia incidencia: tecnico.getIncidencias()){
                idIncidenciaTec.add(incidencia.getId());
            }
        }

        Collections.sort(idIncidenciaTec);
        Collections.sort(idIncidenciaUser);

        for (int i= 0; i<idIncidenciaTec.size()||i<idIncidenciaUser.size(); i++){
            if (idIncidenciaTec.get(i-(idIncidenciaTec.size()+idIncidenciaUser.size())) != idIncidenciaUser.get(i-(idIncidenciaTec.size()+idIncidenciaUser.size()))){
                incidenciaDataClasses1.add(new IncidenciaDataClass(idIncidenciaUser.get(i)));
            }
        }
        return incidenciaDataClasses1;
    }



    @Override
    public String toString() {
        return "GestionApp{" +
                "usuarios=" + usuarios +
                ", tecnicos=" + tecnicos +
                ", admins=" + admins +
                ", incidenciaDataClasses=" + incidenciaDataClasses +
                '}';
    }
}
