/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fine;
import static LMS.MainForm.con;
import static LMS.MainForm.connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
/**
 *
 * @author Abdul Rehman
 */
public class FineFunctions {
    PreparedStatement ps;
    protected boolean payFine(String fineId) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE FINE SET `STATUS` = 'Paid', Pay_date = CURRENT_DATE() WHERE id = '"+fineId+"'");
        return !ps.execute();
    }
    
    protected boolean pardonFine(String fineId) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE FINE SET `STATUS` = 'Pardoned', Pay_date = CURRENT_DATE() WHERE id = '"+fineId+"'");
        return !ps.execute();
    }
    
    protected boolean payFine(String fineId, String status) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE FINE SET `STATUS` = '"+status+"', Pay_date = CURRENT_DATE() WHERE id = '"+fineId+"'");
        return !ps.execute();
    }
    
    protected boolean payLateFine(String fineId, String amount) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE FINE SET `STATUS` = 'Paid', Pay_date = CURRENT_DATE(), "
                + "LATE_AMOUNT = "+amount+", LATE_AMOUNT_STATUS = 'Paid'"
                + "WHERE id = '"+fineId+"'");
        return !ps.execute();
    }
    
    protected boolean pardonLateFine(String fineId, String amount) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("UPDATE FINE SET `STATUS` = 'Paid', Pay_date = CURRENT_DATE(), "
                + "LATE_AMOUNT = "+amount+", LATE_AMOUNT_STATUS = 'Pardoned'"
                + "WHERE id = '"+fineId+"'");
        return !ps.execute();
    }
    
    protected boolean isFinePaid(String fineId){
        try {
            connection();
            ps = con.prepareStatement("SELECT `STATUS` AS S FROM FINE WHERE id = '"+fineId+"'");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return (rs.getString("S").toLowerCase().equals("paid"));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FineFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
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
            Logger.getLogger(FineFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return both;
    }
    
    public int sumOfRemaningFine(String studentId){
        try {
            connection();
            ps = con.prepareStatement("SELECT SUM(FINE) AS F FROM LMS.FINE\n" +
                    "WHERE student_id = '"+studentId+"' AND \n" +
                    "`status` = 'Unpaid'");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("f");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FineFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public boolean doesFineRemain(String studentId){
        try {
            connection();
            ps = con.prepareStatement("SELECT student_id AS F FROM LMS.FINE\n" +
                    "WHERE student_id = '"+studentId+"' AND \n" +
                    "`status` = 'Unpaid'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FineFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int calculateLateReturnedBookDays(String studentId, String bookId) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("SELECT DATEDIFF(CURRENT_DATE(), RETURN_DATE) AS DIFF FROM ISSUE_BOOK\n" +
            "WHERE CURRENT_DATE() > RETURN_DATE AND BOOK_ID = '0016' AND STUDENT_ID = '001'");
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt("DIFF");
        return 0;
    }
    
    public int calculateLateReturnedBookFine(String studentId, String bookId) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("SELECT `VALUE` FROM GENERAL_SETTINGS WHERE ID = 2");
        ResultSet rs2 = ps.executeQuery();
        int finePerDay = rs2.getInt("value");
        
        ps = con.prepareStatement("SELECT DATEDIFF(CURRENT_DATE(), RETURN_DATE) AS DIFF FROM ISSUE_BOOK\n" +
            "WHERE CURRENT_DATE() > RETURN_DATE AND BOOK_ID = '0016' AND STUDENT_ID = '001'");
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt("DIFF") * finePerDay;
        return 0;
    }
    
    public boolean isFinedForLateReturn(String studentId, String bookId) throws SQLException, ClassNotFoundException{
        connection();
        
        ps = con.prepareStatement("SELECT `SET` FROM GENERAL_SETTINGS WHERE ID = 2");
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if (rs.getString("SET").equals("ON")){
                ps = con.prepareStatement("SELECT * FROM ISSUE_BOOK\n" +
                        "WHERE CURRENT_DATE() > RETURN_DATE AND BOOK_ID = '"+bookId+"' AND STUDENT_ID = '"+studentId+"'");
                ResultSet rs2 = ps.executeQuery();
                return rs2.next();
            }
        }
        return false;
    }
    
    public boolean isLateFineApply(String fineId) throws SQLException, ClassNotFoundException{
        connection();
        ps = con.prepareStatement("SELECT `SET` FROM GENERAL_SETTINGS WHERE ID = 3");
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if (rs.getString("set").equals("ON")){
                ps = con.prepareStatement("SELECT * FROM FINE\n" +
                        "WHERE CURRENT_DATE() > `LAST_DATE` AND `STATUS` = 'Unpaid'\n" +
                        "AND ID = '"+fineId+"'");
                ResultSet rs2 = ps.executeQuery();
                return rs2.next();
            }
        }
        return false;
    }
    
//    public static void main(String[] args){
//        System.out.println(new FineFunctions().doesFineRemain("010"));
//    }
}
