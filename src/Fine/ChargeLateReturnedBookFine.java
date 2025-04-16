/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fine;

import java.awt.Color;
import static LMS.MainForm.connection;
import static LMS.MainForm.con;
import LMS.NewException;
import LMS.ReturnBooks;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Abdul Rehman
 */
public class ChargeLateReturnedBookFine extends javax.swing.JFrame {
    PreparedStatement ps;
    String memberName = "";

    /**
     * Creates new form ChargeFine
     */
    public ChargeLateReturnedBookFine() {
        initComponents();
        comboBoxItems();
    }
    
    public ChargeLateReturnedBookFine(String memberId, String bookId) {
        initComponents();
        
        try {
            connection();
            
            {//Searching Previous Fines of this Member.
                ps = con.prepareStatement("SELECT SUM(FINE) AS TFINE FROM FINE \n" +
                            "WHERE student_id = "+memberId+"");
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    lblPreviousFine.setText(rs.getString("TFine"));
            }
            {//Searching Member and filling necessary Information...
                ps = con.prepareStatement("SELECT first_name AS fn, Last_Name AS ln FROM student_data\n" +
                    "WHERE ID = '"+memberId+"'");
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    memberName = rs.getString("fn") + " " + rs.getString("ln");
                }

                lblMemberId.setText(memberId);
                lblName.setText(memberName);

            }
            
            {//Searching for Book Data...
                ps = con.prepareStatement("SELECT TITLE AS T, PRICE AS P FROM book\n" +
                        "WHERE ID = "+bookId+"");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    lblBookPrice.setText(rs.getString("p"));
                    tfFine.setText(rs.getString("p"));
                    lblBookId.setText(bookId);
                    lblTitle.setText(rs.getString("t"));
                }
            }
            
            {//Searching for late days, fine per day and calculated fine.
                //Checking if the fine is once or daily basis.
                //Also Getting the Number of day in which the member has to pay the fine. OR number of day saved 
                //helps to decide the last date of fine.
                
                ps = con.prepareStatement("SELECT DATEDIFF(CURRENT_DATE(), RETURN_DATE) AS DIFF, \n" +
                    "DATEDIFF(CURRENT_DATE(), RETURN_DATE)*\n" +
                    "		(SELECT `VALUE` FROM GENERAL_SETTINGS WHERE ID = 2) AS AMOUNT, \n" +
                    "        (SELECT `VALUE` FROM GENERAL_SETTINGS WHERE ID = 2) AS `value`, \n" +
                    "        (SELECT SET2 FROM GENERAL_SETTINGS WHERE ID = 2) AS SET2,\n" +
                    "        (SELECT SET3 FROM GENERAL_SETTINGS WHERE ID = 2) AS SET3\n" +
                    "FROM ISSUE_BOOK\n" +
                    "WHERE CURRENT_DATE() > RETURN_DATE AND BOOK_ID = '"+bookId+"' AND STUDENT_ID = '"+memberId+"'");
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    lblLateDays.setText(rs.getString("DIFF"));
                    if (rs.getString("SET2").toLowerCase().equals("once")){
                        lblFine.setText(rs.getString("value"));
                        tfFine.setText(rs.getString("value"));
                    }
                    else{
                        lblFine.setText(rs.getString("value"));
                        tfFine.setText(rs.getString("AMOUNT"));
                    }
                    int days = rs.getInt("set3");
                    try{
                        if (days == 0)
                            throw new NewException("");
                        dateChooserLastDate.setCalendar(new General.Date().addDaysToCurrentDate(days));
                    }catch(NewException e){}
                    catch(Exception e){System.out.println(e);}
                    
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(Level.SEVERE, null, ex);
        }

