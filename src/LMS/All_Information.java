
//Some Code is Commented here because of missing files...


package LMS;

import Books.UnavailableBooks;
import Books.BooksReturnedToday;
import Books.BooksIssuedToday;
import Books.Edit_Multiple_Books;
import Books.Edit_Book;
import Books.Add_Book;
import Settings.Settings;
import static LMS.MainForm.con;
import static LMS.MainForm.connection;
import static LMS.MainForm.focusGainedLogic;
import static LMS.MainForm.focusLostLogic;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.awt.FileDialog;
//import AR_PopupMenu.PopupMenu;
import General.DoubleClickDetector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import ar_sql_backup.AR_SQL_Backup;
import static java.awt.Frame.NORMAL;

/**
 *
 * @author Abdul Rehman Lashari
 */
public class All_Information extends javax.swing.JFrame {

    DefaultTableModel tmBooksBookData;
    DefaultTableModel tmStudentsStudentData;
    DefaultTableCellRenderer centerText;
    DefaultTableModel tmStudentGroupsData;
    DefaultTableModel tmBookGroupsData, tmtblStudentGroupsIssuedBookData;
    DefaultTableModel tmtblBookGroupsIssueData;
    DefaultTableModel tmStatisticsMemberData;
    PreparedStatement ps;
    public static DoubleClickDetector doubleClick = new DoubleClickDetector();;
    
    //Settings Variables....
    public static boolean doReturnBookOnDoubleClick;
    public static boolean doPayFineOnDoubleClick = false;
    public static int fontSize = 14;
    
    
    /**
     * Creates new form All_Information
     */
    public All_Information() {
        initComponents();
        tmStudentsStudentData = (DefaultTableModel) tblStudentsStudentData.getModel();
        {//Centering the first Column of Tables...

            //Centering the Columns of student table.
            {
                int[] columns = {0, 5, 4, 8, 6};
                centeringColumnsOfTables(tblStudentsStudentData, columns);
            }

            //Centering the Columns of Book Table.
            {
                int[] columns = {0, 3, 5, 6, 7, 8, 9, 11, 12};
                centeringColumnsOfTables(tblBooksBookData, columns);
            }

            //Centerting the Columns of Statistics table..
            {
                int[] columns = {0, 2, 3, 6, 7};
                centeringColumnsOfTables(tblStatisticsMemberData, columns);
            }

            //Centerting the Columns of Member Category Tables..
            {
                int[] columns = {0, 3};
                centeringColumnsOfTables(tblStudentGroupsData, columns);
                centeringColumnsOfTables(tblStudentGroupsData, columns);
            }

            //Centerting the Columns of Book Category Tables...
            {
                int[] columns = {0, 3, 5, 6, 8, 9};
                centeringColumnsOfTables(tblBookGroupsData, columns);
            }
        }

        //Initializing Global Variables...
        tmBooksBookData = (DefaultTableModel) tblBooksBookData.getModel();
        tmStudentGroupsData = (DefaultTableModel) tblStudentGroupsData.getModel();
        tmBookGroupsData = (DefaultTableModel) tblBookGroupsData.getModel();
        tmtblStudentGroupsIssuedBookData = (DefaultTableModel) tblStudentGroupsIssuedBookData.getModel();
        tmStatisticsMemberData = (DefaultTableModel) tblStatisticsMemberData.getModel();
        
        doubleClick.setDelayBetweenClicks(250);

        settingIcon();
        settblStatisticsBookIssuedData();
        settblStudentsStudentData();
        setBooksBookData();

        //Student Group Portion Functions
        setcbStudentGroupsNames();

        //Book Group Portion Functions..
        setcbBookGroupsNames();

        //AUTOMATIC EMAIL PACKAGEG FUNCTIONS...
        new EmailPackage.AutomaticEmail().sendEmailAtReturnDate();
        new EmailPackage.AutomaticEmail().sendEmailAfterReturnDate();

        //Adding Right Click Popup Menus
        addRightClickPopupMenuesToStudentTable();
        addRightClickPopupMenuesToBookTable();
        addRightClickPopupMenuesToStudentGroupTable();
        
        
        //General Settings...
        setGeneralSettings();
    }
    
    private void addRightClickPopupMenuesToStudentTable() {
//        PopupMenu p = new PopupMenu();
//        p.addOption("View", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                new FramesJustForShowingInformation.StudentInformation(
//                    (String) tblStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL))
//                    .setVisible(true);
//            }
//        });
//        
//        p.addOption("Add Member", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Add_Student().setVisible(true);
//            }
//        });
//        
//        p.addOption("Edit Member", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Edit_Student(tmStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL)
//                        .toString()).setVisible(true);
//            }
//        });
//        p.show(tblStudentsStudentData);
//        
//        p.addOption("Activation", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                new Activate_Student_Account((String)tblStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL)).setVisible(true);
//            }
//        });
//        
//        p.addOption("Save Image", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                String studentId = (String)tblStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(),NORMAL);
//                try {
//                    FileDialog f = new FileDialog((java.awt.Frame)null, "Select Path", FileDialog.SAVE);
//                    
//                    connection();
//                    ps = con.prepareStatement("SELECT ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) AS NAME, Image FROM STUDENT_DATA"
//                            + " WHERE ID = '"+studentId+"'");
//                    ResultSet rs = ps.executeQuery();
//                    if (rs.next()){
//                        //Here we set the Name of the File which is (Name of Member + it's ID)
//                        f.setFile(rs.getString("Name") + " (" + rs.getString("id") + ").jpg");
//                        
//                        f.setVisible(true);
//                        
//                        if (new AR_SQL_Backup().savePicture(rs.getBytes("Image"), f.getDirectory(), f.getFile()))
//                            JOptionPane.showMessageDialog(null, "Image has been saved Sucessfully!", "Save Image", JOptionPane.INFORMATION_MESSAGE);
//                        else
//                            JOptionPane.showMessageDialog(null, "Unable to Save the Image", "Error", JOptionPane.ERROR_MESSAGE);
//                        
//                    }else
//                        JOptionPane.showMessageDialog(null, "This Member has No Image", "Error", JOptionPane.ERROR_MESSAGE);
//                } catch (SQLException | ClassNotFoundException | IOException ex) {
//                    Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }
    
    private void addRightClickPopupMenuesToStudentGroupTable() {
//        PopupMenu p = new PopupMenu();
//        p.addOption("View", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                new FramesJustForShowingInformation.StudentInformation(
//                    (String) tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(), NORMAL))
//                    .setVisible(true);
//            }
//        });
//        
//        p.addOption("Add Member", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Add_Student().setVisible(true);
//            }
//        });
//        
//        p.addOption("Edit Member", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Edit_Student(tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(), NORMAL)
//                        .toString()).setVisible(true);
//            }
//        });
//        p.show(tblStudentGroupsData);
//        
//        p.addOption("Activation", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                new Activate_Student_Account((String)tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(), NORMAL)).setVisible(true);
//            }
//        });
//        
//        p.addOption("Save Image", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                String studentId = (String)tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(),NORMAL);
//                try {
//                    FileDialog f = new FileDialog((java.awt.Frame)null, "Select Path", FileDialog.SAVE);
//                    
//                    connection();
//                    ps = con.prepareStatement("SELECT ID, CONCAT(FIRST_NAME, ' ', LAST_NAME) AS NAME, Image FROM STUDENT_DATA"
//                            + " WHERE ID = '"+studentId+"'");
//                    ResultSet rs = ps.executeQuery();
//                    if (rs.next()){
//                        //Here we set the Name of the File which is (Name of Member + it's ID)
//                        f.setFile(rs.getString("Name") + " (" + rs.getString("id") + ").jpg");
//                        
//                        f.setVisible(true);
//                        
//                        if (new AR_SQL_Backup().savePicture(rs.getBytes("Image"), f.getDirectory(), f.getFile()))
//                            JOptionPane.showMessageDialog(null, "Image has been saved Sucessfully!", "Save Image", JOptionPane.INFORMATION_MESSAGE);
//                        else
//                            JOptionPane.showMessageDialog(null, "Unable to Save the Image", "Error", JOptionPane.ERROR_MESSAGE);
//                        
//                    }else
//                        JOptionPane.showMessageDialog(null, "This Member has No Image", "Error", JOptionPane.ERROR_MESSAGE);
//                } catch (SQLException | ClassNotFoundException | IOException ex) {
//                    Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }
    
    private void addRightClickPopupMenuesToBookTable(){
//        PopupMenu p = new PopupMenu();
//        p.addOption("View", new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                new FramesJustForShowingInformation.BookInformation((String)tblBooksBookData.getValueAt(
//                    tblBooksBookData.getSelectedRow(), NORMAL)).setVisible(true);
//            }
//        });
//        
//        p.addOption("Save Image", new ActionListener(){
//        @Override
//            public void actionPerformed(ActionEvent e){
//                String bookId = (String)tblBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(),NORMAL);
//                try {
//                    FileDialog f = new FileDialog((java.awt.Frame)null, "Select Path", FileDialog.SAVE);
//
//                    connection();
//                    ps = con.prepareStatement("SELECT ID, CONCAT(TITLE, ' ', AUTHOR) AS NAME, FRONTImage, BACKIMAGE FROM BOOK"
//                            + " WHERE ID = '"+bookId+"'");
//                    ResultSet rs = ps.executeQuery();
//                    if (rs.next()){
//                        //Here we set the Name of the File which is (Name of Member + it's ID)
//                        String nameForFrontImage = rs.getString("Name") + " (" + rs.getString("id")+ ") FrontImage" + ".jpg";
//                        String nameForBackImage = rs.getString("Name") + " (" + rs.getString("id")+ ") BackImage" + ".jpg";
//                        f.setFile(nameForFrontImage);
//
//                        f.setVisible(true);
//
//                        if (new AR_SQL_Backup().savePicture(rs.getBytes("frontImage"), f.getDirectory(), nameForFrontImage)
//                                && new AR_SQL_Backup().savePicture(rs.getBytes("backImage"), f.getDirectory(), nameForBackImage))
//                            JOptionPane.showMessageDialog(null, "Images has been saved Sucessfully!", "Save Image", JOptionPane.INFORMATION_MESSAGE);
//                        else
//                            JOptionPane.showMessageDialog(null, "Unable to Save the Image", "Error", JOptionPane.ERROR_MESSAGE);
//
//                    }else
//                        JOptionPane.showMessageDialog(null, "This Member has No Image", "Error", JOptionPane.ERROR_MESSAGE);
//                } catch (SQLException | ClassNotFoundException | IOException ex) {
//                    Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        p.show(tblBooksBookData);
    }

    public All_Information(boolean wantConstructorEmpaty) {
    }
    
    public final void centeringColumnsOfTables(JTable tbl, int[] columns) {
        DefaultTableCellRenderer centerText = new DefaultTableCellRenderer();
        centerText.setHorizontalAlignment(JLabel.CENTER);
        for (int i : columns) {
            tbl.getColumnModel().getColumn(i).setCellRenderer(centerText);
        }
    }

    private void setBooksBookData() {
        try {
            tmBooksBookData.setRowCount(0);
            connection();
            int i = 0;
            ps = con.prepareStatement("SELECT * FROM book");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("TITLE"));
                v.add(rs.getString("Author"));
                v.add(rs.getString("Subject"));
                v.add(rs.getString("Publisher"));
                v.add(change(rs.getString("Edition")));
                v.add(change(rs.getString("Language")));
                v.add(changePrice(rs.getString("Price") + " " + rs.getString("Currency")));
                v.add(rs.getString("Place"));
                v.add(change(rs.getString("Pages")));
                v.add(rs.getString("Keyword"));
                v.add(rs.getString("ISBN"));
                v.add(rs.getString("status"));
                tmBooksBookData.addRow(v);
                i++;
            }
            lblBookCount.setText("Books: " + i);
            Set_Bottom_Labels();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String changePrice(String price) {
        try {
            if (price.equals("null null") || price.equals("0 Rs")) {
                return "";
            } else {
                return Character.isDigit(price.charAt(0)) ? price : price.substring(0, (price.length() - 2));
            }
        } catch (NullPointerException e) {
            return price;
        }
    }

    public boolean exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        for (int i = 0; i < model.getColumnCount(); i++) {
            bw.write(model.getColumnName(i) + "\t");
        }
        bw.write("\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String data = "";
                try {
                    if (j == 0) {
                        data += "\'";
                    }
                    data += table.getValueAt(i, j).equals("null") ? "" : table.getValueAt(i, j).toString();
                    if (j == 0) {
                        data += "\'";
                    }
                } catch (NullPointerException e) {
                }
                bw.write(data + "\t");
            }
            bw.write("\n");
        }
        bw.close();
        return file.exists();
    }

