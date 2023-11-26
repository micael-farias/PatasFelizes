package main.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class EmailSenderThread extends Thread {

    private String to;
    private String subject;
    private String body;
    private File attachmentFile;

    public EmailSenderThread(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachmentFile = attachmentFile;
    }
    
    public void setFile(File file){
        attachmentFile = file;
    }
    

    @Override
    public void run() {
        // Configurações do servidor de email
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", true);

        // Configurações do usuário e senha do email
        String username = "patasfelizes750@gmail.com";
        String password = "htytvlcvipagqkql";

        // Configuração da sessão
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Criando a mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Parte de texto (corpo do email)
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            // Parte anexada (arquivo)
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentFile);

            // Montando a mensagem
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Definindo a mensagem como multipart
            message.setContent(multipart);

            // Enviando o email
            Transport.send(message);

            System.out.println("Email enviado para: " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
