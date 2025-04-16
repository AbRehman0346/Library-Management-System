package Settings;

import LMS.Login;
import LMS.MainForm;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdul Rehman Lashari
 */
public class Deciding_First_Page_To_Open {
    static PreparedStatement ps;
    public static void main(String arug[]){
        try {
            MainForm.connection();
            ps = MainForm.con.prepareStatement("SELECT status FROM password WHERE id = 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getString("status").equals("Current"))
                new Login().setVisible(true);
            else
                new Deciding_First_Page_To_Open().chooseFile();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            new MainForm().setVisible(true);
        }    
    }
    public void chooseFile(){
        try{
            MainForm.connection();
            ps = MainForm.con.prepareStatement("SELECT academy as a FROM lms.academy WHERE id = 2");
            ResultSet rs = ps.executeQuery(); 
            rs.next();
            
            switch (rs.getString("a")){
                case "3" -> {new LMS.All_Information().setVisible(true);}
                default -> {new LMS.MainForm().setVisible(true);}
            }
        } catch (SQLException | ClassNotFoundException ex) {
            new LMS.MainForm().setVisible(true);
//            Logger.getLogger(Deciding_First_Page_To_Open.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}