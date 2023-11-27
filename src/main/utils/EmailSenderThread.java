package main.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import javafx.application.Platform;
import main.App;
import static main.db.ImportData.excluirArquivoFalhas;
import main.enums.MensagemTipo;
import main.interfaces.Acao;

public class EmailSenderThread extends Thread {

    private String to;
    private String subject;
    private String body;
    private File attachmentFile;
    private Acao acao;

    public EmailSenderThread(String to, String subject, String body, Acao acao) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.acao = acao;
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
        String[] dados = LeitorSecrets.lerSecrets();
        String username = dados[0];
        String password = dados[1];

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
            textPart.setText(body, "utf-8", "html");
            
            Multipart multipart = new MimeMultipart();

            // Parte anexada (arquivo)
            if(attachmentFile != null){
               MimeBodyPart attachmentPart = new MimeBodyPart();
               attachmentPart.attachFile(attachmentFile);
               multipart.addBodyPart(attachmentPart);      
            }

            // Montando a mensagem
            multipart.addBodyPart(textPart);

            // Definindo a mensagem como multipart
            message.setContent(multipart);

            // Enviando o email
            Transport.send(message);  

            System.out.println("Email enviado para: " + to);

            Platform.runLater(
              () -> {
                     App.getInstance().SetMensagem(MensagemTipo.SUCESSO, "Email enviado com sucesso");
              }
            );
        } catch (Exception e) {
            
            if(!to.equals(username)){
                Platform.runLater(
                  () -> {
                        App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao enviar email");              }
                 );
            }
          
            e.printStackTrace();
        }
        if(acao != null)
            acao.RealizarAcao(null);
    }
}
