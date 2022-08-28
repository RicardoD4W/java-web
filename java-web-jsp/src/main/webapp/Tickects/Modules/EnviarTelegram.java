package Tickects.Modules;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class EnviarTelegram {

    public static boolean enviaMensajeTelegram(String mensaje) {
        String direccion; //url de l api
        String fijo = "https://api.telegram.org/bot5161817842:AAG3luwMdBBlm3gjwjO0B7vtn3-DMA7ffzg/sendMessage?chat_id=709296353&text=";
        direccion = fijo + mensaje;
        URL url;
        boolean dev = false;

        try {
            url = new URL(direccion); //obj con url del bot
            URLConnection con = url.openConnection(); //peticion get
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev = true; //flag exito
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return dev;
    }
}
