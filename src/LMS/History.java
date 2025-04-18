/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LMS;

import java.awt.Color;
import static LMS.MainForm.connection;
import static LMS.MainForm.con;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import static LMS.MainForm.focusGainedLogic;
import static LMS.MainForm.focusLostLogic;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdul Rehman
 */
public class History extends javax.swing.JFrame {
    DefaultTableModel tmData;
    PreparedStatement ps;
    
    /**
     * Creates new form History
     */
    public History() {
        initComponents();
        tmData = (DefaultTableModel) tblData.getModel();
        settblData("");
    }
    
    private void settblData(String limitClause){
        try {
            int recordCount = 0;
            tmData.setRowCount(0);
            connection();
            ps = con.prepareStatement("SELECT * FROM history ORDER BY ID DESC " + limitClause);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("student_id"));
                v.add(rs.getString("student_name"));
                v.add(rs.getString("category"));
                v.add(rs.getString("book_id"));
                v.add(rs.getString("book_name"));
                v.add(rs.getString("author"));
                v.add(rs.getString("issue_date"));
                v.add(rs.getString("returned_date"));
                tmData.addRow(v);
                recordCount++;
            }
            lblRecords.setText("Records: " + recordCount);
        } catch (java.sql.SQLSyntaxErrorException e){
            cbRecords.setSelectedIndex(0);
            tfSearch.setText("Search History");
            JOptionPane.showMessageDialog(this, "Enter Only Numbers.", "Wrong Input.", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnClearHistory = new javax.swing.JButton();
        tfSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cbRecords = new javax.swing.JComboBox<>();
        btnRecordSearch = new javax.swing.JButton();
        lblRecords = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("History");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "History ID", "Student ID", "Student Name", "Category", "Acc No", "Title", "Author", "Issue Date", "Return Date"
            }
        ));
        jScrollPane1.setViewportView(tblData);

        btnClearHistory.setBackground(new java.awt.Color(204, 204, 204));
        btnClearHistory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnClearHistory.setMnemonic('C');
        btnClearHistory.setText("Clear History");
        btnClearHistory.setBorder(null);
        btnClearHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClearHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClearHistoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClearHistoryMouseExited(evt);
            }
        });
        btnClearHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearHistoryActionPerformed(evt);
            }
        });

        tfSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfSearch.setText("Search History");
        tfSearch.setToolTipText("");
        tfSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSearchFocusLost(evt);
            }
        });
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbRecords.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbRecords.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Records", "First 10 Records", "First 20 Records", "Custom" }));
        cbRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRecordsActionPerformed(evt);
            }
        });

        btnRecordSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRecordSearch.setText("Search");
        btnRecordSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRecordSearch)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRecordSearch)
                    .addComponent(cbRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblRecords.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRecords.setText("Records");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClearHistory)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRecords)
                                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClearHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearHistoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearHistoryMouseEntered
        btnClearHistory.setBackground(new Color(153,153,153));
    }//GEN-LAST:event_btnClearHistoryMouseEntered

    private void btnClearHistoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearHistoryMouseExited
        btnClearHistory.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_btnClearHistoryMouseExited

    private void tfSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSearchFocusGained
        tfSearch.setText(focusGainedLogic(tfSearch.getText(), "Search History"));
    }//GEN-LAST:event_tfSearchFocusGained

    private void tfSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSearchFocusLost
        tfSearch.setText(focusLostLogic(tfSearch.getText(), "Search History"));
    }//GEN-LAST:event_tfSearchFocusLost

    private void tfSearchKeyReleasedLogic(String limitClause) {
        try{
            String search = tfSearch.getText();
            tmData.setRowCount(0);
            connection();
            if (!(search.equals("") || search.equals("Search History"))){
                ps = con.prepareStatement("SELECT * FROM history \n" +
                        "WHERE student_id REGEXP '"+search+"' OR\n" +
                        "student_name REGEXP '"+search+"' OR\n" +
                        "id REGEXP '"+search+"' OR\n" +
                        "category REGEXP '"+search+"' OR\n" +
                        "book_id REGEXP '"+search+"' OR\n" +
                        "book_name REGEXP '"+search+"' OR\n" +
                        "author REGEXP '"+search+"' OR\n" +
                        "issue_date REGEXP '"+search+"' OR \n" +
                        "returned_date REGEXP '"+search+"'\n" +
                        "ORDER BY ID DESC " + limitClause);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("id"));
                    v.add(rs.getString("student_id"));
                    v.add(rs.getString("student_name"));
                    v.add(rs.getString("category"));
                    v.add(rs.getString("book_id"));
                    v.add(rs.getString("book_name"));
                    v.add(rs.getString("author"));
                    v.add(rs.getString("issue_date"));
                    v.add(rs.getString("returned_date"));
                    tmData.addRow(v);
                }
            }else
                settblData(limitClause);
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
        tfSearchKeyReleasedLogic("");
    }//GEN-LAST:event_tfSearchKeyReleased

    boolean isCbRecordsEditable = false;
    private void btnRecordSearchActionPerformed(){
        if (isCbRecordsEditable){tfSearchKeyReleasedLogic("Limit " + cbRecords.getSelectedItem());}
        switch (cbRecords.getSelectedIndex()){
            case 0 -> {tfSearchKeyReleasedLogic("");}
            case 1 -> {tfSearchKeyReleasedLogic("Limit 10");}
            case 2 -> {tfSearchKeyReleasedLogic("Limit 20");}
        }
    }
    
    private void btnRecordSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordSearchActionPerformed
        btnRecordSearchActionPerformed();
    }//GEN-LAST:event_btnRecordSearchActionPerformed

    
    private void cbRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRecordsActionPerformed
        if (cbRecords.getSelectedIndex() == 3){
            isCbRecordsEditable = true;
            cbRecords.setEditable(true);
            cbRecords.setSelectedItem(null);
        }
        else
            cbRecords.setEditable(false);
        
        // Making isCbRecordsEditable false...
        switch (cbRecords.getSelectedIndex()){
            case 0 -> {isCbRecordsEditable = false;}
            case 1 -> {isCbRecordsEditable = false;}
            case 2 -> {isCbRecordsEditable = false;}
        }
    }//GEN-LAST:event_cbRecordsActionPerformed

    public boolean btnClearHistoryActionPerformedLogic(int[] a){
        String whereClause = "WHERE id IN (" + a[0];
        for (int i=1; i<a.length; i++)
            whereClause += "," + a[i];
        
        boolean check = false;
        try {
            connection();
            ps = con.prepareStatement("DELETE FROM history\n" +
                                    whereClause+")");
            check = !ps.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    public boolean btnClearHistoryActionPerformedLogic(String limitClause){
        boolean check = false;
        try {
            connection();
            ps = con.prepareStatement("DELETE FROM history\n" +
                                        "WHERE id IN (\n" +
                                        "			SELECT id FROM (SELECT ID FROM history) AS MhN)\n" +
                                        "ORDER BY id DESC\n" +
                                        limitClause);
            check = !ps.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    
    private void btnClearHistoryActionPerformed(){
        boolean check = false;
        if (!tblData.getSelectionModel().isSelectionEmpty()){
            int[] selectedRowsIds = new int[tblData.getSelectedRows().length];
            int index = 0;
            for (int i : tblData.getSelectedRows()){
                selectedRowsIds[index] = Integer.parseInt(tmData.getValueAt(i, NORMAL).toString());
                index++;
            }
            check = btnClearHistoryActionPerformedLogic(selectedRowsIds);
        }
        else{
            if (isCbRecordsEditable){check = btnClearHistoryActionPerformedLogic("Limit " + cbRecords.getSelectedItem());}
            switch (cbRecords.getSelectedIndex()){
                case 0 -> {check = btnClearHistoryActionPerformedLogic("");}
                case 1 -> {check = btnClearHistoryActionPerformedLogic("Limit 10");}
                case 2 -> {check = btnClearHistoryActionPerformedLogic("Limit 20");}
            }
        }
        if(check)
            btnRecordSearchActionPerformed();
    }
    private void btnClearHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearHistoryActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Do You Want to Clear History", "Clear History", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == 
                JOptionPane.YES_OPTION)
            
            btnClearHistoryActionPerformed();
    }//GEN-LAST:event_btnClearHistoryActionPerformed

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
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new History().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearHistory;
    private javax.swing.JButton btnRecordSearch;
    private javax.swing.JComboBox<String> cbRecords;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecords;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
