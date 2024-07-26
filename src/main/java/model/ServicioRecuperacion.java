package model;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class ServicioRecuperacion {

    private final String username = "pawcloudsoporte@gmail.com"; // Reemplaza con tu dirección de correo de Gmail
    private final String password = "aipl xhpo rxsq nvxp"; // Usa la contraseña de aplicación generada en tu cuenta de Google

    public void enviarTokenRecuperacion(String correoDestino, String token) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("PawCloudSoporte@gmail.com")); // Tu correo
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino) // Correo del destinatario
            );
            message.setSubject("Recuperación de Contraseña de PawCloud");
            message.setText("Hola,\n\nHas solicitado restablecer tu contraseña en PawCloud."
                            + "\n\nTu código de recuperación es: " + token
                            + "\n\nPor favor, ingresa este código en la página de restablecimiento de contraseña para continuar."
                            + "\n\nSi no has solicitado restablecer tu contraseña, por favor ignora este correo o ponte en contacto con nosotros."
                            + "\n\nGracias,\nEl Equipo de PawCloud");


            Transport.send(message);

            System.out.println("Correo enviado exitosamente");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
