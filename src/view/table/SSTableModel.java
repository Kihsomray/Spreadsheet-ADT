package view.table;

import model.Cell;
import model.SpreadSheet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Custom table model for SSTable.
 *
 * @author Elroy O Mbabazi
 * @version 3/7/2023
 */
public class SSTableModel extends DefaultTableModel {

    /**
     * Instance of the SpreadSheet.
     */
    private final SpreadSheet mySpreadSheet;

    /**
     * Constructs a new SpreadSheet table.
     *
     * @param theSpreadSheet Instance of SpreadSheet
     */
    public SSTableModel(final SpreadSheet theSpreadSheet) {
        super(theSpreadSheet.getMyRows(), theSpreadSheet.getMyColumns());
        mySpreadSheet = theSpreadSheet;
    }

    /**
     * Sets the GUI value without updating cell.
     *
     * @param theValue Value to set cell to
     * @param theRow Row index
     * @param theColumn Column index
     */
    public void setGUIValue(final String theValue, final int theRow, final int theColumn) {
        super.setValueAt(theValue, theRow, theColumn);
    }

    /**
     * Returns a value at a specific table index.
     * @param theRow             the row whose value is to be queried
     * @param theColumn          the column whose value is to be queried
     * @return The value at a specified table index.
     */
    @Override
    public Object getValueAt(int theRow, int theColumn) {
        if (theColumn == 0) {
            return theRow;
        }
        return super.getValueAt(theRow, theColumn);
    }

    /**
     * Sets the value at a specific table index.
     * @param theValue          the new value; this can be null
     * @param theRow             the row whose value is to be changed
     * @param theColumn          the column whose value is to be changed
     */
    @Override
    public void setValueAt(final Object theValue, final int theRow, final int theColumn) {

        final Cell cell = mySpreadSheet.getCellAt(theRow, theColumn - 1);
        try {
            // first column is row column
            if (theColumn == 0) {
                return;
            }

            // calls the super method
            super.setValueAt(theValue, theRow, theColumn);

            // adjusts the cell at this location
            mySpreadSheet.addCell(theValue.toString(), theRow, theColumn - 1, false);

            // update cell value in JTable
            if (cell != null) {
                super.setValueAt(cell.getCellValue(), theRow, theColumn);
            }

        } catch (Exception e) {

            // creates JPanel to display
            JPanel panel = new JPanel(new GridLayout(0, 1));

            // rows label/field
            panel.add(new JLabel(e.getMessage()));

            // display the panel
            JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Error!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            super.setValueAt(cell == null ? "" : cell.getCellValue(), theRow, theColumn);
            //System.err.println("Error setting cell value: " + e.getMessage());
        }
    }

    /**
     * Boolean for whether a cell can be edited or not. Currently only prevents first column edits.
     * @param theRow             the row whose value is to be queried
     * @param theColumn          the column whose value is to be queried
     * @return True if editable.
     */
    @Override
    public boolean isCellEditable(final int theRow, int theColumn) {
        return theColumn > 0;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 0) {
            return Integer.class;
        }
        return super.getColumnClass(column);
    }

}
