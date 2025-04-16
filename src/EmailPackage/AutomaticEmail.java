/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailPackage;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Abdul_Rehmancom.company.EncryptPassword;
import static LMS.MainForm.con;
import static LMS.MainForm.connection;
import com.sun.mail.util.MailConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdul Rehman
 */
public class AutomaticEmail {
    PreparedStatement ps;
    public boolean issueBookEmail(String recepient, String books) throws SQLException, ClassNotFoundException, MessagingException {
        EncryptPassword ep = new EncryptPassword();
        StringBuilder msg = new StringBuilder("");
        String subject = "";
        String sender = "";
        String password = "";
            connection();
            ps = con.prepareStatement("SELECT ID, email, password, email1 as e1, sub1 as s1 FROM email_settings");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("ID").equals("2")){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
                }
                else if (rs.getString("ID").equals("3")){
                    subject = rs.getString("s1");
                    msg.append("<h1>Book Issued From IBACCNF Library<br></h1>");
                    msg.append(rs.getString("e1"));
                }
            }

            int index = msg.indexOf(";");
            msg.deleteCharAt(index);
            msg.insert(index, books);


            return new SendEmail().sendEmail(sender, password, recepient, subject, msg.toString());
        
        
    }
    
    public boolean sendCustomEmail(String recepient, String subject, String msg) {
        EncryptPassword ep = new EncryptPassword();
        String sender = "";
        String password = "";
        String[] recepients = seperateCombinedEmails(recepient);
        try{
            connection();
            ps = con.prepareStatement("SELECT email, password FROM email_settings WHERE ID = 2");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                sender = ep.Encrypt(rs.getString("email"));
                password = ep.Encrypt(rs.getString("password"));
            }
            
            new SendEmail().sendEmail(sender, password, recepients, subject, msg, true);
            
            
            for (String i : recepients){
                setEmailHistory(i, (subject+"\n"+msg), "Custom Email", "SENT");
            }
            return true;
        } catch(AddressException e){
            try {
                for (String i : recepients)
                    setEmailHistory(i, (subject+"\n"+msg), "Custom Email", "Email Address Not Available.");
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        catch(NullPointerException e){
            try {
                for (String i : recepients)
                    setEmailHistory(i, (subject+"\n"+msg), "Custom Email", "Email Address Not Available.");
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MailConnectException e){
            try {
                for (String i : recepients)
                    setEmailHistory(i, (subject+"\n"+msg), "Custom Email", "Internet Not Connected.");
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (AuthenticationFailedException e){
            try {
                for (String i : recepients)
                    setEmailHistory(i, (subject+"\n"+msg), "Custom Email", "Could Not Authenticate.");
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MessagingException ex) {
            try {
                for (String i : recepients)
                    setEmailHistory(i, (subject+"\n"+msg), "Custom Eamil", ex.toString());
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException e) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
            }
        }catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Failed to Save Settings", "Emails", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean issueBookEmail(String studentId, String recepient, String books, boolean exceptionsHandled) {
        EncryptPassword ep = new EncryptPassword();
        StringBuilder msg = new StringBuilder("");
        String subject = "";
        String sender = "";
        String password = "";
        try{
            connection();
            ps = con.prepareStatement("SELECT ID, email, password, email1 as e1, sub1 as s1 FROM email_settings");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("ID").equals("2")){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
                }
                else if (rs.getString("ID").equals("3")){
                    subject = rs.getString("s1");
                    msg.append("<h1>Book Issued From IBACCNF Library<br></h1>");
                    msg.append(rs.getString("e1"));
                }
            }

            int index = msg.indexOf(";");
            msg.deleteCharAt(index);
            msg.insert(index, books);


            new SendEmail().sendEmail(sender, password, recepient, subject, msg.toString());
            
            return setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", "SENT");
        } catch(AddressException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", "Email Address Not Available.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        catch(NullPointerException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", "Email Address Not Available.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MailConnectException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", "Internet Not Connected.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (AuthenticationFailedException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", "Could Not Authenticate.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MessagingException ex) {
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Issued Book.", ex.toString());
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException e) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
            }
        }catch (SQLException | ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "Failed to Save Settings", "Emails", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean returnBookEmail(String studentId, String recepient, String books, boolean exceptionHandled){
        EncryptPassword ep = new EncryptPassword();
        StringBuilder msg = new StringBuilder("");
        String subject = "";
        String sender = "";
        String password = "";
        try{
            connection();
            ps = con.prepareStatement("SELECT ID, email, password, email2 as e2, sub2 as s2 FROM email_settings");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("ID").equals("2")){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
                }
                else if (rs.getString("ID").equals("3")){
                    subject = rs.getString("s2");
                    msg.append("<h1>Book Returned at IBACCNF Library<br></h1>");
                    msg.append(rs.getString("e2"));
                }
            }

            int index = msg.indexOf(";");
            msg.deleteCharAt(index);
            msg.insert(index, books);


            new SendEmail().sendEmail(sender, password, recepient, subject, msg.toString());
            
            return setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", "SENT");
        } catch(AddressException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", "Email Address Not Available.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        catch(NullPointerException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", "Email Address Not Available.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MailConnectException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", "Internet Not Connected.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (AuthenticationFailedException e){
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", "Could Not Authenticate.");
                //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MessagingException ex) {
            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
            try {
                setEmailHistory(studentId, ep.Encrypt(recepient), msg.toString(), "Return Book.", ex.toString());
//                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException e) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
            }
        }catch (SQLException | ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "Failed to Save Settings", "Emails", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void sendEmailAtReturnDate(){
        new Thread(()->{
            try {
            connection();
            ps = con.prepareStatement("SELECT AUTOEMAIL3 AS A3 FROM email_settings WHERE ID = 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                if (rs.getString("A3").equals("ON"))
                    new EmailPackage.AutomaticEmail().emailAtReturnDate();
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
    //            Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    private void emailAtReturnDate(){
        EncryptPassword ep = new EncryptPassword();
        StringBuilder msg = new StringBuilder("");
        String realMsg = "";
        String subject = "";
        String sender = "";
        String password = "";
        String recepient = "";
        String studentId = "";
        try{
            connection();
            
            ps = con.prepareStatement("SELECT ID, email, password, email3 as e3, sub3 as s3 FROM email_settings");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("ID").equals("2")){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
                }
                else if (rs.getString("ID").equals("3")){
                    subject = rs.getString("s3");
                    msg.append(rs.getString("e3"));
                }
            }
            
            ps = con.prepareStatement("SELECT DISTINCT(student_id) as sid FROM LMS.ISSUE_BOOK WHERE RETURN_DATE = current_date() AND "
                    + "(EMAIL3 != 'SENT' OR EMAIL3 IS NULL) ");
            ResultSet rs2 = ps.executeQuery();
            while (rs2.next()){
                try{
                    studentId = rs2.getString("sid");
                    StringBuilder issuedBookData = new StringBuilder();
                    String studentName = "";
                    ps = con.prepareStatement("SELECT sd.first_name, sd.Last_Name, sd.sex, ib.book_id, b.title, b.Author, ib.issue_date, ib.return_date FROM ISSUE_BOOK ib \n" +
                        "JOIN student_data sd ON sd.id = ib.student_id\n" +
                        "JOIN book b ON b.id = ib.book_id\n" +
                        "WHERE (RETURN_DATE = current_date() AND (EMAIL3 != 'SENT' OR EMAIL3 IS NULL)) AND STUDENT_ID = " + studentId);
                    ResultSet rs4 = ps.executeQuery();
                    
                    while (rs4.next()){
                        studentName = (rs4.getString("sex").equals("Male") ? "Mr " : "Miss ") +rs4.getString("first_name") + " " + rs4.getString("last_name") + ",";
                        issuedBookData.append("<br>").append("<br>").append("Book Acc: ").append(rs4.getString("book_id")).append("<br>").append("Title: ").
                                append(rs4.getString("title")).append("<br>").append("Author: ").append(rs4.getString("author")).append("<br>").append("Issued Date: ").
                                append(rs4.getString("issue_date")).append("<br>").append("Return Date: ").append(rs4.getString("return_date"));
                    }
                    
                    //Getting Student Email.
                    PreparedStatement psStudent = con.prepareStatement("SELECT EMAIL FROM STUDENT_DATA WHERE ID = " + studentId);
                    ResultSet rsStudent = psStudent.executeQuery();
                    rsStudent.next();
                    recepient = rsStudent.getString("email");

                    realMsg = msg.toString().replaceAll("BOOK_DATA", issuedBookData.toString()).replaceAll("STUDENT_DATA", studentName);

                    new SendEmail().sendEmail(sender, password, recepient, subject, realMsg);

                    setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "SENT");

                    {//Updating the issue table from preventing repeated email...
                        String query = "UPDATE ISSUE_BOOK SET EMAIL3 = 'SENT' WHERE (RETURN_DATE = current_date() AND "
                                + "(EMAIL3 != 'SENT' OR EMAIL3 IS NULL)) AND STUDENT_ID = " + studentId;
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.execute();
                    }
                } catch(AddressException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Email Address Not Available.");
                        //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
                catch(NullPointerException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Email Address Not Available.");
                        //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (MailConnectException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Internet Not Connected.");
                        //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (AuthenticationFailedException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Could Not Authenticate.");
                        //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (MessagingException ex) {
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", ex.toString());
                        //JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException e) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Emails", JOptionPane.INFORMATION_MESSAGE);
//            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendEmailAfterReturnDate(){
        new Thread(()->{
            try {
            connection();
            ps = con.prepareStatement("SELECT AUTOEMAIL4 AS A4 FROM email_settings WHERE ID = 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                if (rs.getString("A4").equals("ON"))
                    emailAfterReturnDate();
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
//                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    private void emailAfterReturnDate(){
        EncryptPassword ep = new EncryptPassword();
        StringBuilder msg = new StringBuilder("");
        String realMsg = "";
        String subject = "";
        String sender = "";
        String password = "";
        String recepient = "";
        String studentId = "";
        try{
            connection();
            
            ps = con.prepareStatement("SELECT ID, email, password, email4 as e4, sub4 as s4 FROM email_settings");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("ID").equals("2")){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
                }
                else if (rs.getString("ID").equals("3")){
                    subject = rs.getString("s4");
                    msg.append(rs.getString("e4"));
                }
            }
            
            ps = con.prepareStatement("SELECT DISTINCT(student_id) as sid FROM LMS.ISSUE_BOOK WHERE RETURN_DATE < current_date() AND "
                    + "(EMAIL4 != 'SENT' OR EMAIL4 IS NULL) ");
            ResultSet rs2 = ps.executeQuery();
            while (rs2.next()){
                try{
                    studentId = rs2.getString("sid");
                    StringBuilder issuedBookData = new StringBuilder();
                    String studentName = "";
                    ps = con.prepareStatement("SELECT sd.first_name, sd.Last_Name, sd.sex, ib.book_id, b.title, b.Author, ib.issue_date, ib.return_date FROM ISSUE_BOOK ib \n" +
                        "JOIN student_data sd ON sd.id = ib.student_id\n" +
                        "JOIN book b ON b.id = ib.book_id\n" +
                        "WHERE (RETURN_DATE < current_date() AND (EMAIL4 != 'SENT' OR EMAIL4 IS NULL)) AND STUDENT_ID = " + studentId);
                    ResultSet rs4 = ps.executeQuery();
                    
                    while (rs4.next()){
                        studentName = (rs4.getString("sex").equals("Male") ? "Mr " : "Miss ") +rs4.getString("first_name") + " " + rs4.getString("last_name") + ",";
                        issuedBookData.append("<br>").append("<br>").append("Book Acc: ").append(rs4.getString("book_id")).append("<br>").append("Title: ").
                                append(rs4.getString("title")).append("<br>").append("Author: ").append(rs4.getString("author")).append("<br>").append("Issued Date: ").
                                append(rs4.getString("issue_date")).append("<br>").append("Return Date: ").append(rs4.getString("return_date"));
                    }
                    
                    //Getting Student Email.
                    PreparedStatement psStudent = con.prepareStatement("SELECT EMAIL FROM STUDENT_DATA WHERE ID = " + studentId);
                    ResultSet rsStudent = psStudent.executeQuery();
                    rsStudent.next();
                    recepient = rsStudent.getString("email");

                    realMsg = msg.toString().replaceAll("BOOK_DATA", issuedBookData.toString()).replaceAll("STUDENT_DATA", studentName);

                    
                    new SendEmail().sendEmail(sender, password, recepient, subject, realMsg);

                    setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", "SENT");

                    {//Updating the issue table from preventing repeated email...
                        String query = "UPDATE ISSUE_BOOK SET EMAIL4 = 'SENT' WHERE (RETURN_DATE < current_date() AND "
                                + "(EMAIL4 != 'SENT' OR EMAIL4 IS NULL)) AND STUDENT_ID = " + studentId;
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.execute();
                    }
                } catch(AddressException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", "Email Address Not Available.");
//                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
                catch(NullPointerException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", "Email Address Not Available.");
//                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (MailConnectException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", "Internet Not Connected.");
//                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (AuthenticationFailedException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", "Could Not Authenticate.");
//                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (MessagingException ex) {
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Books Not Returned On Date", ex.toString());
//                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException e) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Emails", JOptionPane.INFORMATION_MESSAGE);
//            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean setEmailHistory(String memberId, String receiver, String msg, String purpose, String status) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("SELECT EMAIL FROM EMAIL_SETTINGS WHERE ID = 2");
        ResultSet rs = ps.executeQuery();
        rs.next();
        ps = con.prepareStatement("INSERT INTO EMAIL(Sender, member_ID, Receiver, email, `STATUS`, purpose, `date`, `time`)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE, current_time())");
        ps.setString(1, rs.getString("email"));
        ps.setString(2, memberId);
        ps.setString(3, receiver);
        ps.setString(4, msg);
        ps.setString(5, status);
        ps.setString(6, purpose);
        
        return !ps.execute();
    }
    
    public boolean setEmailHistory(String receiver, String msg, String purpose, String status) throws SQLException, ClassNotFoundException{
        EncryptPassword ep = new EncryptPassword();
        StringBuilder ids = new StringBuilder("");
        connection();
        ps = con.prepareStatement("SELECT EMAIL FROM EMAIL_SETTINGS WHERE ID = 2");
        ResultSet rs = ps.executeQuery();
        rs.next();
        
        PreparedStatement ps2 = con.prepareStatement("SELECT id FROM student_data WHERE email = '"+receiver+"'");
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()){
            ids.append(rs2.getString("id")).append(", ");
        }
        ids.deleteCharAt(ids.length()-2);
        
        ps = con.prepareStatement("INSERT INTO EMAIL(Sender, member_ID, Receiver, email, `STATUS`, purpose, `date`, `time`)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE, current_time())");
        ps.setString(1, rs.getString("email"));
        ps.setString(2, ids.toString().trim());
        ps.setString(3, ep.Encrypt(receiver));
        ps.setString(4, msg);
        ps.setString(5, status);
        ps.setString(6, purpose);
        
        return !ps.execute();
    }
    
    public boolean setEmailHistory(String senderEmail, String recepient, String msg, String purpose, String status, boolean withoutMemberId) throws SQLException, ClassNotFoundException{
//        Sender receiver, email(msg) status purpose
        EncryptPassword ep = new EncryptPassword();
        connection();
        
        ps = con.prepareStatement("INSERT INTO EMAIL(Sender, Receiver, email, `STATUS`, purpose, `date`, `time`)\n" +
                "VALUES (?, ?, ?, ?, ?, CURRENT_DATE, current_time())");
        ps.setString(1, ep.Encrypt(senderEmail));
        ps.setString(2, ep.Encrypt(recepient));
        ps.setString(3, msg);
        ps.setString(4, status);
        ps.setString(5, purpose);
        
        return !ps.execute();
    }
    
    public String[] seperateCombinedEmails(String a){
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
    
    public boolean sendAccountInfomation(String studentId){
        EncryptPassword ep = new EncryptPassword();
        String realMsg = "";
        String subject = "Library Account Information From (IBACCNF)";
        String sender = "";
        String password = "";
        String recepient = "";
        try{
            connection();
            
            ps = con.prepareStatement("SELECT ID, email, password FROM email_settings WHERE ID = 2");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                    sender = ep.Encrypt(rs.getString("email"));
                    password = ep.Encrypt(rs.getString("password"));
            }
            
            ps = con.prepareStatement("SELECT * FROM STUDENT_DATA WHERE ID = " + studentId);
            ResultSet rs2 = ps.executeQuery();
            if (rs2.next()){
                try{
                    StringBuilder studentData = new StringBuilder();
                    studentData.append("<h1>Member Data</h1><br>");
                    studentData.append("<h2>ID: ").append(rs2.getString("id")).append("<br>");
                    studentData.append("First Name: ").append(rs2.getString("first_name")).append("<br>");
                    studentData.append("Last Name: ").append(rs2.getString("last_name")).append("<br>");
                    studentData.append("Father's Name: ").append(rs2.getString("father_name")).append("<br>");
                    studentData.append("Gender: ").append(rs2.getString("sex")).append("<br>");
                    studentData.append("Class: ").append(rs2.getString("group")).append("<br>");
                    studentData.append("Phone: ").append(rs2.getString("phone")).append("<br>");
                    studentData.append("Email: ").append(rs2.getString("email")).append("<br>");
                    studentData.append("Address: ").append(rs2.getString("address")).append("</h2><br><br><br>");
                    studentData.append("<h1>Issued Books</h1>");
                    recepient = rs2.getString("email");
                    
                    StringBuilder issuedBookData = new StringBuilder();
                    ps = con.prepareStatement("SELECT sd.first_name, sd.Last_Name, sd.sex, ib.book_id, b.title, b.Author, ib.issue_date, ib.return_date FROM ISSUE_BOOK ib \n" +
                        "JOIN student_data sd ON sd.id = ib.student_id\n" +
                        "JOIN book b ON b.id = ib.book_id\n" +
                        "WHERE student_id = " + studentId);
                    ResultSet rs4 = ps.executeQuery();
                    
                    while (rs4.next()){
                        issuedBookData.append("<h2><br>").append("<br>").append("Book Acc: ").append(rs4.getString("book_id")).append("<br>").append("Title: ").
                                append(rs4.getString("title")).append("<br>").append("Author: ").append(rs4.getString("author")).append("<br>").append("Issued Date: ").
                                append(rs4.getString("issue_date")).append("<br>").append("Return Date: ").append(rs4.getString("return_date")).append("</h2>");
                    }
                    
                    studentData.append(issuedBookData);
                    realMsg = studentData.toString();
                    
                    new SendEmail().sendEmail(sender, password, recepient, subject, realMsg);

                    setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Account Information.", "SENT");

                    return true;
                } catch(AddressException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Email Address Not Available.");
                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
                catch(NullPointerException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Email Address Not Available.");
                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (NullPointerException ex){JOptionPane.showMessageDialog(null, "Email Address Not Found!", "Address Not Found", JOptionPane.ERROR_MESSAGE);}
                }
                catch (MailConnectException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Internet Not Connected.");
                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (AuthenticationFailedException e){
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", "Could Not Authenticate.");
                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch (MessagingException ex) {
                    try {
                        setEmailHistory(studentId, ep.Encrypt(recepient), realMsg, "Issued Book's Last Date Reminder", ex.toString());
                        JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", "Email", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | ClassNotFoundException e) {
                        Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Emails", JOptionPane.INFORMATION_MESSAGE);
//            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public boolean sendPasswordRecoveryCode(String msg, String subject) {
        EncryptPassword ep = new EncryptPassword();
        String senderEmail = "librarymanagementsystem0346@gmail.com";
        String password = "03468942392";
        String recepient = "";
        String purpose = "Password Recovery Code";
        try{
            connection();
            ps = con.prepareStatement("SELECT recoveryEmail AS re FROM email_settings WHERE id = 1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                recepient = rs.getString("re");
            }
            
            new SendEmail().sendEmail(senderEmail, password, recepient, subject, msg);
            
            return setEmailHistory(senderEmail, recepient, msg, purpose, "SENT", true);
        } catch(AddressException e){
            try {
                setEmailHistory(senderEmail, recepient, msg, purpose, "Email Address Not Available.", true);
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", purpose, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        catch(NullPointerException e){
            try {
                setEmailHistory(senderEmail, recepient, msg, purpose, "Email Address Not Available.", true);
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", purpose, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MailConnectException e){
            try {
                setEmailHistory(senderEmail, recepient, msg, purpose, "Internet Not Connected.", true);
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", purpose, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (AuthenticationFailedException e){
            try {
                setEmailHistory(senderEmail, recepient, msg, purpose, "Could Not Authenticate.", true);
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", purpose, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (MessagingException ex) {
            try {
                setEmailHistory(senderEmail, recepient, msg, purpose, ex.toString(), true);
                JOptionPane.showMessageDialog(null, "Failed!\nNo Internet Connection.", purpose, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | ClassNotFoundException e) {
                Logger.getLogger(AutomaticEmail.class.getName()).log(Level.SEVERE, null, e);
            }
        }catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Failed to Save Settings", purpose, JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