        comboBoxItems();
    }
    
    private void comboBoxItems(){
        //Book Lost, teared, Missing Pages.
        comboBoxPurpose.addItem("Late Returned");
        comboBoxPurpose.setEnabled(false);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblBookPrice = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblBookId = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblMemberId = new javax.swing.JLabel();
        lblPreviousFine = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tfFine = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        comboBoxPurpose = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dateChooserLastDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblFine = new javax.swing.JLabel();
        lblLateDays = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnCharge = new javax.swing.JButton();
        btnChargeAsPaid = new javax.swing.JButton();
        btnChargeAsPaid1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Charge Fine");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Fine For Late Book Return");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Book Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 0, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setText("Book Price:");

        lblBookPrice.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblBookPrice.setText("50");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 255));
        jLabel9.setText("Title");

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblTitle.setText("Fine");
        lblTitle.setToolTipText("");
        lblTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTitleMouseEntered(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setText("Book ID:");

        lblBookId.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblBookId.setText("Fine");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBookPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBookId, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblBookPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblBookId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTitle))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Member Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 0, 255))); // NOI18N

        lblMemberId.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblMemberId.setText("Fine");

        lblPreviousFine.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblPreviousFine.setText("50");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 255));
        jLabel14.setText("Name:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 255));
        jLabel10.setText("Total Prev. Fine:");

        lblName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblName.setText("Fine");
        lblName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNameMouseEntered(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 255));
        jLabel13.setText("Member ID:");

        lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbl.setForeground(new java.awt.Color(255, 51, 0));
        lbl.setText(" ! ");
        lbl.setToolTipText("Get Full Detail About Previous Fines");
        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPreviousFine, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblPreviousFine)
                    .addComponent(lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMemberId)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblName))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fine Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 0, 255))); // NOI18N

        tfFine.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfFine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfFineKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 255));
        jLabel5.setText("Description:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 255));
        jLabel4.setText("Fine:");

        taDescription.setColumns(20);
        taDescription.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        taDescription.setRows(5);
        jScrollPane1.setViewportView(taDescription);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 255));
        jLabel16.setText("Purpose:");

        comboBoxPurpose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboBoxPurpose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPurposeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Rs");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 255));
        jLabel17.setText("Last Date:");

        dateChooserLastDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 255));
        jLabel7.setText("Late Days:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Rs");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 255));
        jLabel12.setText("Fine Per Day/Once:");

        lblFine.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblFine.setText("100000");

        lblLateDays.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblLateDays.setText("100000");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Rs");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(22, 22, 22)
                        .addComponent(lblLateDays, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel16))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(comboBoxPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserLastDate, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(56, 56, 56)
                        .addComponent(tfFine, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(14, 14, 14)
                        .addComponent(lblFine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblLateDays)
                    .addComponent(jLabel11))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblFine)
                    .addComponent(jLabel8))
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfFine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(dateChooserLastDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(comboBoxPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCharge.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCharge.setText("Charge");
        btnCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChargeActionPerformed(evt);
            }
        });

        btnChargeAsPaid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChargeAsPaid.setText("Paid");
        btnChargeAsPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChargeAsPaidActionPerformed(evt);
            }
        });

        btnChargeAsPaid1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChargeAsPaid1.setText("Pardon");
        btnChargeAsPaid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChargeAsPaid1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnChargeAsPaid1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnChargeAsPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCharge))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCharge)
                    .addComponent(btnChargeAsPaid)
                    .addComponent(btnChargeAsPaid1))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMouseEntered
        lbl.setForeground(new Color(153,153,153));
    }//GEN-LAST:event_lblMouseEntered

    private void lblMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMouseExited
        lbl.setForeground(new Color(255,51,0));
    }//GEN-LAST:event_lblMouseExited

    private void tfFineKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFineKeyTyped
        if (!(Character.isDigit(evt.getKeyChar())))
            evt.consume();
    }//GEN-LAST:event_tfFineKeyTyped

    private void chargeFine(String fineStatus){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date lastdate = dateChooserLastDate.getDate();
            String date = df.format(lastdate);
            
            if (tfFine.getText().equals(""))
                throw new NewException("Please Specify the Fine Amount.");
            
            String memberId = lblMemberId.getText();
            String bookId = lblBookId.getText();
            String purpose = comboBoxPurpose.getSelectedItem().toString();
            String fine = tfFine.getText();
            String status = "Unpaid";
            String description = taDescription.getText();
            int fineId;
            
            connection();
            
            //Calculating Fine Id...
            ps = con.prepareStatement("SELECT MAX(ID) AS ID FROM FINE");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                fineId = rs.getInt("id");
                fineId++;
            }
            else
                throw new SQLException("Error! Can't Calculate Fine Id");
            
            //Saving Data...
            ps = con.prepareStatement("INSERT INTO FINE(STUDENT_ID, BOOK_ID, PURPOSE, `DATE`, FINE, `STATUS`, `DESCRIPTION`, id, `last_date`)\n" +
                    "VALUES (?, ?, ?, current_date(), ?, ?, ?, ?, ?)");
            ps.setString(1, memberId);
            ps.setString(2, bookId);
            ps.setString(3, purpose);
            ps.setString(4, fine);
            ps.setString(5, status);
            ps.setString(6, description);
            ps.setInt(7, fineId);
            ps.setDate(8, java.sql.Date.valueOf(date));
            
            if (!ps.execute()){
                //Setting Book Status(Available/Unavailable)...
                String bookStatus = "Available";
                StringBuilder msg = new StringBuilder(memberName+" is Charged for " + fine + " Rupees.");
                
                new ReturnBooks().returnBook(memberId, bookId, bookStatus, String.valueOf(fineId));
                
                {//Paying fine If (charge as paid OR pardon) Button is clicked
                    switch (fineStatus){
                        case("Paid")->{
                            FineFunctions f = new FineFunctions();
                            f.payFine(String.valueOf(fineId));
                        }
                        case("Pardoned")->{
                            FineFunctions f = new FineFunctions();
                            f.pardonFine(String.valueOf(fineId));
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, msg, "Fine", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NewException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }catch(NullPointerException ex){
            Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Please Choose the Last Date of Paying Fine", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnChargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChargeActionPerformed
        chargeFine("Unpaid");
    }//GEN-LAST:event_btnChargeActionPerformed

    private void comboBoxPurposeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPurposeActionPerformed
        if (comboBoxPurpose.getSelectedItem().toString().equals("Custom")){
            comboBoxPurpose.setEditable(true);
            comboBoxPurpose.setSelectedItem(null);
        }
        else
            comboBoxPurpose.setEditable(false);
    }//GEN-LAST:event_comboBoxPurposeActionPerformed

    private void lblTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleMouseEntered
        lblTitle.setToolTipText(lblTitle.getText());
    }//GEN-LAST:event_lblTitleMouseEntered

    private void lblNameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNameMouseEntered
        lblName.setToolTipText(lblName.getText());
    }//GEN-LAST:event_lblNameMouseEntered

    private void btnChargeAsPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChargeAsPaidActionPerformed
        chargeFine("Paid");
    }//GEN-LAST:event_btnChargeAsPaidActionPerformed

    private void lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMouseClicked
        new PreviousSpecificInformation(lblMemberId.getText()).setVisible(true);
    }//GEN-LAST:event_lblMouseClicked

    private void btnChargeAsPaid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChargeAsPaid1ActionPerformed
        chargeFine("Pardoned");
    }//GEN-LAST:event_btnChargeAsPaid1ActionPerformed

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
            java.util.logging.Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChargeLateReturnedBookFine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChargeLateReturnedBookFine().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCharge;
    private javax.swing.JButton btnChargeAsPaid;
    private javax.swing.JButton btnChargeAsPaid1;
    private javax.swing.JComboBox<String> comboBoxPurpose;
    private com.toedter.calendar.JDateChooser dateChooserLastDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lblBookId;
    private javax.swing.JLabel lblBookPrice;
    private javax.swing.JLabel lblFine;
    private javax.swing.JLabel lblLateDays;
    private javax.swing.JLabel lblMemberId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPreviousFine;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTextField tfFine;
    // End of variables declaration//GEN-END:variables
}
