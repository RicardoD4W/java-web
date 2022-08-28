package Tickects.Modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Lectura_ficheros {

    public static String leerFichero(String nombreFichero) {

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
                 mensaje += texto;

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
}
