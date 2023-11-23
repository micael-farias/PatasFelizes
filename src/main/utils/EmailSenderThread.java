package main.utils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderThread extends Thread {

    private String to;
    private String subject;
    private String body;

    public EmailSenderThread(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
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
            message.setText(body);

            // Enviando o email
            Transport.send(message);

            System.out.println("Email enviado para: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso da classe EmailSenderThread
        String destinatario = "destinatario@example.com";
        String assunto = "Assunto do Email";
        String corpo = "Corpo do Email";

        // Criando e iniciando a thread para enviar o email
        EmailSenderThread emailSenderThread = new EmailSenderThread(destinatario, assunto, corpo);
        emailSenderThread.start();
    }
}
