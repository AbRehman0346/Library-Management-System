/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailPackage;

import com.sun.mail.util.MailConnectException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Test {
    public void test(){
        //this line removes the html code from string
//        System.out.println(msg.replaceAll("\\<.*?\\>", ""));
        try {
            System.out.println("Preparing to send email");
            String receivers[] = {"abrehman0346@gmail.com", "abdulrehmanlashari1995@gmail.com"};
            sendEmail("librarymanagementsystem0346@gmail.com", "03468942392", receivers, "Test", "Test Email", true);
            System.out.println("Email Sent");
        } catch (MessagingException | NullPointerException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
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
        
        for (String s : recepient)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(s));
        
        
        message.setSubject(subject);
        if (isTextProvided)
            message.setText(msg);
        else
            message.setContent(msg, "text/html");
        
        Transport.send(message);
        return true;
    }
    
    public String[] extractString(String a){
        int count = 0;
        for (int i=0; i<a.length(); i++)
            if (a.charAt(i) == 44)
                count++;
        int index = 0;
        String[] sb = new String[++count];
        
        for (int i=0; i<sb.length; i++)
            sb[i] = "";
        
        for (int i=0; i<a.length(); i++){
            if (a.charAt(i) != 44){
                sb[index] += a.charAt(i);
            }
            else{
                index++;
            }
        }
        
        for (int i=0; i<sb.length; i++)
            sb[i] = sb[i].trim();
        
        return sb;
    }
    
    public static void main(String[] args){
        new Test().test();
    }
    
//    One More Return Button on Student... (DONE)
//    Solved Table Providing wrong Data... (DONE)
//    Changed Picture Save Dialog (JFileChooser to FileDialog) (DONE)
//    New Button on Statustics to show which books need to return today.
}
