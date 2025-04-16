/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonFunction;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

/**
 *
 * @author Abdul Rehman
 */
public class JTableFunction {
    public final void centeringColumnsOfTables(JTable tbl, int[] columns) {
        DefaultTableCellRenderer centerText = new DefaultTableCellRenderer();
        centerText.setHorizontalAlignment(JLabel.CENTER);
        for (int i : columns) {
            tbl.getColumnModel().getColumn(i).setCellRenderer(centerText);
        }
    }
}
