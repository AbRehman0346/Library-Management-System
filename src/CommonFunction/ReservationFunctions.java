/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonFunction;

import static LMS.MainForm.connection;
import static LMS.MainForm.con;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Abdul Rehman
 */
public class ReservationFunctions {

    PreparedStatement ps;

    public String getReservationOf(String bookId) throws SQLException, ClassNotFoundException {
        connection();
        ps = con.prepareStatement("SELECT STUDENT_ID AS ID FROM RESERVE_BOOK WHERE BOOK_ID = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("ID");
        } else {
            return null;
        }
    }

    public String getFullReservationOf(String bookId) throws SQLException, ClassNotFoundException {
        connection();
        ps = con.prepareStatement("SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME, ' (', STUDENT_ID, ')') AS RESERVATION FROM RESERVe_BOOK\n"
                + "JOIN student_data ON student_id = id WHERE BOOK_ID = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("RESERVATION");
        } else {
            return null;
        }
    }

    public boolean isReserved(String bookId) throws SQLException, ClassNotFoundException {
        connection();
        ps = con.prepareStatement("SELECT STUDENT_ID AS ID FROM RESERVE_BOOK WHERE BOOK_ID = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public boolean setReserved(String bookId) throws SQLException, ClassNotFoundException {
        connection();
        ps = con.prepareStatement("SELECT STUDENT_ID AS ID FROM RESERVE_BOOK WHERE BOOK_ID = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
