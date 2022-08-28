package Tickects;

import Tickects.DAO.DAOManager;
import Tickects.Modelo.Incidencia;
import Tickects.Modelo.Tecnico;
import Tickects.Modules.EnviarMail;
import Tickects.Modules.EnviarTelegram;

import javax.mail.MessagingException;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, MessagingException {//pongo la vista en el main ya que en el UML no existe la clase vista
        Scanner num = new Scanner(System.in);
        Scanner cad = new Scanner(System.in);



        boolean exit = false;
        boolean repetir;

        int opMenuLogin=0;

        GestionApp gestionApp = new GestionApp();
        gestionApp.insertaAdmin("admin","istrador","admin","admin@admin.com");
       // gestionApp.insertaAdmin("a","istrador","a","amin@admin.com");
        //gestionApp.insertaUsuario("a","a","a","root@gestionvirtual.com");

            gestionApp.cargarDatos();
     //   gestionApp.insertaIncidencia("prueba", 10, gestionApp.buscaUsuario("r"), gestionApp.buscaUsuario("r").getId());

        //carga datos de json
            String texto = "";
            //carga logs
          //  texto = gestionApp.recoveryLogs();










        do {
                menuLogIn();
/*
            Properties properties = new Properties();
            properties.load(new FileReader("file.properties"));
            properties.setProperty("Last_Log_In", " ");
            properties.setProperty("Path", "./file.properties");
            properties.setProperty("Invitado", "True");
            properties.store(new BufferedWriter(new FileWriter("file.properties")), "config");

            Properties propertiesAdmin = new Properties();
            properties.load(new FileReader("fileAdmin.properties"));
            properties.setProperty("Last_Log_In", " ");
            properties.setProperty("Path", "./file.properties");
            properties.setProperty("Invitado", "True");
            properties.store(new BufferedWriter(new FileWriter("fileAdmin.properties")), "config.Admin");
*/
            do {
                repetir = false;
                try{
                     opMenuLogin = num.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Valor no válido, vuelva a ingresar valor");
                    num.nextLine();
                    repetir = true;
                }
            } while (repetir);

                String nombre, apel, clave, mail, usuario = null, passw;

                switch (opMenuLogin){//0 ->admin, 1->usuario, 2->tecnico, -1->no se encuentra
                    case 1://ya estoy registrado
                        yaEstoyRegistrado(); usuario=cad.nextLine(); passw=cad.nextLine();

                        if (gestionApp.login(usuario, passw)==0){//admin
                            try {accediendoAdmin();} catch (InterruptedException e) {e.printStackTrace();}

                            //menu de admin
                            int opMenuAdmin = 0;
                            boolean exitMenuAdmin = false;

                            do {
                                //vista menu admin abiertas y noasignadas

                                menuDeAdmin(gestionApp.buscaAdmin(usuario).getNombre(),gestionApp.incidenciasAbiertas(),gestionApp.incidenciasAbiertasAsignadas());
                                do {
                                    repetir = false;
                                    try{
                                        opMenuAdmin = num.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Valor no válido, vuelva a ingresar valor");
                                        num.nextLine();
                                        repetir = true;
                                    }
                                } while (repetir);


                                //###########log##########

                                try {
                                    BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                    texto += "[Log-in A "+ usuario +" - "+ LocalDate.now() +"]\n";
                                    bw.write(texto);
                                    bw.close();
                                } catch (IOException e) {
                                    System.out.println("");
                                }


                                switch (opMenuAdmin){

                                    case 1://consultar todas las incidencias abiertas
                                        System.out.println(gestionApp.buscaTodasLasInciddenciasAbiertas());
                                        break;
                                    case 2://consultar las incidencias cerradas
                                        System.out.println(gestionApp.incidenciasCerradasResueltas());
                                        break;
                                    case 3://consultar incidencias por termino
                                        System.out.println("Inserte un término: descripccion, solucion o prioridad ...");
                                        String termino = cad.nextLine();
                                        System.out.println(gestionApp.buscaIncidenciaByTerm(termino));
                                        break;
                                    case 4://consultar los tecnicos
                                        System.out.println(gestionApp.getTecnicos());
                                        break;
                                    case 5://asignar incidencia a tecnico
                                        System.out.println(gestionApp.buscaTodasLasInciddenciasAbiertas());
                                        System.out.println("Introduzca el id de la incidencia a asignar");
                                        int idIncidencia = num.nextInt();

                                        gestionApp.cargarDatos2();
                                        System.out.println(gestionApp.getTecnicos());
                                        System.out.println("Introduzca el id del tecnico al cual asignar la incidencia");
                                        int idTecnico = num.nextInt();
                                        gestionApp.asignaIncidenciaaTecnico(gestionApp.buscaIncidencia(idIncidencia), gestionApp.buscaTecnico(idTecnico));

                                        //###########log##########
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Incidencia asignada a" + gestionApp.buscaTecnico(idTecnico) +" - "+ LocalDate.now() + "]"+" \n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        break;
                                    case 6://dar de alta a un tecnico
                                        registrarmeTécnico();/*String nombre, String apel, String clave, String mail*/
                                        nombre = cad.nextLine(); apel = cad.nextLine(); clave = cad.nextLine(); mail = cad.nextLine();
                                        gestionApp.insertaTecnico(nombre, apel, clave, mail);

                                        //###########log##########
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Técnico dado de alta" + nombre +" - "+ LocalDate.now() + "]"+"\n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        break;
                                    case 7://borrar un tecnico
                                        System.out.println(gestionApp.getTecnicos());
                                        System.out.println("Introduzca el id del tecnico a borrar");
                                        int id = num.nextInt();
                                        gestionApp.borrarTecnico(id);
                                        break;
                                    case 8://consultar los usuarios
                                        System.out.println(gestionApp.getUsuarios());
                                        break;
                                    case 9://estadisticas de la aplicación
                                        System.out.println("Actualmente hay "+gestionApp.incidenciasAbiertas()+" incidencias abiertas");
                                        System.out.println("Actualmente hay "+gestionApp.incidenciasAbiertasAsignadas()+" incidencias abiertas asignadas");
                                        System.out.println("Actualmente hay "+gestionApp.incidenciasCerradas()+" incidencias cerradas");

                                        break;
                                    case 10://muestra config
                                        System.out.println(gestionApp.mostrarPropiedades("file.properties"));

                                        break;
                                    case 11://enviar listadpo deinc abiertas a tecnico
                                        for (Tecnico tecnico: gestionApp.getTecnicos()){
                                            for (Incidencia incidencia: tecnico.getIncidencias()){
                                                if (incidencia.isEstaResuelta() == 0){
                                                    System.out.println(tecnico);
                                                    System.out.println(incidencia);
                                                }
                                            }
                                        }
                                        System.out.println("¿A qué técnico desea mandar las incidencias? (ponga su id)");
                                        int tecnico = num.nextInt();
                                        System.out.println("¿Asunto? Si no pone nada se mandará un asunto por defecto");
                                        String asunto = "Inicdencias asignadas";
                                        asunto = cad.nextLine();


                                    //    String destino = gestionApp.buscaTecnico(tecnico).getEmail();
                                    //    gestionApp.enviarCSV(destino, asunto);

                                        break;
                                    case 12://cerrar sesion
                                        exitMenuAdmin = true;
                                        break;
                                }

                            }while (!exitMenuAdmin);

                        }//<>cierra if admin#####################################################################################################################


                        if (gestionApp.login(usuario,passw)==1){//usuario
                            try {accediendoUser();} catch (InterruptedException e) {e.printStackTrace();}
/*
                            properties.setProperty("Last_Log_In", String.valueOf(LocalDateTime.now())); //properties login
                            properties.setProperty("Invitado", "False"); //properties login
                            properties.store(new BufferedWriter(new FileWriter("file.properties")), "config");

                            propertiesAdmin.setProperty("Last_Log_in_"+gestionApp.buscaUsuario(usuario).getNombre() , String.valueOf(LocalDateTime.now()));
                            propertiesAdmin.setProperty("Invitado_"+gestionApp.buscaUsuario(usuario).getNombre(), "False"); //properties login
                            propertiesAdmin.store(new BufferedWriter(new FileWriter("fileAdmin.properties")), "config.Admin");
*/

                            //menu de usuario
                            int opMenuUsuario = 0;
                            boolean exitMenuUser;

                            do{
                           //     System.out.println("Último inicio de sesión: " + properties.getProperty("Last_Log_In"));
                                gestionApp.parche(usuario);
                            menuDeUsuario(usuario, gestionApp.buscaUsuario(usuario).incidenciasAbiertas(),gestionApp.incidenciasAsignadas(usuario));

                            do {
                                repetir = false;
                                try{
                                   opMenuUsuario = num.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Valor no válido, vuelva a ingresar valor");
                                    num.nextLine();
                                    repetir = true;
                                }
                            } while (repetir);


                             exitMenuUser= false;
                                try {
                                    BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                    texto += "[Log-in U "+usuario+ " - " + LocalDate.now()+ " ]"+"\n";
                                    bw.write(texto);
                                    bw.close();
                                } catch (IOException e) {
                                    System.out.println("");
                                }



                                switch (opMenuUsuario){//menu usuario
                                    case 1: //registrar incidencia
                                        System.out.println("Introduzca una descripcción de la incidencia: ");
                                        String descripcion=cad.nextLine();
                                        System.out.println("Introduzca la prioridad de la incidencia: ");
                                        int prioridad=num.nextInt();
                                        gestionApp.insertaIncidencia(descripcion, prioridad, gestionApp.buscaUsuario(usuario), gestionApp.buscaUsuario(usuario).getId());
                                    //    EnviarMail.enviarMail(gestionApp.buscaUsuario(usuario).getEmail(),"Nueva incidencia registrada: \n\tDescripcion: "+descripcion+
                                     //           "\n\tPrioridad: "+prioridad+"\n\t","Nueva incidencia creada por el usuario "+usuario+" (si no es usted, contacte con nosotros)");

                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Incidencia registrada" + usuario +" - "+ LocalDate.now() + descripcion + "]"+"\n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        break;
                                    case 2://consultar inicidencias abiertas
                                        System.out.println(gestionApp.buscaUsuario(usuario));
                                        break;
                                    case 3://consultar incidencias cerradas
                                        System.out.println(gestionApp.buscaIncidenciasCerradas());
                                        break;
                                    case 4://mostrar mi perfil
                                        System.out.println(gestionApp.buscaUsuario(usuario));
                                        break;
                                    case 5://cambiar clave
                                        System.out.println("Introduzca una nueva clave: ");
                                        String nuevaClave = cad.nextLine();
                                        gestionApp.buscaUsuario(usuario).setClave(nuevaClave);
                                        gestionApp.actualizarClave(gestionApp.buscaUsuario(usuario), nuevaClave );

                                    //    EnviarMail.enviarMail(gestionApp.buscaUsuario(usuario).getEmail(),"Si no ha sido usted, contáctenos", "Contraseña renovada con éxito");
                                        break;
                                    case 6://guardar cambios

                                    //    gestionApp.escribirAjson();

                                        break;
                                    case 7://cerrar sesion
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Cierre sesión U" + usuario +" - "+ LocalDate.now() +" ]"+"\n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        exitMenuUser = true;
                                        break;
                                }//<>fin swicht
                            }while (!exitMenuUser);


                        }//<>cierra if users#####################################################################################################################


                        if (gestionApp.login(usuario,passw)==2){//tecnico
                            try {accediendoTecnico();} catch (InterruptedException e) {e.printStackTrace();}
                            int opMenuTecnico = 0;
                            boolean exitMenuTecnico = false;

                            //menu de tecnico
                            do {
                                menuDeTecnico(gestionApp.buscaTecnico(usuario).getNombre(), gestionApp.incidenciasAbiertas(), gestionApp.incidenciasAsignadas(usuario) , gestionApp.prioridadMediaTecnico(gestionApp.buscaTecnico(usuario)));
                                do {
                                    repetir = false;
                                    try{
                                        opMenuTecnico = num.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Valor no válido, vuelva a ingresar valor");
                                        num.nextLine();
                                        repetir = true;
                                    }
                                } while (repetir);
                                try {
                                    BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                    texto += "[Log-in T " + usuario +" - "+ LocalDate.now() + "] "+"\n";
                                    bw.write(texto);
                                    bw.close();
                                } catch (IOException e) {
                                    System.out.println("");
                                }
                                switch (opMenuTecnico){
                                    case 1://consultar incidencias asiganas no resueltas
                                        System.out.println( gestionApp.incidenciasAsignadasNoResueltas(gestionApp.buscaTecnico(usuario)));
                                        break;
                                    case 2://marcar incidencia como resuelta
                                        int idIncidencia; String solucion;
                                        System.out.println("Introduzca el numero de la incidencia a cerrar y, posteriormente su solución");
                                        idIncidencia = num.nextInt(); solucion = cad.nextLine();
                                        gestionApp.cierraIncidencia(idIncidencia, solucion);
                                    //    EnviarTelegram.enviaMensajeTelegram("Incidencia con ID: "+idIncidencia+" resuelta, detalles: \n\tsolución: "+solucion+" resuelta por"+usuario);
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Incidencia resuelta" + solucion +" - "+ LocalDate.now() + "]"+"\n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        break;
                                    case 3://consultar mis incidencias cerradas
                                        System.out.println(gestionApp.incidenciasCerradas(gestionApp.buscaTecnico(usuario)));;
                                        break;
                                    case 4://mostrar mi perfil
                                        System.out.println(gestionApp.buscaTecnico(usuario));
                                        break;
                                    case 5://cambiar clave
                                        System.out.println("Introduzca su nueva clave");
                                        String nuevaClave = cad.nextLine();

                                        gestionApp.buscaTecnico(usuario).setClave(nuevaClave);
                                        gestionApp.actualizarClave(gestionApp.buscaTecnico(usuario), nuevaClave);
                                        break;
                                    case 6://cerrar sesion
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("log.dat"));
                                            texto += "[Cierre sesión T " + usuario +" - "+ LocalDate.now() + "]"+"\n";
                                            bw.write(texto);
                                            bw.close();
                                        } catch (IOException e) {
                                            System.out.println("");
                                        }
                                        exitMenuTecnico = true;
                                        break;
                                }
                            }while (!exitMenuTecnico);
                        }//<>cierra if tecnico###################################################################################################################


                        if (gestionApp.login(usuario,passw)==-1){//no esta
                            try {accediendoError();} catch (InterruptedException e) {e.printStackTrace();}
                             errorLogIn();
                        }//<>cierra error########################################################################################################################

                        break;


                    case 2://reistrarme
                        System.out.println("Registrándose como usuario...");

                           //ussers
                                registrarmeUser();
                                gestionApp.insertaUsuario(nombre=cad.nextLine(), apel=cad.nextLine(), clave=cad.nextLine(), mail=cad.nextLine());
                       //         EnviarMail.enviarMail(mail,"Bienvenido y gracias por registrate en nuestro sistema","Inicio");

                        break;//<>cierra registrarme

                    case 3://modo invitado
                   /*     if (properties.getProperty("Invitado").equals("True")){
                            System.out.println(gestionApp.buscaTodasLasInciddenciasAbiertas());
                        }else{
                            System.out.println("Ya está registrado, modo invitado desabilitado");
                        }
                        break;*/
                }//<>cierramenu login



        }while (!exit);


    }
    //################################################################################################
    //############################            VISTA            #######################################
    //################################################################################################
    public static void accediendoError() throws InterruptedException {
        System.out.printf("Buscando datos, espere .");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.println("");
    }
    public static void accediendoAdmin() throws InterruptedException {
        System.out.printf("Accediendo como administrador, espere .");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.println("");
    }
    public static void accediendoUser() throws InterruptedException {
        System.out.printf("Accediendo como usuario, espere .");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.println();
    }
    public static void accediendoTecnico() throws InterruptedException {
        System.out.printf("Accediendo como tecnico, espere .");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.printf(".");
        Thread.sleep(1000);
        System.out.println("");
    }
    public static void errorLogIn(){
        System.out.println("""
                            3̛͉̦͉̣͎̞͕͖ͦͪ̎Ṛ̼̪̱͖̫͔͐͢r͉̬̖ͥ̓ͫ͗͘0̪̖̗̭̥̓͗̚͡ͅṞ̟̊ͭ͡ ̵͔͍̹͖̥͌ͥ͆̂1̢̝̰͖̗͕͓̆͒ͬ5̵̜̼̫͑̆4̉̂͗
                            3̛͉̦͉̣͎̞͕͖ͦͪ̎Ṛ̼̪̱͐͢        ͏̝̲̱̱̥ͅ３尺яѲ尺 １54    ̞͕͖Ṛ̼̪̱͖̫͔͐͢rͥ̓ͫ͗
                            3̛͉̦͉̣͎̞͕͖ͦͪ̎Ṛ̼̪̱͖̫͔͐͢r͉̬̖ͥ̓ͫ͗͘0̪̖̗̭̥̓͗̚͡ͅṞ̟̊ͭ͡ ̵͔͍̹͖̥͌ͥ͆̂1̢̝̰͖̗͕͓̆͒ͬ5̵̜̼̫͑̆4̉̂͗͏
                            
                                (usuario o contraseña incorrectos)
                            """);
    }
    public static void yaEstoyRegistrado(){
        System.out.println("""
                            ╔═════════════════════════════════════════════╗
                            
                             Introduzca sus credenciales:
                             
                                            1.-nombre 2.-contraseña 
                                            
                            ╚══════════════════════════════════════════════╝
                            """);
    }
    public static void registrarmeUser(){
        System.out.println("""
                            ╔═════════════════════════════════════════════════════════╗
                            
                             Registrando como usuario, introduzca sus datos:
                             
                                            1.-nombre 2.-apellido 3.-clave 4.-email
                                            
                            ╚═════════════════════════════════════════════════════════╝
                                            
                            """);
    }
    public static void registrarmeTécnico(){
        System.out.println("""
                            ╔═════════════════════════════════════════════════════════╗
                            
                             Registrando como tecnico, introduzca sus datos:
                             
                                            1.-nombre 2.-apellido 3.-clave 4.-email
                                            
                            ╚═════════════════════════════════════════════════════════╝
                                            
                            """);
    }

    public static void menuLogIn(){
        System.out.println("""
                            ╔═══════════════════════════════════════════════════════════════════════════╗
                            ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒    MENU DE INICIO    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                            
                                      Bienvenido a FernanTickect
                             ***Recuerda que para reportar una incidencia tienes que estar registrado***
                             
                                 -------------------------------------------------------------------
                                  [1]   Ya estoy registrado
                                 -------------------------------------------------------------------
                                  [2]   Registrarme
                                 -------------------------------------------------------------------
                                  [3]   Modo invitado
                                 -------------------------------------------------------------------
                                 
                            ╚═══════════════════════════════════════════════════════════════════════════╝
                            """);
    }

    public static void menuDeUsuario(String nombreUser, int incidenciasSinAsignar, int incidenciasAsignadas){
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════╗\n"+
                            "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒    MENU DE USUARIO    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n" +
                                    "\t\tBienvenido usuario "+nombreUser+"\n"+
                            "Actualmente tiene "+incidenciasSinAsignar+" incidencia/s sin asignar\n\t y "+incidenciasAsignadas+" incidencia/s ya asignadas\n\n"+
                            """
                                 ----------------------------------------------------------
                                 [1] Registrar una incidencia
                                 ----------------------------------------------------------
                                 [2] Consultar mis incidencias abiertas
                                 ----------------------------------------------------------
                                 [3] Consultar mis incidencias asignadas
                                 ----------------------------------------------------------
                                 [4] Mostrar mi perfil
                                 ----------------------------------------------------------
                                 [5] Cambiar clave de acceso
                                 ----------------------------------------------------------
                                 [6] Guardar cambios
                                 ----------------------------------------------------------
                                 [7] Cerrar sesión
                                 ----------------------------------------------------------
                                                     
                              ╚═══════════════════════════════════════════════════════════════════════════╝
                                    """



                            );
    }
    public static void menuDeTecnico(String nombreTecnico, int incidenciasAbiertas, int incidenciasAsignadas, float prioridadMedia){
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════╗\n"+
                "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒    MENU DE TECNICO    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n" +
                "\t\tBienvenido usuario "+nombreTecnico+"\n"+
                "Actualmente tiene "+incidenciasAbiertas+" incidencia/s abiertas\n\t y "+incidenciasAsignadas+" incidencia/s cerradas\n"+
                "La prioridad media de sus incidencias es "+prioridadMedia+" \n" + """
                   
                   ----------------------------------------------------------
                   [1] Consultar incidencias asignadas no resueltas
                   ----------------------------------------------------------
                   [2] Marcar incidencia como resuelta
                   ----------------------------------------------------------
                   [3] Consultar mis incidencias cerradas
                   ----------------------------------------------------------
                   [4] Mostrar mi perfil
                   ----------------------------------------------------------
                   [5] Cambiar clave de acceso
                   ----------------------------------------------------------
                   [6] Cerrar sesión
                   ----------------------------------------------------------
                                       
                ╚═══════════════════════════════════════════════════════════════════════════╝
                                    """


        );
    }
    public static void menuDeAdmin(String nombreAdmin, int incidenciasAbbiertas, int noAsignadasaNingunTecnico){
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════╗\n"+
                             "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒    MENU DE ADMINISTRADOR    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n" +
                "\t\tBienvenido usuario "+nombreAdmin+"\n"+
                "Actualmente, hay "+incidenciasAbbiertas+" incidencias abiertas, de las cuales "+noAsignadasaNingunTecnico+" están asignadas\n"+
                " \n\n" + """
                   
                   ----------------------------------------------------------
                   [1] Consultar todas las incidencias abiertas
                   ----------------------------------------------------------
                   [2] Consultar todas las incidencias cerradas
                   ----------------------------------------------------------
                   [3] Consultar incidencias por término
                   ----------------------------------------------------------
                   [4] Consultar los tecnicos
                   ----------------------------------------------------------
                   [5] Asignar incidencia a un tecnico
                   ----------------------------------------------------------
                   [6] Dar de alta a un tecnico
                   ----------------------------------------------------------
                   [7] Borrar un tecnico
                   ----------------------------------------------------------
                   [8] Concultar los usuarios
                   ----------------------------------------------------------
                   [9] Estadísticas de la aplicación
                   ----------------------------------------------------------
                   [10] Mostrar configuración
                   ----------------------------------------------------------
                   [11] Enviar incidencias abiertas a técnicos por correo (EXCEL)
                   ----------------------------------------------------------
                   [12] Cerrar sesión
                   ----------------------------------------------------------
                                       
                ╚═══════════════════════════════════════════════════════════════════════════╝
                                    """


        );
    }

}
