/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LMS;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static LMS.MainForm.connection;
import static LMS.MainForm.con;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Abdul Rehman Lashari
 */
public class ReturnBooks extends javax.swing.JFrame {

    DefaultTableModel tm;
    PreparedStatement ps;

    /**
     * Creates new form ReturnBooks
     */
    public ReturnBooks() {
        initComponents();
        setIssueTableData();
    }

    private void setIssueTableData() {
        tm = (DefaultTableModel) tblReturnBookList.getModel();
        tm.setRowCount(0);
        try {
            connection();
            ps = con.prepareStatement("SELECT ib.student_id, sd.first_name, sd.last_name, sd.`group`, ib.book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM lms.issue_book ib\n"
                    + "JOIN student_data sd ON sd.id = ib.student_id\n"
                    + "JOIN LMS.BOOK b ON b.id = ib.book_id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(changeNullString(rs.getString("student_id")));
                v.add(changeNullString(rs.getString("first_name") + " " + rs.getString("last_name")));
                v.add(changeNullString(rs.getString("group")));
                v.add(changeNullString(rs.getString("book_id")));
                v.add(changeNullString(rs.getString("title")));
                v.add(changeNullString(rs.getString("author")));
                v.add(changeNullString(rs.getString("issue_date")));
                v.add(changeNullString(rs.getString("return_date")));
                tm.addRow(v);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String changeNullString(String nullLine) {
        try {
            return (!nullLine.equals("null")) ? nullLine : "";
        } catch (NullPointerException e) {
            return nullLine;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnReturned = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReturnBookList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Return Books");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Return Book", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(255, 0, 0))); // NOI18N

        btnReturned.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReturned.setText("Returned");
        btnReturned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnedActionPerformed(evt);
            }
        });

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.setText("Search");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        tblReturnBookList.setAutoCreateRowSorter(true);
        tblReturnBookList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Member ID", "Name", "Category", "Acc No", "Title", "Author", "Issue Date", "Return Date"
            }
        ));
        tblReturnBookList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblReturnBookList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnReturned))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 839, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReturned)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public String getBookStatus(String bookId) throws SQLException, ClassNotFoundException {
        connection();
        //Check if Someone has Reserved the Book...
        ps = con.prepareStatement("SELECT student_id FROM RESERVE_BOOK\n"
                + "WHERE book_id = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return "Reserved By '" + rs.getString("student_id") + "'";
        }
        return "Available";
    }

    public void createBookReturnHistory(String studentId, String bookId, String fineId) throws SQLException, ClassNotFoundException {
        //SAVING THE RETURNED BOOK ENTRY TO HISTORY DATABASE...
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        connection();
        System.out.println("Student Id: " + studentId);
        System.out.println("Book Id: " + bookId);
        ps = con.prepareStatement("SELECT sd.id as sid, sd.First_Name, sd.Last_Name, sd.`group`, b.id as bid, b.title, b.author, ib.issue_date, ib.return_date FROM issue_book ib\n"
                + "JOIN student_data sd ON sd.id = ib.student_id\n"
                + "JOIN book b ON b.id = ib.book_id\n"
                + "WHERE ib.student_id = '" + studentId + "' AND ib.book_id = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        rs.next();

        ps = con.prepareStatement("INSERT INTO HISTORY(Student_ID, Student_Name, category, Book_ID, Book_Name, author, Issue_Date, Last_Date, fine_id, Returned_Date)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        try {
            ps.setString(1, rs.getString("sid"));
        } catch (NullPointerException e) {
            ps.setString(1, "none");
        }
        try {
            ps.setString(2, rs.getString("first_name") + " " + rs.getString("last_name"));
        } catch (NullPointerException e) {
            ps.setString(2, "none");
        }
        try {
            ps.setString(3, rs.getString("group"));
        } catch (NullPointerException e) {
            ps.setString(3, "none");
        }
        try {
            ps.setString(4, rs.getString("bid"));
        } catch (NullPointerException e) {
            ps.setString(4, "none");
        }
        try {
            ps.setString(5, rs.getString("title"));
        } catch (NullPointerException e) {
            ps.setString(5, "none");
        }
        try {
            ps.setString(6, rs.getString("author"));
        } catch (NullPointerException e) {
            ps.setString(6, "none");
        }
        try {
            ps.setDate(7, rs.getDate("issue_Date"));
        } catch (NullPointerException | IllegalArgumentException e) {
            ps.setString(7, "0000-00-00");
        }
        try {
            ps.setDate(8, java.sql.Date.valueOf(df.format(new Date())));
        } catch (NullPointerException | IllegalArgumentException e) {
            ps.setString(8, "0000-00-00");
        }
        
        ps.setString(9, fineId);
        
        try {
            ps.setDate(10, rs.getDate("return_date"));
        } catch (NullPointerException | IllegalArgumentException e) {
            ps.setString(7, "0000-00-00");
        }
        
        ps.execute();
        //HISTORY DATABASE ENTRY FINISHED...
    }

    public boolean returnBook(final String memberId, final String bookId) {
        boolean isReturned = false;
        try {
            
            //Below Function Saves the Information of Book as history...
            createBookReturnHistory(memberId, bookId, null);

            //DELETING THE SELECTED ENTRY FROM ISSUE TABLE...
            ps = con.prepareStatement("DELETE FROM ISSUE_BOOK\n"
                    + "WHERE BOOK_ID = '" + bookId + "' AND STUDENT_ID = '" + memberId + "'");
            if (!ps.execute()) {
                ps = con.prepareStatement("UPDATE book SET `status` = \"" + getBookStatus(bookId) + "\" WHERE ID = '" + bookId + "'");
                isReturned = !ps.execute();

                {//Sending Automatic Email to the Member who Issued the Book
                    new Thread(() -> {
                        String studentEmail;
                        String author;
                        String title;
                        try {
                            connection();
                            {//Conforming weather Automatic Emails are turned On....
                                PreparedStatement ps = con.prepareStatement("SELECT autoEmail2 as a2 FROM email_settings WHERE ID = 1");
                                ResultSet rs2 = ps.executeQuery();
                                if (rs2.next()) {
                                    if (rs2.getString("a2").equals("OFF")) {
                                        throw new NewException("");
                                    }
                                } else {
                                    throw new NewException("");
                                }
                            }

                            {//Getting the email of student.
                                PreparedStatement ps = con.prepareStatement("SELECT EMAIL FROM STUDENT_DATA WHERE ID = " + memberId);
                                ResultSet rs2 = ps.executeQuery();
                                rs2.next();
                                studentEmail = rs2.getString("email");
                            }

                            {//getting the title and author of book.
                                PreparedStatement ps = con.prepareStatement("SELECT title, author FROM BOOK WHERE ID = " + bookId);
                                ResultSet rs2 = ps.executeQuery();
                                rs2.next();
                                title = rs2.getString("title");
                                author = rs2.getString("author");
                            }
                            String msgEmail = "<br>ID: " + bookId + "<br>Title: " + title + "<br> Author: " + author;
                            new EmailPackage.AutomaticEmail().returnBookEmail(memberId, studentEmail, msgEmail, true);
                        } catch (SQLException | ClassNotFoundException ex) {
                            String message = "Failed to Save Settings \n Because: " + ex;
                            JOptionPane.showMessageDialog(this, message, "Emails", JOptionPane.ERROR_MESSAGE);
                            //Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NewException ex) {
                            //Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }).start();
                }
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return isReturned;
    }
    
    public boolean returnBook(final String studentId, final String bookId, final String bookStatus, String fineId) {
        boolean isReturned = false;
        try {
            //Below Function Saves the Information of Book as history...
            createBookReturnHistory(studentId, bookId, fineId);

            //DELETING THE SELECTED ENTRY FROM ISSUE TABLE...
            ps = con.prepareStatement("DELETE FROM ISSUE_BOOK\n"
                    + "WHERE BOOK_ID = '" + bookId + "' AND STUDENT_ID = '" + studentId + "'");
            if (!ps.execute()) {
                ps = con.prepareStatement("UPDATE book SET `status` = \"" + bookStatus + "\" WHERE ID = '" + bookId + "'");
                isReturned = !ps.execute();

                {//Sending Automatic Email to the Member who Issued the Book
                    new Thread(() -> {
                        String studentEmail;
                        String author;
                        String title;
                        try {
                            connection();
                            {//Conforming weather Automatic Emails are turned On....
                                PreparedStatement ps = con.prepareStatement("SELECT autoEmail2 as a2 FROM email_settings WHERE ID = 1");
                                ResultSet rs2 = ps.executeQuery();
                                if (rs2.next()) {
                                    if (rs2.getString("a2").equals("OFF")) {
                                        throw new NewException("");
                                    }
                                } else {
                                    throw new NewException("");
                                }
                            }

                            {//Getting the email of student.
                                PreparedStatement ps = con.prepareStatement("SELECT EMAIL FROM STUDENT_DATA WHERE ID = " + studentId);
                                ResultSet rs2 = ps.executeQuery();
                                rs2.next();
                                studentEmail = rs2.getString("email");
                            }

                            {//getting the title and author of book.
                                PreparedStatement ps = con.prepareStatement("SELECT title, author FROM BOOK WHERE ID = " + bookId);
                                ResultSet rs2 = ps.executeQuery();
                                rs2.next();
                                title = rs2.getString("title");
                                author = rs2.getString("author");
                            }
                            String msgEmail = "<br>ID: " + bookId + "<br>Title: " + title + "<br> Author: " + author;
                            new EmailPackage.AutomaticEmail().returnBookEmail(studentId, studentEmail, msgEmail, true);
                        } catch (SQLException | ClassNotFoundException ex) {
                            String message = "Failed to Save Settings \n Because: " + ex;
                            JOptionPane.showMessageDialog(this, message, "Emails", JOptionPane.ERROR_MESSAGE);
                            //Logger.getLogger(EmailSettings.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NewException ex) {
                            //Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }).start();
                }
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return isReturned;
    }
    private void btnReturnedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnedActionPerformed
        int row = tblReturnBookList.getSelectedRow();
        String bookId = tm.getValueAt(tblReturnBookList.getSelectedRow(), 3).toString();
        if (returnBook(tm.getValueAt(tblReturnBookList.getSelectedRow(), NORMAL).toString(), bookId)) {
            JOptionPane.showMessageDialog(this, tm.getValueAt(row, 4) + " Book Returned", "Return Book", JOptionPane.INFORMATION_MESSAGE);
            new All_Information().showReservationMessage(bookId, tm.getValueAt(tblReturnBookList.getSelectedRow(), 4).toString());
            setIssueTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't Return the Book\n", "Return Book", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnReturnedActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        txtSearch.setText(MainForm.focusGainedLogic(txtSearch.getText(), "Search"));
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        txtSearch.setText(MainForm.focusLostLogic(txtSearch.getText(), "Search"));
    }//GEN-LAST:event_txtSearchFocusLost

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        try {
            connection();
            tm.setRowCount(0);
            if (txtSearch.getText().equals("")) {
                setIssueTableData();
            } else {
                ps = con.prepareStatement("SELECT ib.student_id, sd.first_name, ib.book_id, b.name, ib.student_group, ib.issue_date, ib.return_date FROM LMS.ISSUE_BOOK ib\n"
                        + "JOIN student_data sd ON ib.student_id = sd.id\n"
                        + "JOIN lms.book b ON ib.book_id = b.id\n"
                        + "WHERE ib.student_id REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "sd.first_name REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "ib.book_id REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "b.name REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "ib.student_group REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "ib.issue_date REGEXP '" + txtSearch.getText() + "' OR\n"
                        + "ib.return_date REGEXP '" + txtSearch.getText() + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(changeNullString(rs.getString("student_id")));
                    v.add(changeNullString(rs.getString("first_name")));
                    v.add(changeNullString(rs.getString("book_id")));
                    v.add(changeNullString(rs.getString("name")));
                    v.add(changeNullString(rs.getString("student_group")));
                    v.add(changeNullString(rs.getString("issue_date")));
                    v.add(changeNullString(rs.getString("return_date")));
                    tm.addRow(v);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReturnBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturned;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReturnBookList;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