    public boolean exportTable(JTable table, File file, boolean is3ColumnId) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        for (int i = 0; i < model.getColumnCount(); i++) {
            bw.write(model.getColumnName(i) + "\t");
        }
        bw.write("\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String data = "";
                try {
                    if (j == 0 || j == 3) {
                        data += "\'";
                    }
                    data += table.getValueAt(i, j).equals("null") ? "" : table.getValueAt(i, j).toString();
                    if (j == 0 || j == 3) {
                        data += "\'";
                    }
                } catch (NullPointerException e) {
                }
                bw.write(data + "\t");
            }
            bw.write("\n");
        }
        bw.close();
        return file.exists();
    }

    String change(String s) {
        try {
            return ((s.equals("none") || s.equals("0")) ? "" : s);
        } catch (NullPointerException e) {
            return s;
        }
    }

    private void setStudentPicture() {
        try {
            lblStudentsPicture.setText(null);
            connection();
            ps = con.prepareStatement("SELECT image FROM student_Data\n"
                    + "WHERE ID = \"" + tmStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), 0) + "\"");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] byt = rs.getBytes("image");
                lblStudentsPicture.setIcon(MainForm.setImage(byt, lblStudentsPicture.getWidth(), lblStudentsPicture.getHeight()));
            }
        } catch (NullPointerException e) {
            lblStudentsPicture.setIcon(null);
            lblStudentsPicture.setText("      Picture Not Found");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void settblStudentsStudentData() {
        try {
            int dataInTable = 0;
            connection();
            tmStudentsStudentData.setRowCount(0);
            ps = con.prepareStatement("SELECT * FROM student_data WHERE `STATUS` != 'Cleared'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("first_name"));
                v.add(rs.getString("last_name"));
                v.add(rs.getString("father_name"));
                v.add(rs.getString("phone"));
                v.add(rs.getString("Email"));
                v.add(rs.getString("sex"));
                v.add(rs.getString("address"));
                v.add(rs.getString("group"));
                tmStudentsStudentData.addRow(v);
                dataInTable++;
            }
            lblStudentCount.setText("Members: " + dataInTable);
            studentCountLabel();
            Set_Bottom_Labels();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void settblStatisticsBookIssuedData() {
        DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
        tm.setRowCount(0);
        try {
            connection();
            ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.group, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM "
                    + "lms.issue_book ib\n"
                    + "LEFT JOIN lms.student_data sd ON sd.id = ib.student_id\n"
                    + "LEFT JOIN LMS.BOOK b ON b.id = ib.book_id");
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
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String changeNullString(String nullLine) {
        String s = nullLine;
        try {
            s = (!nullLine.equals("null")) ? nullLine : "";
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return s;
    }

    public final void settingIcon() {
        btnBookIssuedRefresh.setIcon(MainForm.setImage(getClass().getResource("/IconsUsed/RefreshIcon.jpg"), btnBookIssuedRefresh.getWidth(), btnBookIssuedRefresh.getHeight()));
        btnStudentsRefresh.setIcon(MainForm.setImage(getClass().getResource("/IconsUsed/RefreshIcon.jpg"), btnStudentsRefresh.getWidth(), btnStudentsRefresh.getHeight()));
        btnBooksRefresh.setIcon(MainForm.setImage(getClass().getResource("/IconsUsed/RefreshIcon.jpg"), btnBooksRefresh.getWidth(), btnBooksRefresh.getHeight()));
        btnStudentGroupsRefresh.setIcon(MainForm.setImage(getClass().getResource("/IconsUsed/RefreshIcon.jpg"), btnStudentGroupsRefresh.getWidth(), btnStudentGroupsRefresh.getHeight()));
        btnBookGroupsRefresh.setIcon(MainForm.setImage(getClass().getResource("/IconsUsed/RefreshIcon.jpg"), btnBookGroupsRefresh.getWidth(), btnBookGroupsRefresh.getHeight()));
    }

    public void Set_Bottom_Labels() {
        try {
            connection();
            {//This Block is for counting total Students
                ps = con.prepareStatement("SELECT count(id) AS count FROM student_data");
                ResultSet rs = ps.executeQuery();
                rs.next();
                lblStudentStudentCount.setText("Members: " + rs.getInt("count"));
            }
            {//This Block is for counting total Courses
                ps = con.prepareStatement("SELECT count(id) AS count FROM book");
                ResultSet rs = ps.executeQuery();
                rs.next();
                lblStudentCourseCount.setText("Books: " + rs.getInt("count"));
            }
            {//This Block is for Displaying Acadimy Name
                ps = con.prepareStatement("SELECT academy FROM academy");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblAcademyName.setText("Academy: " + rs.getString("academy"));
                } else {
                    lblAcademyName.setText(null);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setCurrentReturnBookData() {
        try {
            connection();
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            tm.setRowCount(0);
            ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.group, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM LMS.ISSUE_BOOK ib\n"
                    + "JOIN student_data sd ON ib.student_id = sd.id\n"
                    + "JOIN lms.book b ON ib.book_id = b.id\n"
                    + "WHERE RETURN_DATE = CURRENT_DATE()");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(changeNullString(rs.getString("student_id")));
                v.add(changeNullString(rs.getString("first_name") + " " + rs.getString("last_Name")));
                v.add(changeNullString(rs.getString("group")));
                v.add(changeNullString(rs.getString("book_id")));
                v.add(changeNullString(rs.getString("title")));
                v.add(changeNullString(rs.getString("author")));
                v.add(changeNullString(rs.getString("issue_date")));
                v.add(changeNullString(rs.getString("return_date")));
                tm.addRow(v);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setReturnedBookData() {
        try {
            connection();
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            tm.setRowCount(0);
            ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.group, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM LMS.ISSUE_BOOK ib\n"
                    + "JOIN student_data sd ON ib.student_id = sd.id\n"
                    + "JOIN lms.book b ON ib.book_id = b.id\n"
                    + "WHERE RETURN_DATE < CURRENT_DATE()");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(changeNullString(rs.getString("student_id")));
                v.add(changeNullString(rs.getString("first_name") + " " + rs.getString("last_Name")));
                v.add(changeNullString(rs.getString("group")));
                v.add(changeNullString(rs.getString("book_id")));
                v.add(changeNullString(rs.getString("title")));
                v.add(changeNullString(rs.getString("author")));
                v.add(changeNullString(rs.getString("issue_date")));
                v.add(changeNullString(rs.getString("return_date")));
                tm.addRow(v);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchAll() {
        try {
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            connection();
            tm.setRowCount(0);
            String search = txtStatisticsSearch.getText();
            if (search.equals("") || search.equals("Search")) {
                settblStatisticsBookIssuedData();
            } else {
                ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.group, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM LMS.ISSUE_BOOK ib\n"
                        + "JOIN student_data sd ON ib.student_id = sd.id\n"
                        + "JOIN lms.book b ON ib.book_id = b.id\n"
                        + "WHERE sd.id REGEXP '" + search + "' OR\n"
                        + "sd.first_name REGEXP '" + search + "' OR\n"
                        + "sd.last_name REGEXP '" + search + "' OR\n"
                        + "sd.group REGEXP '" + search + "' OR\n"
                        + "b.id REGEXP '" + search + "' OR\n"
                        + "b.title REGEXP '" + search + "' OR\n"
                        + "b.author REGEXP '" + search + "' OR\n"
                        + "ib.issue_date REGEXP '" + search + "' OR\n"
                        + "ib.return_date REGEXP '" + search + "'");
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
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchTodayReturns() {
        try {
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            String search = txtStatisticsSearch.getText();
            connection();
            tm.setRowCount(0);
            if (search.equals("") || search.equals("Search")) {
                setCurrentReturnBookData();
            } else {
                ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.`group`, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM LMS.ISSUE_BOOK ib\n"
                        + "JOIN student_data sd ON ib.student_id = sd.id\n"
                        + "JOIN lms.book b ON ib.book_id = b.id\n"
                        + "WHERE RETURN_DATE = CURRENT_DATE() AND\n"
                        + "(sd.id REGEXP '" + search + "' OR\n"
                        + "sd.first_name REGEXP '" + search + "' OR\n"
                        + "sd.last_name REGEXP '" + search + "' OR\n"
                        + "sd.group REGEXP '" + search + "' OR\n"
                        + "b.id REGEXP '" + search + "' OR\n"
                        + "b.title REGEXP '" + search + "' OR\n"
                        + "b.author REGEXP '" + search + "' OR\n"
                        + "ib.issue_date REGEXP '" + search + "' OR\n"
                        + "ib.return_date REGEXP '" + search + "')");
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
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchOutOfDate() {
        try {
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            String search = txtStatisticsSearch.getText();
            connection();
            tm.setRowCount(0);
            if (search.equals("") || search.equals("Search")) {
                setReturnedBookData();
            } else {
                ps = con.prepareStatement("SELECT sd.id as student_id, first_name, last_name, sd.`group`, b.id as book_id, b.title, b.author, ib.ISSUE_DATE, ib.RETURN_DATE FROM LMS.ISSUE_BOOK ib\n"
                        + "JOIN student_data sd ON ib.student_id = sd.id\n"
                        + "JOIN lms.book b ON ib.book_id = b.id\n"
                        + "WHERE RETURN_DATE < CURRENT_DATE() AND\n"
                        + "(sd.id REGEXP '" + search + "' OR\n"
                        + "sd.first_name REGEXP '" + search + "' OR\n"
                        + "sd.last_name REGEXP '" + search + "' OR\n"
                        + "sd.group REGEXP '" + search + "' OR\n"
                        + "b.id REGEXP '" + search + "' OR\n"
                        + "b.title REGEXP '" + search + "' OR\n"
                        + "b.author REGEXP '" + search + "' OR\n"
                        + "ib.issue_date REGEXP '" + search + "' OR\n"
                        + "ib.return_date REGEXP '" + search + "')");
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
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setcbStudentGroupsNames() {
        try {
            cbStudentGroupsNames.removeAllItems();
            connection();
            ps = con.prepareStatement("SELECT DISTINCT `group` FROM LMS.student_data \n"
                    + "WHERE `group` is not null AND `group` != 'none'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbStudentGroupsNames.addItem(rs.getString("group"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setcbBookGroupsNames() {
        try {
            cbBookGroupsNames.removeAllItems();
            connection();
            ps = con.prepareStatement("SELECT DISTINCT `GROUP` FROM LMS.BOOK\n"
                    + "WHERE `group` IS NOT NULL AND `group` != 'None'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbBookGroupsNames.addItem(rs.getString("group"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void studentCountLabel() {
        try {
            connection();
            //Check Total Number of Student that have Issued Books
            ps = con.prepareStatement("SELECT count(distinct student_id) AS N, count(distinct book_id) as BN FROM issue_book");
            ResultSet rs2 = ps.executeQuery();
            if (rs2.next()) {
                lblStudentCountHavingIssuedBooks.setText("Members Having Issued Books: " + rs2.getString("N"));
                statisticsMemberCount.setText("Members: " + rs2.getString("N"));
                statisticsBookCount.setText("Books: " + rs2.getString("BN"));
                lblBookIssuedCount.setText("Issued Books: " + rs2.getString("BN"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    //General Settings Methods
    private void setGeneralSettings() {
        try{
            connection();
            ps = con.prepareStatement("SELECT id, `SET` FROM GENERAL_SETTINGS");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                switch(rs.getInt("id")){
                    case(1) -> doReturnBookOnDoubleClick = rs.getString("set").equals("ON");
                    case(2) -> doPayFineOnDoubleClick = rs.getString("set").equals("ON");
                }
            }
        } catch (SQLException | ClassNotFoundException ex) { 
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlStatistics = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStatisticsMemberData = new javax.swing.JTable();
        btnStatisticsReturned = new javax.swing.JButton();
        txtStatisticsSearch = new javax.swing.JTextField();
        btnBookIssuedRefresh = new javax.swing.JButton();
        cbStatistics = new javax.swing.JComboBox<>();
        statisticsMemberCount = new javax.swing.JLabel();
        statisticsBookCount = new javax.swing.JLabel();
        btnStatisticsPrint = new javax.swing.JButton();
        btnStatisticsExport = new javax.swing.JButton();
        btnStatisticsFine = new javax.swing.JButton();
        pnlStudent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudentsStudentData = new javax.swing.JTable();
        txtStudentsSearch = new javax.swing.JTextField();
        btnStudentsAddStudent = new javax.swing.JButton();
        btnStudentsDelete = new javax.swing.JButton();
        btnStudentsEdit = new javax.swing.JButton();
        btnStudentsRefresh = new javax.swing.JButton();
        btnStudentIssueBook = new javax.swing.JButton();
        lblStudentsPicture = new javax.swing.JLabel();
        lblStudentCount = new javax.swing.JLabel();
        btnStudentsClearance = new javax.swing.JButton();
        lblStudentCountHavingIssuedBooks = new javax.swing.JLabel();
        btnStudentsExport = new javax.swing.JButton();
        btnStudentsExport1 = new javax.swing.JButton();
        btnStudentsEmailAccountInfo = new javax.swing.JButton();
        pnlBook = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBooksBookData = new javax.swing.JTable();
        txtBooksSearch = new javax.swing.JTextField();
        btnBooksAddBook = new javax.swing.JButton();
        btnBooksDelete = new javax.swing.JButton();
        btnBooksEdit = new javax.swing.JButton();
        btnBooksRefresh = new javax.swing.JButton();
        btnBooksIssueBook = new javax.swing.JButton();
        lblBookCount = new javax.swing.JLabel();
        lblBookIssuedCount = new javax.swing.JLabel();
        btnBooksExport = new javax.swing.JButton();
        lblBookFrontImage = new javax.swing.JLabel();
        lblBookBackImage = new javax.swing.JLabel();
        pnlStudentGroup = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblStudentGroupsData = new javax.swing.JTable();
        btnStudentGroupsRemove = new javax.swing.JButton();
        btnStudentGroupsDelete = new javax.swing.JButton();
        cbStudentGroupsNames = new javax.swing.JComboBox<>();
        btnStudentGroupsRefresh = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblStudentGroupsIssuedBookData = new javax.swing.JTable();
        btnStudentGroupsRename = new javax.swing.JButton();
        btnStudentGroupsClearance = new javax.swing.JButton();
        btnStudentGroupsReturnBook = new javax.swing.JButton();
        pnlBookGroup = new javax.swing.JPanel();
        cbBookGroupsNames = new javax.swing.JComboBox<>();
        btnBookGroupsRemove = new javax.swing.JButton();
        btnBookGroupDelete = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBookGroupsData = new javax.swing.JTable();
        btnBookGroupsRefresh = new javax.swing.JButton();
        lblBookGroupsIssueInformation = new javax.swing.JLabel();
        btnBookGroupsRename = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblStudentStudentCount = new javax.swing.JLabel();
        lblStudentCourseCount = new javax.swing.JLabel();
        lblAcademyName = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuItemSettings = new javax.swing.JMenuItem();
        menuItemHistory = new javax.swing.JMenuItem();
        menuItemFileEmails = new javax.swing.JMenuItem();
        menuItemCreateBackup = new javax.swing.JMenuItem();
        menuItemRestoreBackup = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuItemAcademyName = new javax.swing.JMenuItem();
        menuItemEditDeveloperInformation = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuitemBooksIssuedToday = new javax.swing.JMenuItem();
        menuItemBooksReturnedToday = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuItemSearchByDate = new javax.swing.JMenuItem();
        menuItemClearance = new javax.swing.JMenu();
        menuItemClearStudents = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuItemCurrentFines = new javax.swing.JMenuItem();
        menuItemFileHistory = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("All Information (LMS)");

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pnlStatistics.setBackground(new java.awt.Color(204, 204, 204));

        tblStatisticsMemberData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblStatisticsMemberData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Member ID", "Name", "Category", "Book ID", "Title", "Author", "Date Issue", "Date Return"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStatisticsMemberData.getTableHeader().setReorderingAllowed(false);
        tblStatisticsMemberData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStatisticsMemberDataMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblStatisticsMemberData);

        btnStatisticsReturned.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStatisticsReturned.setText("Returned");
        btnStatisticsReturned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsReturnedActionPerformed(evt);
            }
        });

        txtStatisticsSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtStatisticsSearch.setText("Search");
        txtStatisticsSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStatisticsSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStatisticsSearchFocusLost(evt);
            }
        });
        txtStatisticsSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStatisticsSearchKeyReleased(evt);
            }
        });

        btnBookIssuedRefresh.setBackground(new java.awt.Color(204, 204, 204));
        btnBookIssuedRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBookIssuedRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookIssuedRefreshMouseClicked(evt);
            }
        });

        cbStatistics.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbStatistics.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Books", "Today Returns", "Out Of Date" }));
        cbStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatisticsActionPerformed(evt);
            }
        });

        statisticsMemberCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        statisticsMemberCount.setText("Members:");

        statisticsBookCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        statisticsBookCount.setText("Books:");

        btnStatisticsPrint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStatisticsPrint.setText("Print");
        btnStatisticsPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsPrintActionPerformed(evt);
            }
        });

        btnStatisticsExport.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStatisticsExport.setText("Export");
        btnStatisticsExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsExportActionPerformed(evt);
            }
        });

        btnStatisticsFine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStatisticsFine.setText("Fine");
        btnStatisticsFine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsFineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlStatisticsLayout = new javax.swing.GroupLayout(pnlStatistics);
        pnlStatistics.setLayout(pnlStatisticsLayout);
        pnlStatisticsLayout.setHorizontalGroup(
            pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1513, Short.MAX_VALUE)
            .addGroup(pnlStatisticsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStatisticsLayout.createSequentialGroup()
                        .addComponent(statisticsMemberCount, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(statisticsBookCount, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStatisticsFine)
                        .addGap(18, 18, 18)
                        .addComponent(btnStatisticsReturned)
                        .addContainerGap())
                    .addGroup(pnlStatisticsLayout.createSequentialGroup()
                        .addComponent(txtStatisticsSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnBookIssuedRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(cbStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStatisticsExport)
                        .addGap(25, 25, 25)
                        .addComponent(btnStatisticsPrint)
                        .addGap(25, 25, 25))))
        );
        pnlStatisticsLayout.setVerticalGroup(
            pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatisticsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBookIssuedRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatisticsSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStatisticsPrint)
                        .addComponent(btnStatisticsExport)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnStatisticsReturned)
                        .addComponent(btnStatisticsFine))
                    .addGroup(pnlStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(statisticsMemberCount)
                        .addComponent(statisticsBookCount)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Statistics", pnlStatistics);

        pnlStudent.setBackground(new java.awt.Color(204, 204, 204));

        tblStudentsStudentData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblStudentsStudentData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblStudentsStudentData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Father's Name", "Phone", "Email", "Gender", "Address", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudentsStudentData.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblStudentsStudentData.getTableHeader().setReorderingAllowed(false);
        tblStudentsStudentData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentsStudentDataMouseClicked(evt);
            }
        });
        tblStudentsStudentData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStudentsStudentDataKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudentsStudentData);
        if (tblStudentsStudentData.getColumnModel().getColumnCount() > 0) {
            tblStudentsStudentData.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        txtStudentsSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtStudentsSearch.setText("Search");
        txtStudentsSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStudentsSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStudentsSearchFocusLost(evt);
            }
        });
        txtStudentsSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStudentsSearchKeyReleased(evt);
            }
        });

        btnStudentsAddStudent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsAddStudent.setText("Add Member");
        btnStudentsAddStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsAddStudentActionPerformed(evt);
            }
        });

        btnStudentsDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsDelete.setText("Delete");
        btnStudentsDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsDeleteActionPerformed(evt);
            }
        });

        btnStudentsEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsEdit.setText("Edit");
        btnStudentsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsEditActionPerformed(evt);
            }
        });

        btnStudentsRefresh.setBackground(new java.awt.Color(204, 204, 204));
        btnStudentsRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStudentsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsRefreshActionPerformed(evt);
            }
        });

        btnStudentIssueBook.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentIssueBook.setText("Issue Book");
        btnStudentIssueBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentIssueBookActionPerformed(evt);
            }
        });

        lblStudentsPicture.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStudentsPicture.setText("                Picture");
        lblStudentsPicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblStudentsPicture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStudentsPicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStudentsPictureMouseClicked(evt);
            }
        });

        lblStudentCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStudentCount.setText("Members: ");

        btnStudentsClearance.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsClearance.setText("Cleared");
        btnStudentsClearance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsClearanceActionPerformed(evt);
            }
        });

        lblStudentCountHavingIssuedBooks.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStudentCountHavingIssuedBooks.setText("Members Having Issued Books: ");

        btnStudentsExport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsExport.setText("Export");
        btnStudentsExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsExportActionPerformed(evt);
            }
        });

        btnStudentsExport1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsExport1.setText("Email");
        btnStudentsExport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsExport1ActionPerformed(evt);
            }
        });

        btnStudentsEmailAccountInfo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentsEmailAccountInfo.setText("Email Account Info");
        btnStudentsEmailAccountInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsEmailAccountInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentLayout = new javax.swing.GroupLayout(pnlStudent);
        pnlStudent.setLayout(pnlStudentLayout);
        pnlStudentLayout.setHorizontalGroup(
            pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentLayout.createSequentialGroup()
                .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentLayout.createSequentialGroup()
                                .addComponent(txtStudentsSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentsRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStudentIssueBook)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStudentsAddStudent))
                            .addGroup(pnlStudentLayout.createSequentialGroup()
                                .addComponent(btnStudentsDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btnStudentsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btnStudentsClearance)
                                .addGap(13, 13, 13)
                                .addComponent(btnStudentsExport)
                                .addGap(13, 13, 13)
                                .addComponent(btnStudentsEmailAccountInfo)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentsExport1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblStudentCount, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblStudentCountHavingIssuedBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnlStudentLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1319, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStudentsPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlStudentLayout.setVerticalGroup(
            pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStudentsAddStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStudentsRefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStudentsSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStudentIssueBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addGroup(pnlStudentLayout.createSequentialGroup()
                        .addComponent(lblStudentsPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStudentCount)
                        .addComponent(lblStudentCountHavingIssuedBooks)
                        .addComponent(btnStudentsExport1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnStudentsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStudentsDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStudentsClearance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStudentsExport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStudentsEmailAccountInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("Member", pnlStudent);

        pnlBook.setBackground(new java.awt.Color(204, 204, 204));

        tblBooksBookData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBooksBookData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Accession No", "Title", "Author", "Subject", "Publisher", "Edition", "Language", "Price", "Place", "Pages", "Keyword", "ISBN", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBooksBookData.getTableHeader().setReorderingAllowed(false);
        tblBooksBookData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBooksBookDataMouseClicked(evt);
            }
        });
        tblBooksBookData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblBooksBookDataKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblBooksBookData);

        txtBooksSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBooksSearch.setText("Search Book");
        txtBooksSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBooksSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBooksSearchFocusLost(evt);
            }
        });
        txtBooksSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBooksSearchKeyReleased(evt);
            }
        });

        btnBooksAddBook.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBooksAddBook.setText("Add Book");
        btnBooksAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksAddBookActionPerformed(evt);
            }
        });

        btnBooksDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBooksDelete.setText("Delete");
        btnBooksDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksDeleteActionPerformed(evt);
            }
        });

        btnBooksEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBooksEdit.setText("Edit");
        btnBooksEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksEditActionPerformed(evt);
            }
        });

        btnBooksRefresh.setBackground(new java.awt.Color(204, 204, 204));
        btnBooksRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBooksRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksRefreshActionPerformed(evt);
            }
        });

        btnBooksIssueBook.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBooksIssueBook.setText("Issue Book");
        btnBooksIssueBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksIssueBookActionPerformed(evt);
            }
        });

        lblBookCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBookCount.setText("Books: ");

        lblBookIssuedCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBookIssuedCount.setText("Issued Books");

        btnBooksExport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBooksExport.setText("Export");
        btnBooksExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBooksExportActionPerformed(evt);
            }
        });

        lblBookFrontImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblBookFrontImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBookFrontImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBookFrontImageMouseClicked(evt);
            }
        });

        lblBookBackImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblBookBackImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBookBackImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBookBackImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlBookLayout = new javax.swing.GroupLayout(pnlBook);
        pnlBook.setLayout(pnlBookLayout);
        pnlBookLayout.setHorizontalGroup(
            pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1513, Short.MAX_VALUE)
            .addGroup(pnlBookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addComponent(txtBooksSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBooksRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBooksIssueBook, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBooksAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addComponent(btnBooksDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBooksEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBooksExport, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(lblBookCount, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBookIssuedCount, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBookFrontImage, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBookBackImage, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlBookLayout.setVerticalGroup(
            pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtBooksSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlBookLayout.createSequentialGroup()
                            .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnBooksAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBooksIssueBook, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1)))
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnBooksRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBooksDelete)
                            .addComponent(btnBooksEdit)
                            .addComponent(lblBookCount)
                            .addComponent(lblBookIssuedCount)
                            .addComponent(btnBooksExport)))
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBookBackImage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBookFrontImage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Books", pnlBook);

        pnlStudentGroup.setBackground(new java.awt.Color(204, 204, 204));

        tblStudentGroupsData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblStudentGroupsData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudentGroupsData.getTableHeader().setReorderingAllowed(false);
        tblStudentGroupsData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentGroupsDataMouseClicked(evt);
            }
        });
        tblStudentGroupsData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStudentGroupsDataKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblStudentGroupsData);
        if (tblStudentGroupsData.getColumnModel().getColumnCount() > 0) {
            tblStudentGroupsData.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnStudentGroupsRemove.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStudentGroupsRemove.setText("Remove");
        btnStudentGroupsRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsRemoveActionPerformed(evt);
            }
        });

        btnStudentGroupsDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStudentGroupsDelete.setText("Delete");
        btnStudentGroupsDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsDeleteActionPerformed(evt);
            }
        });

        cbStudentGroupsNames.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbStudentGroupsNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStudentGroupsNamesActionPerformed(evt);
            }
        });

        btnStudentGroupsRefresh.setBackground(new java.awt.Color(204, 204, 204));
        btnStudentGroupsRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStudentGroupsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsRefreshActionPerformed(evt);
            }
        });

        tblStudentGroupsIssuedBookData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblStudentGroupsIssuedBookData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Issued Book ID", "Title", "Author", "Category"
            }
        ));
        tblStudentGroupsIssuedBookData.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblStudentGroupsIssuedBookData);

        btnStudentGroupsRename.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStudentGroupsRename.setText("Rename");
        btnStudentGroupsRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsRenameActionPerformed(evt);
            }
        });

        btnStudentGroupsClearance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStudentGroupsClearance.setText("Cleared");
        btnStudentGroupsClearance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsClearanceActionPerformed(evt);
            }
        });

        btnStudentGroupsReturnBook.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStudentGroupsReturnBook.setText("Return");
        btnStudentGroupsReturnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentGroupsReturnBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentGroupLayout = new javax.swing.GroupLayout(pnlStudentGroup);
        pnlStudentGroup.setLayout(pnlStudentGroupLayout);
        pnlStudentGroupLayout.setHorizontalGroup(
            pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                .addGroup(pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                                .addComponent(cbStudentGroupsNames, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnStudentGroupsRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                                .addComponent(btnStudentGroupsDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentGroupsRemove)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentGroupsRename)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentGroupsClearance)
                                .addGap(18, 18, 18)
                                .addComponent(btnStudentGroupsReturnBook)))
                        .addGap(0, 1030, Short.MAX_VALUE))
                    .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStudentGroupLayout.setVerticalGroup(
            pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnStudentGroupsRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbStudentGroupsNames, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
                    .addGroup(pnlStudentGroupLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane7)))
                .addGap(18, 18, 18)
                .addGroup(pnlStudentGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStudentGroupsRemove)
                    .addComponent(btnStudentGroupsDelete)
                    .addComponent(btnStudentGroupsRename)
                    .addComponent(btnStudentGroupsClearance)
                    .addComponent(btnStudentGroupsReturnBook))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Member Category", pnlStudentGroup);

        pnlBookGroup.setBackground(new java.awt.Color(204, 204, 204));

        cbBookGroupsNames.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbBookGroupsNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBookGroupsNamesActionPerformed(evt);
            }
        });

        btnBookGroupsRemove.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBookGroupsRemove.setText("Remove");
        btnBookGroupsRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookGroupsRemoveActionPerformed(evt);
            }
        });

        btnBookGroupDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBookGroupDelete.setText("Delete");
        btnBookGroupDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookGroupDeleteActionPerformed(evt);
            }
        });

        tblBookGroupsData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBookGroupsData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Acc No", "Title", "Author", "Subject", "Publisher", "Edition", "Language", "ISBN", "Keyword", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookGroupsData.getTableHeader().setReorderingAllowed(false);
        tblBookGroupsData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBookGroupsDataMouseClicked(evt);
            }
        });
        tblBookGroupsData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblBookGroupsDataKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblBookGroupsData);
        if (tblBookGroupsData.getColumnModel().getColumnCount() > 0) {
            tblBookGroupsData.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnBookGroupsRefresh.setBackground(new java.awt.Color(204, 204, 204));
        btnBookGroupsRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBookGroupsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookGroupsRefreshActionPerformed(evt);
            }
        });

        lblBookGroupsIssueInformation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnBookGroupsRename.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBookGroupsRename.setText("Rename");
        btnBookGroupsRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookGroupsRenameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBookGroupLayout = new javax.swing.GroupLayout(pnlBookGroup);
        pnlBookGroup.setLayout(pnlBookGroupLayout);
        pnlBookGroupLayout.setHorizontalGroup(
            pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookGroupLayout.createSequentialGroup()
                .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookGroupLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(btnBookGroupsRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBookGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBookGroupDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnBookGroupsRemove)
                        .addGap(18, 18, 18)
                        .addComponent(btnBookGroupsRename)))
                .addGap(189, 189, 189)
                .addComponent(lblBookGroupsIssueInformation, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBookGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(cbBookGroupsNames, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1313, Short.MAX_VALUE)))
            .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1513, Short.MAX_VALUE))
        );
        pnlBookGroupLayout.setVerticalGroup(
            pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookGroupLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBookGroupsRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 568, Short.MAX_VALUE)
                .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBookGroupsRemove)
                    .addComponent(btnBookGroupDelete)
                    .addComponent(lblBookGroupsIssueInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBookGroupsRename))
                .addGap(20, 20, 20))
            .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBookGroupLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(cbBookGroupsNames, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(614, Short.MAX_VALUE)))
            .addGroup(pnlBookGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBookGroupLayout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(73, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Book Category", pnlBookGroup);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblStudentStudentCount.setBackground(new java.awt.Color(204, 204, 204));
        lblStudentStudentCount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblStudentStudentCount.setText("Members");

        lblStudentCourseCount.setBackground(new java.awt.Color(204, 204, 204));
        lblStudentCourseCount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblStudentCourseCount.setText("Books:");

        lblAcademyName.setBackground(new java.awt.Color(204, 204, 204));
        lblAcademyName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAcademyName.setText("Academy: ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStudentStudentCount, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(lblStudentCourseCount, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAcademyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(337, 337, 337))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentStudentCount)
                    .addComponent(lblStudentCourseCount)
                    .addComponent(lblAcademyName))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem1.setText("Back To Home");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Reservation");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        menuItemSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemSettings.setText("Settings");
        menuItemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSettingsActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSettings);

        menuItemHistory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemHistory.setText("History");
        menuItemHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHistoryActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemHistory);

        menuItemFileEmails.setText("Emails");
        menuItemFileEmails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemFileEmailsActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemFileEmails);

        menuItemCreateBackup.setText("Create Backup");
        menuItemCreateBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCreateBackupActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemCreateBackup);

        menuItemRestoreBackup.setText("Restore Backup");
        menuItemRestoreBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRestoreBackupActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemRestoreBackup);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Edit");

        menuItemAcademyName.setText("Academy Name");
        menuItemAcademyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAcademyNameActionPerformed(evt);
            }
        });
        jMenu4.add(menuItemAcademyName);

        menuItemEditDeveloperInformation.setText("Developer Information");
        menuItemEditDeveloperInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditDeveloperInformationActionPerformed(evt);
            }
        });
        jMenu4.add(menuItemEditDeveloperInformation);

        jMenuBar1.add(jMenu4);

        jMenu2.setText("Report");

        menuitemBooksIssuedToday.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuitemBooksIssuedToday.setText("Issued Today");
        menuitemBooksIssuedToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemBooksIssuedTodayActionPerformed(evt);
            }
        });
        jMenu2.add(menuitemBooksIssuedToday);

        menuItemBooksReturnedToday.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemBooksReturnedToday.setText("Returned Today");
        menuItemBooksReturnedToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBooksReturnedTodayActionPerformed(evt);
            }
        });
        jMenu2.add(menuItemBooksReturnedToday);

        jMenuItem3.setText("Unavailable Books");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        menuItemSearchByDate.setText("Search By Date");
        menuItemSearchByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSearchByDateActionPerformed(evt);
            }
        });
        jMenu2.add(menuItemSearchByDate);

        jMenuBar1.add(jMenu2);

        menuItemClearance.setText("Clearance");

        menuItemClearStudents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemClearStudents.setText("Cleared Members");
        menuItemClearStudents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemClearStudentsActionPerformed(evt);
            }
        });
        menuItemClearance.add(menuItemClearStudents);

        jMenuBar1.add(menuItemClearance);

        jMenu3.setText("Fine");

        menuItemCurrentFines.setText("Current Fines");
        menuItemCurrentFines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCurrentFinesActionPerformed(evt);
            }
        });
        jMenu3.add(menuItemCurrentFines);

        menuItemFileHistory.setText("Fine History");
        menuItemFileHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemFileHistoryActionPerformed(evt);
            }
        });
        jMenu3.add(menuItemFileHistory);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new MainForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnStudentsAddStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsAddStudentActionPerformed
        new Add_Student().setVisible(true);
    }//GEN-LAST:event_btnStudentsAddStudentActionPerformed

    private void btnBooksAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksAddBookActionPerformed
        new Add_Book().setVisible(true);
    }//GEN-LAST:event_btnBooksAddBookActionPerformed

    private void btnStudentsEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsEditActionPerformed
        int row = tblStudentsStudentData.getSelectedRow();
        if (!(row == -1))
            new Edit_Student(tmStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL).toString()).setVisible(true);
        else
            JOptionPane.showMessageDialog(this, "No Student is Selected", "Edit Student", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnStudentsEditActionPerformed

    private boolean areMultipleRowsSelected(JTable tbl) {
        int count = 1;
        for (int i : tbl.getSelectedRows()) {
            if (count > 1) {
                return true;
            }
            count++;
        }
        return false;
    }
    private void btnBooksEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksEditActionPerformed
        if (areMultipleRowsSelected(tblBooksBookData)) {
            String[] id = new String[tblBooksBookData.getSelectedRows().length];
            int index = 0;
            for (int i : tblBooksBookData.getSelectedRows()) {
                id[index] = tmBooksBookData.getValueAt(i, NORMAL).toString();
                index++;
            }
            new Edit_Multiple_Books(id).setVisible(true);
        } else if (!(tblBooksBookData.getSelectionModel().isSelectionEmpty()))
            new Edit_Book(tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString()).setVisible(true);
        else
            JOptionPane.showMessageDialog(this, "No Book is Selected.", "Edit Book", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnBooksEditActionPerformed

    private void menuItemHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHistoryActionPerformed
        new History().setVisible(true);
    }//GEN-LAST:event_menuItemHistoryActionPerformed

    private void btnStudentGroupsRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsRemoveActionPerformed
        try {
            if (tblStudentGroupsData.getSelectionModel().isSelectionEmpty()) {
                throw new NewException("First Select Student(s) to Remove From Category");
            }
            if (JOptionPane.showConfirmDialog(this, "Do You Want to Remove Selected Members From this Category", "Remove Members From Category",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                connection();
                for (int selectedRow : tblStudentGroupsData.getSelectedRows()) {
                    ps = con.prepareStatement("UPDATE LMS.STUDENT_DATA\n"
                            + "SET `group` = 'None'\n"
                            + "WHERE `id` = '" + tmStudentGroupsData.getValueAt(selectedRow, NORMAL) + "'");
                    ps.execute();
                }
                JOptionPane.showMessageDialog(this, "Successfully Removed the Selected Members Of this Category.", "Remove Members From Category",
                        JOptionPane.INFORMATION_MESSAGE);
                setcbStudentGroupsNames();
            }
        } catch (SQLException | ClassNotFoundException | NewException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStudentGroupsRemoveActionPerformed

    private void btnStudentGroupsDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsDeleteActionPerformed
        String table = cbStudentGroupsNames.getSelectedItem().toString();
        try {
            if (JOptionPane.showConfirmDialog(this, "Do You Want to Delete \"" + table + "\" Category", "Delete Category",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                connection();
                ps = con.prepareStatement("UPDATE STUDENT_DATA\n"
                        + "SET `group` = 'None'\n"
                        + "WHERE `group` = '" + table + "'");
                if (!ps.execute()) {
                    JOptionPane.showMessageDialog(this, "Group \"" + table + "\" Has Been Deleted Successfully.", "Delete Group", JOptionPane.INFORMATION_MESSAGE);
                    setcbStudentGroupsNames();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Delete Group \"" + table + "\"", "Delete Group", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStudentGroupsDeleteActionPerformed

    private void btnBookGroupsRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookGroupsRemoveActionPerformed
        try {
            if (tblBookGroupsData.getSelectionModel().isSelectionEmpty()) {
                throw new NewException("First Select Book(s) to Remove From Category");
            }
            if (JOptionPane.showConfirmDialog(this, "Do You Want to Remove Selected Members From this Category", "Remove Members From Category",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                connection();
                for (int selectedRow : tblBookGroupsData.getSelectedRows()) {
                    ps = con.prepareStatement("UPDATE LMS.Book\n"
                            + "SET `group` = 'None'\n"
                            + "WHERE `id` = '" + tmBookGroupsData.getValueAt(selectedRow, NORMAL) + "'");
                    ps.execute();
                }
                JOptionPane.showMessageDialog(this, "Successfully Removed the Selected Members Of this Category.", "Remove Members From Category",
                        JOptionPane.INFORMATION_MESSAGE);
                setcbBookGroupsNames();
            }
        } catch (SQLException | ClassNotFoundException | NewException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBookGroupsRemoveActionPerformed

    private void btnBookGroupDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookGroupDeleteActionPerformed
        String table = cbBookGroupsNames.getSelectedItem().toString();
        try {
            if (JOptionPane.showConfirmDialog(this, "Do You Want to Delete \"" + table + "\" Category", "Delete Category",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                connection();
                ps = con.prepareStatement("UPDATE LMS.Book\n"
                        + "SET `group` = 'None'\n"
                        + "WHERE `group` = '" + table + "'");
                if (!ps.execute()) {
                    JOptionPane.showMessageDialog(this, "Category \"" + table + "\" Has Been Deleted Successfully.", "Delete Category", JOptionPane.INFORMATION_MESSAGE);
                    setcbBookGroupsNames();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Delete Category \"" + table + "\"", "Delete Category", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBookGroupDeleteActionPerformed

    private void btnStudentIssueBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentIssueBookActionPerformed
        int row = tblStudentsStudentData.getSelectedRow();
        if (!(row == -1)) {
            new IssueBook(tmStudentsStudentData.getValueAt(row, 0).toString()).setVisible(true);
        } else {
            new IssueBook().setVisible(true);
        }

    }//GEN-LAST:event_btnStudentIssueBookActionPerformed

    private void btnBooksIssueBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksIssueBookActionPerformed
        if (tblBooksBookData.getSelectionModel().isSelectionEmpty())
            new IssueBook().setVisible(true);
        else
            new IssueBook(tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString(), 3).setVisible(true);
    }//GEN-LAST:event_btnBooksIssueBookActionPerformed

    private void txtStatisticsSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStatisticsSearchKeyReleased
        if (cbStatistics.getSelectedItem().toString().equals("All Books"))
            searchAll();
        else if (cbStatistics.getSelectedItem().toString().equals("Out Of Date"))
            searchOutOfDate();
        else if (cbStatistics.getSelectedItem().toString().equals("Today Returns"))
            searchTodayReturns();
    }//GEN-LAST:event_txtStatisticsSearchKeyReleased

    private void cbStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatisticsActionPerformed
        if (cbStatistics.getSelectedItem().toString().equals("All Books"))
            searchAll();
        else if (cbStatistics.getSelectedItem().toString().equals("Out Of Date"))
            searchOutOfDate();
        else if (cbStatistics.getSelectedItem().toString().equals("Today Returns"))
            searchTodayReturns();
    }//GEN-LAST:event_cbStatisticsActionPerformed

    private void txtStatisticsSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStatisticsSearchFocusGained
        txtStatisticsSearch.setText(MainForm.focusGainedLogic(txtStatisticsSearch.getText(), "Search"));
    }//GEN-LAST:event_txtStatisticsSearchFocusGained

    private void txtStatisticsSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStatisticsSearchFocusLost
        txtStatisticsSearch.setText(MainForm.focusLostLogic(txtStatisticsSearch.getText(), "Search"));
    }//GEN-LAST:event_txtStatisticsSearchFocusLost

    public void showReservationMessage(String bookId, String title) {
        try {
            String reserved = new ReturnBooks().getBookStatus(bookId);
            if (reserved.subSequence(0, 8).equals("Reserved")) {
                JOptionPane.showMessageDialog(this, title + " Book was Reserved By: '"
                        + reserved.substring(reserved.indexOf('\'') + 1, reserved.lastIndexOf('\'')) + "'");
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void returnBook(){
        if (JOptionPane.showConfirmDialog(this, "Do you want to return Selected Book ?", "Return Book", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION){
            DefaultTableModel tm = (DefaultTableModel) tblStatisticsMemberData.getModel();
            int row = tblStatisticsMemberData.getSelectedRow();
            String memberId = tm.getValueAt(row, 0).toString();
            String bookId = tm.getValueAt(row, 3).toString();
            
//Checking if student has late returned the book so that he can be fined for late days
//according to the spcified price per day or once at general settings...
            try{if (new Fine.FineFunctions().isFinedForLateReturn(memberId, bookId)){
                new Fine.ChargeLateReturnedBookFine(memberId, bookId).setVisible(true);
                return;
            }}
            catch(SQLException | ClassNotFoundException e){}
            
            if (new ReturnBooks().returnBook(memberId, bookId)) {

                JOptionPane.showMessageDialog(this, tm.getValueAt(row, 4) + " Book Returned", "Return Book", JOptionPane.INFORMATION_MESSAGE);

                //Checking if this book is Reserved. if yes than showing the message..
                showReservationMessage(tm.getValueAt(row, 3).toString(), tm.getValueAt(row, 4).toString());
                //Checking end...
                if (cbStatistics.getSelectedItem().toString().equals("All Books")) {
                    searchAll();
                } else if (cbStatistics.getSelectedItem().toString().equals("Out Of Date")) {
                    searchOutOfDate();
                } else if (cbStatistics.getSelectedItem().toString().equals("Today Returns")) {
                    searchTodayReturns();
                }

            } else
                JOptionPane.showMessageDialog(this, "Couldn't Return the Book\n", "Return Book", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void btnStatisticsReturnedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsReturnedActionPerformed
        returnBook();
    }//GEN-LAST:event_btnStatisticsReturnedActionPerformed

    private void btnBookIssuedRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookIssuedRefreshMouseClicked
        txtStatisticsSearch.setText("Search");
        if (cbStatistics.getSelectedItem().toString().equals("All Books")) {
            searchAll();
        } else if (cbStatistics.getSelectedItem().toString().equals("Out Of Date")) {
            searchOutOfDate();
        } else if (cbStatistics.getSelectedItem().toString().equals("Today Returns")) {
            searchTodayReturns();
        }
        studentCountLabel();
    }//GEN-LAST:event_btnBookIssuedRefreshMouseClicked

    public void txtStudentsSearchLogic() {
        try {
            connection();
            int dataInTable = 0;
            String search = txtStudentsSearch.getText();
            tmStudentsStudentData.setRowCount(0);
            {//Setting Picture null..
                lblStudentsPicture.setIcon(null);
                lblStudentsPicture.setText("                Picture");
            }
            if (search.equals("Search") || search.equals("")) {
                settblStudentsStudentData();
            } else {
                ps = con.prepareStatement("SELECT * FROM student_data\n"
                        + "WHERE (id REGEXP '" + search + "' OR \n"
                        + "first_name REGEXP '" + search + "' OR \n"
                        + "last_Name REGEXP '" + search + "' OR \n"
                        + "father_name REGEXP '" + search + "' OR \n"
                        + "sex REGEXP '" + search + "' OR \n"
                        + "phone REGEXP '" + search + "' OR \n"
                        + "`group` REGEXP '" + search + "' OR \n"
                        + "`Email` REGEXP '" + search + "' OR \n"
                        + "address REGEXP '" + search + "') AND `STATUS` != 'Cleared'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("id"));
                    v.add(rs.getString("first_name"));
                    v.add(rs.getString("last_name"));
                    v.add(rs.getString("father_name"));
                    v.add(rs.getString("phone"));
                    v.add(rs.getString("Email"));
                    v.add(rs.getString("sex"));
                    v.add(rs.getString("address"));
                    v.add(rs.getString("group"));
                    tmStudentsStudentData.addRow(v);
                    dataInTable++;
                }
                lblStudentCount.setText("Members: " + dataInTable);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void txtStudentsSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentsSearchKeyReleased
        txtStudentsSearchLogic();
    }//GEN-LAST:event_txtStudentsSearchKeyReleased

    private void txtStudentsSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentsSearchFocusGained
        txtStudentsSearch.setText(focusGainedLogic(txtStudentsSearch.getText(), "Search"));
    }//GEN-LAST:event_txtStudentsSearchFocusGained

    private void txtStudentsSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentsSearchFocusLost
        txtStudentsSearch.setText(focusLostLogic(txtStudentsSearch.getText(), "Search"));
    }//GEN-LAST:event_txtStudentsSearchFocusLost

    private void tblStudentsStudentDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentsStudentDataKeyReleased
        setStudentPicture();
    }//GEN-LAST:event_tblStudentsStudentDataKeyReleased

    private void tblStudentsStudentDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentsStudentDataMouseClicked
        setStudentPicture();
        if (doubleClick.detectDoubleClick()) {
//            This method receives student id as parameter...
            new FramesJustForShowingInformation.StudentInformation(
                    (String) tblStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL))
                    .setVisible(true);
        }
    }//GEN-LAST:event_tblStudentsStudentDataMouseClicked

    private void btnStudentsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsRefreshActionPerformed
        txtStudentsSearch.setText("Search");
        settblStudentsStudentData();
        lblStudentsPicture.setIcon(null);
        lblStudentsPicture.setText("                Picture");
    }//GEN-LAST:event_btnStudentsRefreshActionPerformed

    public boolean deleteStudent(String id) {
        boolean check = false;
        try {
            connection();
            ps = con.prepareStatement("DELETE FROM student_data\n"
                    + "WHERE id = '" + id + "'");
            check = !ps.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    private void btnStudentsDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsDeleteActionPerformed
        StringBuilder studentIds = new StringBuilder("(");
        try {
            if (!tblStudentsStudentData.getSelectionModel().isSelectionEmpty()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want to Deletet Selected Student(s)", "Delete Student(s)",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

                    for (int selectedRow : tblStudentsStudentData.getSelectedRows()) {
                        String memberId = tmStudentsStudentData.getValueAt(selectedRow, NORMAL).toString();
                        
                        boolean isBookIssued, isFineRemaning;
                        
                        ps = con.prepareStatement("SELECT student_id FROM issue_book \n" +
                                "WHERE student_id = '"+memberId+"'");
                        ResultSet rs = ps.executeQuery();
                        isBookIssued = rs.next();
                        
                        ps = con.prepareStatement("SELECT id FROM fine \n" +
                                "WHERE student_id = '"+memberId+"' AND `status` = 'Unpaid'");
                        ResultSet rs2 = ps.executeQuery();
                        isFineRemaning = rs2.next();
                        
                        if (isBookIssued && isFineRemaning){
                            JOptionPane.showMessageDialog(this, "\"" + tmStudentsStudentData.getValueAt(selectedRow, 1) + " " + tmStudentsStudentData.getValueAt(selectedRow, 2)
                                    + "\" Can't be Deleted Because He\\She has Issued Book(s) And Fines Remaning to Pay.", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                            continue;
                        }
                        else if (isBookIssued){
                            JOptionPane.showMessageDialog(this, "\"" + tmStudentsStudentData.getValueAt(selectedRow, 1) + " " + tmStudentsStudentData.getValueAt(selectedRow, 2)
                                    + "\" Can't be Deleted Because He\\She has Issued Book(s)", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                            continue;
                        }
                        else if (isFineRemaning){
                            JOptionPane.showMessageDialog(this, "\"" + tmStudentsStudentData.getValueAt(selectedRow, 1) + " " + tmStudentsStudentData.getValueAt(selectedRow, 2)
                                    + "\" Can't be Deleted Because He\\She has Fine Remaning to Pay(s)", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                            continue;
                        }
                        
                        studentIds.append(tmStudentsStudentData.getValueAt(selectedRow, NORMAL).toString()).append(", ");

                    }

                    studentIds.deleteCharAt(studentIds.length() - 2);

                    studentIds.append(")");

                }

                PreparedStatement ps = con.prepareStatement("DELETE FROM student_data WHERE ID IN " + studentIds);

                if (!ps.execute()) {
                    JOptionPane.showMessageDialog(this, "Selected Student(s) has been Deleted.", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                    lblStudentsPicture.setIcon(null);
                    txtStudentsSearchLogic();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No Any Student is Selected to Delete.", "Delete Student", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnStudentsDeleteActionPerformed

    private void txtBooksSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBooksSearchFocusGained
        txtBooksSearch.setText(focusGainedLogic(txtBooksSearch.getText(), "Search Book"));
    }//GEN-LAST:event_txtBooksSearchFocusGained

    private void txtBooksSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBooksSearchFocusLost
        txtBooksSearch.setText(focusLostLogic(txtBooksSearch.getText(), "Search Book"));
    }//GEN-LAST:event_txtBooksSearchFocusLost

    public void txtBooksSearchLogic() {
        try {
            connection();
            String search = txtBooksSearch.getText();
            int i = 0;
            tmBooksBookData.setRowCount(0);
            if (search.equals("Search Book") || search.equals("")) {
                setBooksBookData();
            } else {
                ps = con.prepareStatement("SELECT * FROM lms.book\n"
                        + "WHERE id REGEXP '" + search + "' OR\n"
                        + "`title` REGEXP '" + search + "' OR\n"
                        + "`Author` REGEXP '" + search + "' OR\n"
                        + "`Subject` REGEXP '" + search + "' OR\n"
                        + "Publisher REGEXP '" + search + "' OR\n"
                        + "Edition REGEXP '" + search + "' OR\n"
                        + "Language REGEXP '" + search + "' OR\n"
                        + "Place REGEXP '" + search + "' OR\n"
                        + "Pages REGEXP '" + search + "' OR\n"
                        + "Keyword REGEXP '" + search + "' OR\n"
                        + "ISBN REGEXP '" + search + "' OR\n"
                        + "status REGEXP '" + search + "' OR\n"
                        + "Price REGEXP '" + search + "' OR\n"
                        + "Currency REGEXP '" + search + "'");

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("id"));
                    v.add(rs.getString("title"));
                    v.add(rs.getString("Author"));
                    v.add(rs.getString("Subject"));
                    v.add(rs.getString("Publisher"));
                    v.add(change(rs.getString("Edition")));
                    v.add(change(rs.getString("Language")));
                    v.add(changePrice(rs.getString("Price") + " " + rs.getString("Currency")));
                    v.add(rs.getString("place"));
                    v.add(change(rs.getString("pages")));
                    v.add(rs.getString("keyword"));
                    v.add(rs.getString("ISBN"));
                    v.add(rs.getString("status"));
                    tmBooksBookData.addRow(v);
                    i++;
                }
                lblBookCount.setText("Books: " + i);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void txtBooksSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBooksSearchKeyReleased
        txtBooksSearchLogic();
    }//GEN-LAST:event_txtBooksSearchKeyReleased

    private void btnBooksRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksRefreshActionPerformed
        txtBooksSearch.setText("Search Book");
        lblBookFrontImage.setIcon(null);
        lblBookBackImage.setIcon(null);
        setBooksBookData();
    }//GEN-LAST:event_btnBooksRefreshActionPerformed

    private void btnBooksDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksDeleteActionPerformed
        StringBuilder bookIds = new StringBuilder();
        try {
            if (!tblBooksBookData.getSelectionModel().isSelectionEmpty()) {

                if (JOptionPane.showConfirmDialog(this, "Do You Want to Delete Selected Book(s)", "Delete Book",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    bookIds.append("(");
                    for (int selectedRow : tblBooksBookData.getSelectedRows()) {
                        ps = con.prepareStatement("SELECT book_id FROM lms.issue_book WHERE book_id = '"
                                + tmBooksBookData.getValueAt(selectedRow, NORMAL) + "'");
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(this, "Book \"" + tmBooksBookData.getValueAt(selectedRow, 1)
                                    + "\" Can't be Deleted Because It is Issued to Student(s)", "Delete Book", JOptionPane.INFORMATION_MESSAGE);
                            continue;
                        }

                        bookIds.append(tmBooksBookData.getValueAt(selectedRow, NORMAL).toString()).append(", ");

                    }

                    bookIds.deleteCharAt(bookIds.length() - 2);

                    bookIds.append(")");

                    PreparedStatement ps = con.prepareStatement("DELETE FROM book WHERE ID IN " + bookIds);

                    if (!ps.execute()) {
                        JOptionPane.showMessageDialog(this, "Selected Books Are Deleted.", "Delete Book",
                                JOptionPane.INFORMATION_MESSAGE);
                        txtBooksSearchLogic();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select Any Book To Delete", "Delete Book", JOptionPane.ERROR_MESSAGE);
            }

        } catch (java.sql.SQLSyntaxErrorException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Delete Book", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnBooksDeleteActionPerformed


    private void btnStudentGroupsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsRefreshActionPerformed
        setcbStudentGroupsNames();
        cbStudentGroupNamesLogic();
    }//GEN-LAST:event_btnStudentGroupsRefreshActionPerformed

    private void cbStudentGroupNamesLogic() {
        try {
            tmStudentGroupsData.setRowCount(0);
            connection();
            ps = con.prepareStatement("SELECT id, first_name, last_name, phone FROM student_data\n"
                    + "WHERE `group` = '" + cbStudentGroupsNames.getSelectedItem() + "' AND `STATUS` != 'Cleared'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("First_Name"));
                v.add(rs.getString("last_name"));
                v.add(rs.getString("phone"));
                tmStudentGroupsData.addRow(v);
            }
        } catch (NullPointerException e) {
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cbStudentGroupsNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStudentGroupsNamesActionPerformed
        cbStudentGroupNamesLogic();
        tmtblStudentGroupsIssuedBookData.setRowCount(0);
    }//GEN-LAST:event_cbStudentGroupsNamesActionPerformed

    private void cbBookGroupNameActionPerformedLogic() {
        try {
            tmBookGroupsData.setRowCount(0);
            connection();
            ps = con.prepareStatement("SELECT id, title, author, subject, publisher, edition, language, isbn, keyword, status FROM lms.book\n"
                    + "WHERE `group` = '" + cbBookGroupsNames.getSelectedItem() + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("title"));
                v.add(rs.getString("author"));
                v.add(rs.getString("subject"));
                v.add(rs.getString("publisher"));
                v.add(rs.getString("edition"));
                v.add(rs.getString("language"));
                v.add(rs.getString("isbn"));
                v.add(rs.getString("keyword"));
                v.add(rs.getString("status"));
                tmBookGroupsData.addRow(v);
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cbBookGroupsNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBookGroupsNamesActionPerformed
        cbBookGroupNameActionPerformedLogic();
        lblBookGroupsIssueInformation.setText(null);
    }//GEN-LAST:event_cbBookGroupsNamesActionPerformed

    private void btnBookGroupsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookGroupsRefreshActionPerformed
        setcbBookGroupsNames();
    }//GEN-LAST:event_btnBookGroupsRefreshActionPerformed

    private void menuItemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSettingsActionPerformed
        new Settings().setVisible(true);
    }//GEN-LAST:event_menuItemSettingsActionPerformed

    private void tblStudentGroupsDataKeyReleasedLogic() {
        try {
            tmtblStudentGroupsIssuedBookData.setRowCount(0);
            connection();
            ps = con.prepareStatement("SELECT book_id, title, author, `group` FROM lms.issue_book ib\n"
                    + "JOIN lms.book b ON ib.book_id = b.id\n"
                    + "WHERE student_id = '" + tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(), NORMAL) + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("book_id"));
                v.add(rs.getString("title"));
                v.add(rs.getString("author"));
                v.add(rs.getString("group"));
                tmtblStudentGroupsIssuedBookData.addRow(v);
            }
        } catch (NullPointerException e) {
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void tblStudentGroupsDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentGroupsDataKeyReleased
        tblStudentGroupsDataKeyReleasedLogic();
    }//GEN-LAST:event_tblStudentGroupsDataKeyReleased

    private void tblStudentGroupsDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentGroupsDataMouseClicked
        tblStudentGroupsDataKeyReleasedLogic();
        if (doubleClick.detectDoubleClick()){
            new FramesJustForShowingInformation.StudentInformation((String) tblStudentGroupsData.getValueAt(tblStudentGroupsData.getSelectedRow(), NORMAL)).setVisible(true);
        }
    }//GEN-LAST:event_tblStudentGroupsDataMouseClicked

    private void tblBookGroupsDataKeyReleased() {
        try {
            connection();
            lblBookGroupsIssueInformation.setText(null);
            ps = con.prepareStatement("SELECT id, first_name, last_name, `group` FROM lms.issue_book ib\n"
                    + "JOIN student_data sd ON sd.id = ib.student_id\n"
                    + "WHERE book_id = '" + tblBookGroupsData.getValueAt(tblBookGroupsData.getSelectedRow(), NORMAL) + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lblBookGroupsIssueInformation.setText("Issued To: " + rs.getString("first_name") + " " + rs.getString("last_name") + "  ID: "
                        + rs.getString("id") + "  Category: " + rs.getString("group"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void tblBookGroupsDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBookGroupsDataKeyReleased
        tblBookGroupsDataKeyReleased();
    }//GEN-LAST:event_tblBookGroupsDataKeyReleased

    private void tblBookGroupsDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookGroupsDataMouseClicked
        tblBookGroupsDataKeyReleased();
        if (doubleClick.detectDoubleClick()){
            new FramesJustForShowingInformation.BookInformation((String) tblBookGroupsData.getValueAt(tblBookGroupsData.getSelectedRow(), NORMAL)).setVisible(true);
        }
    }//GEN-LAST:event_tblBookGroupsDataMouseClicked

    private void menuitemBooksIssuedTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemBooksIssuedTodayActionPerformed
        new BooksIssuedToday().setVisible(true);
    }//GEN-LAST:event_menuitemBooksIssuedTodayActionPerformed

    private void menuItemBooksReturnedTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBooksReturnedTodayActionPerformed
        new BooksReturnedToday().setVisible(true);
    }//GEN-LAST:event_menuItemBooksReturnedTodayActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new ReservationOfBook().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnStudentGroupsRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsRenameActionPerformed
        String result = JOptionPane.showInputDialog(this, "Enter New Name", "Rename Category", JOptionPane.OK_CANCEL_OPTION);
        try {
            if (!result.equals("None")) {
                String table = cbStudentGroupsNames.getSelectedItem().toString();
                connection();
                ps = con.prepareStatement("UPDATE LMS.STUDENT_DATA\n"
                        + "SET `group` = '" + result + "'\n"
                        + "WHERE `group` = '" + table + "'");
                if (!ps.execute()) {
                    JOptionPane.showMessageDialog(this, "Category Has Been Renamed Successfully.", "Rename Group", JOptionPane.INFORMATION_MESSAGE);
                    setcbStudentGroupsNames();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Rename Category \"" + table + "\"", "Rename Category", JOptionPane.ERROR_MESSAGE);
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NullPointerException e) {
        }

    }//GEN-LAST:event_btnStudentGroupsRenameActionPerformed

    private void btnBookGroupsRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookGroupsRenameActionPerformed
        String result = JOptionPane.showInputDialog(this, "Enter New Name", "Rename Category", JOptionPane.OK_CANCEL_OPTION);
        try {
            if (!result.equals("None")) {
                String table = cbBookGroupsNames.getSelectedItem().toString();
                connection();
                ps = con.prepareStatement("UPDATE LMS.Book\n"
                        + "SET `group` = '" + result + "'\n"
                        + "WHERE `group` = '" + table + "'");
                if (!ps.execute()) {
                    JOptionPane.showMessageDialog(this, "Category Has Been Renamed Successfully.", "Rename Category", JOptionPane.INFORMATION_MESSAGE);
                    setcbBookGroupsNames();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Rename Category \"" + table + "\"", "Rename Category", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_btnBookGroupsRenameActionPerformed

    private boolean clearStudents(JTable table) throws SQLException, ClassNotFoundException, NewException {
        DefaultTableModel tm = (DefaultTableModel) table.getModel();
        if (table.getSelectionModel().isSelectionEmpty()) {
            throw new NewException("Nothing is Selected");
        }
        boolean check = false;
        connection();
        if (JOptionPane.showConfirmDialog(this, "Do You Want to Clear Selected Student(s)?", "Clearance", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            for (int selectedRow : table.getSelectedRows()) {
                String studentId = (String) tm.getValueAt(selectedRow, NORMAL);
                String studentName = (String) tm.getValueAt(selectedRow, 1);
                //Checking if student has some books issued...
                ps = con.prepareStatement("SELECT BOOK_ID FROM ISSUE_BOOK WHERE STUDENT_ID = '" + tm.getValueAt(selectedRow, NORMAL) + "'");
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    int fine = new Fine.FineFunctions().sumOfRemaningFine(studentId);
                    if (fine == 0) {
                        //Inserting student into cleared table so taht it can be clared...
                        ps = con.prepareStatement("UPDATE STUDENT_DATA\n" +
                            "SET `STATUS` = 'Cleared',\n" +
                            "`CLEARED_DATE` = CURRENT_DATE()\n" +
                            "WHERE ID = '"+studentId+"'");
                        check = !ps.execute();
                        
                    } else {
                        String gender = new Fine.FineFunctions().getGender(studentId);
                        JOptionPane.showMessageDialog(this, gender + " " + studentName + " Can't be Cleared\nBecause of total " + fine + " in amount of Remaning Fine.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, tm.getValueAt(selectedRow, 1) + " Can't be Cleared Because He/She Has Some Books Issued");
                }
            }
        }
        return check;
    }
    private void btnStudentsClearanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsClearanceActionPerformed
        try {

            if (clearStudents(tblStudentsStudentData)) {
                JOptionPane.showMessageDialog(this, "Selected Students Has Been Cleared", "Clearance", JOptionPane.INFORMATION_MESSAGE);
                txtStudentsSearchLogic();
            }
        } catch (NewException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Selection Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStudentsClearanceActionPerformed

    private void btnStudentGroupsClearanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsClearanceActionPerformed
        try {
            if (clearStudents(tblStudentGroupsData)) {
                JOptionPane.showMessageDialog(this, "Selected Student(s) has been Cleared", "Clearance", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NewException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Sorry! Can't Clear Student Because of Any Error. " + ex.getMessage());
            //Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStudentGroupsClearanceActionPerformed

    private void menuItemClearStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemClearStudentsActionPerformed
        new ClearedStudents().setVisible(true);
    }//GEN-LAST:event_menuItemClearStudentsActionPerformed

    private void menuItemAcademyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAcademyNameActionPerformed
        try {
            connection();
            String academy = JOptionPane.showInputDialog(null, "Enter Academy Name", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
            ps = con.prepareStatement("SELECT academy FROM academy");
            ResultSet rs = ps.executeQuery();
            if (academy == null) {
            } else if (rs.next()) {
                ps = con.prepareStatement("UPDATE `academy`\n"
                        + "SET academy = '" + academy + "'\n"
                        + "WHERE id = 1");
                ps.execute();
                lblAcademyName.setText("Academy: " + academy);
            } else {
                ps = con.prepareStatement("INSERT INTO `academy`(`academy`)\n"
                        + "VALUES ('" + academy + "')");
                ps.execute();
                lblAcademyName.setText("Academy: " + academy);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuItemAcademyNameActionPerformed

    private void menuItemEditDeveloperInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditDeveloperInformationActionPerformed
        new Developers_Information().setVisible(true);
    }//GEN-LAST:event_menuItemEditDeveloperInformationActionPerformed

    private void btnStatisticsPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsPrintActionPerformed
        try {
            java.text.MessageFormat h = new java.text.MessageFormat("Issued Books");
            tblStatisticsMemberData.print(JTable.PrintMode.NORMAL, null, h);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(BooksIssuedToday.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStatisticsPrintActionPerformed

    private void btnStatisticsExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsExportActionPerformed
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FileDialog file = new FileDialog(this, "Choose Path", FileDialog.SAVE);
            file.setFile("Issued Books");
            file.setVisible(true);
            String location = file.getDirectory() + file.getFile() + " " + df.format(new Date()) + ".xls";
            if (location.substring(0, 4).equals("null")) {
                throw new NewException("");
            }
            File f = new File(location);
            if (new All_Information().exportTable(tblStatisticsMemberData, f, true)) {
                JOptionPane.showMessageDialog(this, "Sucessfully Created Your File.");
            } else {
                JOptionPane.showConfirmDialog(this, "Sorry! File Could Not Be Created.");
            }
        } catch (NewException e) {
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(BooksReturnedToday.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStatisticsExportActionPerformed

    private void btnStudentsExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsExportActionPerformed
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FileDialog file = new FileDialog(this, "Choose Path", FileDialog.SAVE);
            file.setFile("All Students");
            file.setVisible(true);
            String location = file.getDirectory() + file.getFile() + " " + df.format(new Date()) + ".xls";
            if (location.substring(0, 4).equals("null")) {
                throw new NewException("");
            }
            File f = new File(location);
            if (new All_Information().exportTable(tblStudentsStudentData, f)) {
                JOptionPane.showMessageDialog(this, "Successfully Created Your File.");
            } else {
                JOptionPane.showConfirmDialog(this, "Sorry! File Could Not Be Created.");
            }
        } catch (NewException e) {
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(BooksReturnedToday.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStudentsExportActionPerformed

    private void btnBooksExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBooksExportActionPerformed
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FileDialog file = new FileDialog(this, "Choose Path", FileDialog.SAVE);
            file.setFile("All Books");
            file.setVisible(true);
            String location = file.getDirectory() + file.getFile() + " " + df.format(new Date()) + ".xls";
            if (location.substring(0, 4).equals("null")) {
                throw new NewException("");
            }
            File f = new File(location);
            if (new All_Information().exportTable(tblBooksBookData, f)) {
                JOptionPane.showMessageDialog(this, "Successfully Created Your File.");
            } else {
                JOptionPane.showConfirmDialog(this, "Sorry! File Could Not Be Created.");
            }
        } catch (NewException e) {
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            //Logger.getLogger(BooksReturnedToday.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBooksExportActionPerformed

    private void menuItemSearchByDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSearchByDateActionPerformed
        new SearchBooksByDate().setVisible(true);
    }//GEN-LAST:event_menuItemSearchByDateActionPerformed

    private void setBooksPictures(String bookId, JLabel lblFrontImage, JLabel lblBackImage) throws SQLException, ClassNotFoundException, NullPointerException {
        connection();
        ps = con.prepareStatement("SELECT frontImage as f, backImage as b FROM book WHERE ID = '" + bookId + "'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            byte[] frontImage = rs.getBytes("f");
            byte[] backImage = rs.getBytes("b");

            lblFrontImage.setIcon(MainForm.setImage(frontImage, lblFrontImage.getWidth(), lblFrontImage.getHeight()));
            lblBackImage.setIcon(MainForm.setImage(backImage, lblBackImage.getWidth(), lblBackImage.getHeight()));
        }
    }
    
    private void tblBooksBookDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBooksBookDataMouseClicked
        try {
            setBooksPictures(tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString(), lblBookFrontImage, lblBookBackImage);
        } catch (NullPointerException e) {
            lblBookFrontImage.setIcon(null);
            lblBookBackImage.setIcon(null);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
        if (doubleClick.detectDoubleClick()){
            new FramesJustForShowingInformation.BookInformation((String)tblBooksBookData.getValueAt(
                    tblBooksBookData.getSelectedRow(), NORMAL)).setVisible(true);
        }
    }//GEN-LAST:event_tblBooksBookDataMouseClicked

    private void tblBooksBookDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBooksBookDataKeyReleased
        try {
            setBooksPictures(tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString(), lblBookFrontImage, lblBookBackImage);
        } catch (NullPointerException e) {
            lblBookFrontImage.setIcon(null);
            lblBookBackImage.setIcon(null);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_tblBooksBookDataKeyReleased

    private void lblBookFrontImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBookFrontImageMouseClicked
        if (!tblBooksBookData.getSelectionModel().isSelectionEmpty())
            new ImageMagnifier(true, tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString()).setVisible(true);
        else
            JOptionPane.showMessageDialog(this, "Nothing is Selected From Table.", "Selection Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_lblBookFrontImageMouseClicked

    private void lblBookBackImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBookBackImageMouseClicked
        if (!tblBooksBookData.getSelectionModel().isSelectionEmpty())
            new ImageMagnifier(false, tmBooksBookData.getValueAt(tblBooksBookData.getSelectedRow(), NORMAL).toString()).setVisible(true);
        else
            JOptionPane.showMessageDialog(this, "Nothing is Selected From Table.", "Selection Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_lblBookBackImageMouseClicked

    private void menuItemFileEmailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemFileEmailsActionPerformed
        new EmailPackage.EmailHistory().setVisible(true);
    }//GEN-LAST:event_menuItemFileEmailsActionPerformed

    private void btnStudentsExport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsExport1ActionPerformed
        customEmail(tblStudentsStudentData);
    }//GEN-LAST:event_btnStudentsExport1ActionPerformed

    private void btnStudentsEmailAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsEmailAccountInfoActionPerformed
        boolean isOperationRunSucessfully = false;
        for (int selectedRows : tblStudentsStudentData.getSelectedRows()) {
            isOperationRunSucessfully = new EmailPackage.AutomaticEmail().sendAccountInfomation(tblStudentsStudentData.getValueAt(selectedRows, NORMAL).toString());
        }
        if (isOperationRunSucessfully)
            JOptionPane.showMessageDialog(this, "Account Information has been Sent Sucessfully to Student(s)", "Account Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnStudentsEmailAccountInfoActionPerformed

    private void btnStudentGroupsReturnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentGroupsReturnBookActionPerformed
        DefaultTableModel tm1 = (DefaultTableModel) tblStudentGroupsData.getModel();
        DefaultTableModel tm2 = (DefaultTableModel) tblStudentGroupsIssuedBookData.getModel();
        int row1 = tblStudentGroupsData.getSelectedRow();
        int row2 = tblStudentGroupsIssuedBookData.getSelectedRow();
        try {
            if (tblStudentGroupsData.getSelectionModel().isSelectionEmpty()) {
                throw new NewException("No Student is Selected.");
            }
            if (tblStudentGroupsIssuedBookData.getSelectionModel().isSelectionEmpty()) {
                throw new NewException("No Book is Selected.");
            }

            if (new ReturnBooks().returnBook(tm1.getValueAt(row1, 0).toString(), tm2.getValueAt(row2, 0).toString())) {

                JOptionPane.showMessageDialog(this, tm2.getValueAt(row2, 0) + " Book Returned", "Return Book", JOptionPane.INFORMATION_MESSAGE);

                //Checking if this book is Reserved. if yes than showing the message..
                showReservationMessage(tm1.getValueAt(row1, 0).toString(), tm2.getValueAt(row2, 0).toString());
                //Checking end...

                //Refreshing Table.
                tblStudentGroupsDataKeyReleasedLogic();

            } else {
                JOptionPane.showMessageDialog(this, "Couldn't Return the Book\n", "Return Book", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NewException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Selectio Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnStudentGroupsReturnBookActionPerformed

    private void btnStatisticsFineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsFineActionPerformed
        if (!tblStatisticsMemberData.getSelectionModel().isSelectionEmpty()) {
            String memberId = tmStatisticsMemberData.getValueAt(tblStatisticsMemberData.getSelectedRow(), NORMAL).toString();
            String bookId = tmStatisticsMemberData.getValueAt(tblStatisticsMemberData.getSelectedRow(), 3).toString();
            new Fine.ChargeFine(memberId, bookId).setVisible(true);
        } else
            JOptionPane.showMessageDialog(this, "No Entry is Selected.", "Seletion Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnStatisticsFineActionPerformed

    private void menuItemFileHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemFileHistoryActionPerformed
        new Fine.PreviousFines().setVisible(true);
    }//GEN-LAST:event_menuItemFileHistoryActionPerformed

    private void menuItemCurrentFinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCurrentFinesActionPerformed
        Fine.CurrentFines fine = new Fine.CurrentFines();
        Fine.CurrentFines.object = fine;
        fine.setVisible(true);
    }//GEN-LAST:event_menuItemCurrentFinesActionPerformed

    private void lblStudentsPictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStudentsPictureMouseClicked
        if (!tblStudentsStudentData.getSelectionModel().isSelectionEmpty())
            new FramesJustForShowingInformation.MemberImageMagnifier((String)tblStudentsStudentData.getValueAt(tblStudentsStudentData.getSelectedRow(), NORMAL)).setVisible(true);
    }//GEN-LAST:event_lblStudentsPictureMouseClicked

    private void tblStatisticsMemberDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStatisticsMemberDataMouseClicked
        if (doubleClick.detectDoubleClick()){
            if (doReturnBookOnDoubleClick){
                returnBook();
            }
        }
    }//GEN-LAST:event_tblStatisticsMemberDataMouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new UnavailableBooks().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void menuItemCreateBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCreateBackupActionPerformed
//        AR_SQL_Backup backup = new AR_SQL_Backup();
//        FileDialog file = new FileDialog((java.awt.Frame)null, "Backup Save", FileDialog.SAVE);
//        file.setFile("LMS");
//        file.setVisible(true);
//        String directory = file.getDirectory();
//        
//        try {
//            backup.setConnection(MainForm.DRIVER, MainForm.URL, MainForm.USER_NAME, MainForm.PASSWORD);
//            backup.backupSchema(MainForm.SCHEMA, directory);
//            JOptionPane.showMessageDialog(this, "Database Has Been Backuped Up", "Backup", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException | ClassNotFoundException | IOException ex) {
//            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_menuItemCreateBackupActionPerformed

    private void menuItemRestoreBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRestoreBackupActionPerformed
//        AR_SQL_Backup backup = new AR_SQL_Backup();
//        FileDialog file = new FileDialog((java.awt.Frame)null, "Backup Save", FileDialog.LOAD);
//        file.setFile("LMS");
//        file.setVisible(true);
//        String directory = file.getDirectory();
//        
//        try {
//            backup.setConnection(MainForm.DRIVER, MainForm.URL, MainForm.USER_NAME, MainForm.PASSWORD);
//            backup.restoreSchema(directory);
//            JOptionPane.showMessageDialog(this, "Database Has Been Restored", "Backup", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException | ClassNotFoundException | IOException ex) {
//            Logger.getLogger(All_Information.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_menuItemRestoreBackupActionPerformed

    private void customEmail(JTable tbl) {
        String[] emails = new String[tbl.getSelectedRowCount()];
        int index = 0;
        boolean isExceptionThrown = false;
        for (int e : tbl.getSelectedRows()) {
            try {
                emails[index] = tbl.getValueAt(e, 5).toString();
            } catch (NullPointerException ex) {
                isExceptionThrown = true;
            }
            index++;
        }
        if (isExceptionThrown) {
            JOptionPane.showMessageDialog(this, "Student(s) Doesn't Have Email");
        }
        new EmailPackage.Custom_Emails(emails).setVisible(true);
    }

    public boolean deleteBookGroup(String groupName) throws SQLException, ClassNotFoundException {
        boolean check = false;
        connection();
        ps = con.prepareStatement("DROP TABLE lms_Book_groups.`" + groupName + "`");
        if (!ps.execute()) {
            ps = con.prepareStatement("DELETE FROM lms_Book_groups.group_names\n"
                    + "WHERE table_name = '" + groupName + "'");
            check = !ps.execute();
        }
        return check;
    }

    public boolean deleteStudentFromGroup(String group, String id) throws SQLException, ClassNotFoundException {
        connection();
        ps = con.prepareStatement("DELETE FROM lms_student_groups.`" + group + "`\n"
                + "WHERE `student id` = " + id);
        return !ps.execute();
    }

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
            java.util.logging.Logger.getLogger(All_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(All_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(All_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(All_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new All_Information().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBookGroupDelete;
    private javax.swing.JButton btnBookGroupsRefresh;
    private javax.swing.JButton btnBookGroupsRemove;
    private javax.swing.JButton btnBookGroupsRename;
    private javax.swing.JButton btnBookIssuedRefresh;
    private javax.swing.JButton btnBooksAddBook;
    private javax.swing.JButton btnBooksDelete;
    private javax.swing.JButton btnBooksEdit;
    private javax.swing.JButton btnBooksExport;
    private javax.swing.JButton btnBooksIssueBook;
    private javax.swing.JButton btnBooksRefresh;
    private javax.swing.JButton btnStatisticsExport;
    private javax.swing.JButton btnStatisticsFine;
    private javax.swing.JButton btnStatisticsPrint;
    private javax.swing.JButton btnStatisticsReturned;
    private javax.swing.JButton btnStudentGroupsClearance;
    private javax.swing.JButton btnStudentGroupsDelete;
    private javax.swing.JButton btnStudentGroupsRefresh;
    private javax.swing.JButton btnStudentGroupsRemove;
    private javax.swing.JButton btnStudentGroupsRename;
    private javax.swing.JButton btnStudentGroupsReturnBook;
    private javax.swing.JButton btnStudentIssueBook;
    private javax.swing.JButton btnStudentsAddStudent;
    private javax.swing.JButton btnStudentsClearance;
    private javax.swing.JButton btnStudentsDelete;
    private javax.swing.JButton btnStudentsEdit;
    private javax.swing.JButton btnStudentsEmailAccountInfo;
    private javax.swing.JButton btnStudentsExport;
    private javax.swing.JButton btnStudentsExport1;
    protected javax.swing.JButton btnStudentsRefresh;
    private javax.swing.JComboBox<String> cbBookGroupsNames;
    private javax.swing.JComboBox<String> cbStatistics;
    private javax.swing.JComboBox<String> cbStudentGroupsNames;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAcademyName;
    private javax.swing.JLabel lblBookBackImage;
    private javax.swing.JLabel lblBookCount;
    private javax.swing.JLabel lblBookFrontImage;
    private javax.swing.JLabel lblBookGroupsIssueInformation;
    private javax.swing.JLabel lblBookIssuedCount;
    private javax.swing.JLabel lblStudentCount;
    private javax.swing.JLabel lblStudentCountHavingIssuedBooks;
    private javax.swing.JLabel lblStudentCourseCount;
    private javax.swing.JLabel lblStudentStudentCount;
    private javax.swing.JLabel lblStudentsPicture;
    private javax.swing.JMenuItem menuItemAcademyName;
    private javax.swing.JMenuItem menuItemBooksReturnedToday;
    private javax.swing.JMenuItem menuItemClearStudents;
    private javax.swing.JMenu menuItemClearance;
    private javax.swing.JMenuItem menuItemCreateBackup;
    private javax.swing.JMenuItem menuItemCurrentFines;
    private javax.swing.JMenuItem menuItemEditDeveloperInformation;
    private javax.swing.JMenuItem menuItemFileEmails;
    private javax.swing.JMenuItem menuItemFileHistory;
    private javax.swing.JMenuItem menuItemHistory;
    private javax.swing.JMenuItem menuItemRestoreBackup;
    private javax.swing.JMenuItem menuItemSearchByDate;
    private javax.swing.JMenuItem menuItemSettings;
    private javax.swing.JMenuItem menuitemBooksIssuedToday;
    private javax.swing.JPanel pnlBook;
    private javax.swing.JPanel pnlBookGroup;
    private javax.swing.JPanel pnlStatistics;
    private javax.swing.JPanel pnlStudent;
    private javax.swing.JPanel pnlStudentGroup;
    private javax.swing.JLabel statisticsBookCount;
    private javax.swing.JLabel statisticsMemberCount;
    private javax.swing.JTable tblBookGroupsData;
    private javax.swing.JTable tblBooksBookData;
    private javax.swing.JTable tblStatisticsMemberData;
    private javax.swing.JTable tblStudentGroupsData;
    private javax.swing.JTable tblStudentGroupsIssuedBookData;
    private javax.swing.JTable tblStudentsStudentData;
    private javax.swing.JTextField txtBooksSearch;
    private javax.swing.JTextField txtStatisticsSearch;
    private javax.swing.JTextField txtStudentsSearch;
    // End of variables declaration//GEN-END:variables
}
