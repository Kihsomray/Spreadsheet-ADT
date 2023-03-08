package view.table;

import model.Cell;
import model.SpreadSheet;

import javax.swing.table.DefaultTableModel;

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

    @Override
    public Object getValueAt(int theRow, int theColumn) {
        if (theColumn == 0) {
            return theRow;
        }
        return super.getValueAt(theRow, theColumn);
    }

    @Override
    public void setValueAt(final Object theValue, final int theRow, final int theColumn) {
        try {
            // first column is row column
            if (theColumn == 0) {
                return;
            }

            // calls the super method
            super.setValueAt(theValue, theRow, theColumn);

            // adjusts the cell at this location
            mySpreadSheet.addCell(theValue.toString(), theRow, theColumn - 1);

            // update cell value in JTable
            final Cell cell = mySpreadSheet.getCellAt(theRow, theColumn - 1);
            if (cell != null) {
                super.setValueAt(cell.getCellValue(), theRow, theColumn - 1);
            }

        } catch (Exception e) {
            // TODO catch all exceptions
            System.err.println("Error setting cell value: " + e.getMessage());
        }
    }

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
