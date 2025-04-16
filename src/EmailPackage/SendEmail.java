/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailPackage;

import com.sun.mail.util.MailConnectException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Abdul Rehman
 */
public class SendEmail {
    boolean sendEmail(final String sender, final String Password, String recepient, String subject, String msg) 
            throws MessagingException, MailConnectException, NullPointerException{
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender, Password);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(subject);
        message.setContent(msg, "text/html");
        
        Transport.send(message);
        return true;
    }
    
    boolean sendEmail(final String sender, final String Password, String[] recepient, String subject, String msg, boolean isTextProvided) 
            throws MessagingException, MailConnectException, NullPointerException{
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender, Password);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        for (String r : recepient)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(r));
        message.setSubject(subject);
        if (isTextProvided)
            message.setText(msg);
        else
            message.setContent(msg, "text/html");
        
        Transport.send(message);
        return true;
    }
}
