/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import static LMS.MainForm.connection;
import static LMS.MainForm.con;
import java.sql.PreparedStatement;

/**
 *
 * @author Abdul Rehman
 */
public class RPTE {
    private PreparedStatement ps;
    private String recoveryCode = null;
    
    public boolean sendCode(){
        StringBuilder msg = new StringBuilder();
        AutomaticEmail ae = new AutomaticEmail();
        String subject = "Recover Library Management System's Password.";
        
        //Preparing Message
        msg.append("<h1>Welcome to LMS Recovery System</h1><br>");
        msg.append("<h2>Your Recovery Code is ");
        msg.append(generateRandomCode());
        msg.append("</h2>");
        
        return ae.sendPasswordRecoveryCode(msg.toString(), subject);
    }
    
    public String getCode(){
        return recoveryCode;
    }
    
    public boolean setRecoveryEmail(String recoveryEmail) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE EMAIL_SETTINGS SET RECOVERYEMAIL = '"+recoveryEmail+"' WHERE ID = 1");
        return !ps.execute();
    }
    
    public String getRecoveryEmail() throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("SELECT recoveryEmail AS re FROM EMAIL_SETTINGS WHERE ID = 1");
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getString("re");
        else
            return null;
    }
    
    public boolean sendTestEmail(){
        StringBuilder msg = new StringBuilder();
        AutomaticEmail ae = new AutomaticEmail();
        String subject = "Recover Library Management System's Password.";
        
        //Preparing Message
        msg.append("<h1>Welcom to LMS Recovery Systetm</h1><br>");
        msg.append("<h2>Your Recovery Code is G-");
        msg.append(generateRandomCode());
        msg.append("</h2> <br><br>");
        msg.append("<h3>Note: This is just a Test Email...</h3>");
        
        return ae.sendPasswordRecoveryCode(msg.toString(), subject);
    }
    
    public boolean matchCode(String code){
        return code.equals(recoveryCode);
    }
    
//    Private Functions...
    private String generateRandomCode(){
        recoveryCode = String.valueOf(Math.random()*100);
        recoveryCode = recoveryCode.substring(recoveryCode.length()-8, recoveryCode.length());
        return recoveryCode;
    }
}
