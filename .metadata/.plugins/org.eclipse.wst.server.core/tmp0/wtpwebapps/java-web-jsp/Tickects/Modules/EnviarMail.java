package Tickects.Modules;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EnviarMail {

    public static Boolean enviarMail(String destino, String mensaje, String asunto) {
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


        try {
            //msg
            Message message = new MimeMessage(session);
            //establecer emisor
            message.setFrom(new InternetAddress(emisor));
            //establecer receptor
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            //asunto
            message.setSubject(asunto);
            //+contenido
            message.setContent(mensaje, "text/html; charset=utf-8");
            //try send
            Transport.send(message);
            resultado = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }



    }

