package ml.georgedi23;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {

    private final String targetEmail;
    private final String sourceEmail;
    private final String sourceEmailCredentials;
    private final TransportWrapper transportWrapper;

    public EmailUtility(String targetEmail, String sourceEmail,
                        String sourceEmailCredentials, TransportWrapper transportWrapper){
        this.targetEmail = targetEmail;
        this.sourceEmail = sourceEmail;
        this.sourceEmailCredentials = sourceEmailCredentials;
        this.transportWrapper = transportWrapper;
    }

    // For IPUtility purposes, subject should be "IP Address at " + current datetime
    public String sendEmail(String subject, String bodyText){
        try{
            Map<String, String> emailSetup = new HashMap<>();
            emailSetup.put("subject", subject);
            emailSetup.put("bodyText", bodyText);
            emailSetup.put("sourceEmail", this.sourceEmail);
            emailSetup.put("targetEmail", this.targetEmail);

            Session session = getGmailSession(this.sourceEmail, this.sourceEmailCredentials);
            MimeMessage message = createMimeMessage(emailSetup, session);
            this.transportWrapper.send(message);
            return "Transmission Successful";
        } catch (Exception e){
            return "Transmission failure: " + e.toString();
        }
    }

    public MimeMessage createMimeMessage(Map<String, String> emailSetup, Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailSetup.get("sourceEmail")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailSetup.get("targetEmail")));
        message.setSubject(emailSetup.get("subject"));
        message.setText(emailSetup.get("bodyText"));
        return message;
    }

    public Session getGmailSession(String sourceEmail, String sourceEmailCredentials){
        // Update for expanded compatibility
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(
                        sourceEmail, sourceEmailCredentials);
            }
        });
        //session.setDebug(true); <-- Debug if needed
        return session;
    }
}