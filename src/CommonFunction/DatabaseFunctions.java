/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonFunction;

import static LMS.MainForm.con;
import static LMS.MainForm.connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

/**
 *
 * @author Abdul Rehman
 */
public class DatabaseFunctions {
    PreparedStatement ps;
    public String getGender(String id) {
        String mr = "Mr ", miss = "Miss ", both = "Mr/Miss ";
        try {
            connection();
            ps = con.prepareStatement("SELECT sex FROM STUDENT_DATA WHERE ID = '"+id+"'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                if (rs.getString("sex").toLowerCase().equals("male"))
                    return mr;
                else if (rs.getString("sex").toLowerCase().equals("female"))
                    return miss;
                else
                    return both;
            }else
                return both;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return both;
    }
    
    public String getPronoun(String gender){
        switch(gender){
            case("Mr ")->{return "He";}
            case("Miss ") -> {return "She";}
            case("Mr/Miss") -> {return "He/She";}
            default -> {return "";}
        }
    }
    
//    public static void main(String[] args){
//        DatabaseFunctions d = new DatabaseFunctions();
//        System.out.println(d.getPronoun(d.getGender("005")));
//    }
}
