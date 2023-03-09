package view.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import static view.SSFrame.ROW_COLUMN_WIDTH;

/**
 * Custom table for the SpreadSheet GUI.
 *
 * @author Elroy O Mbabazi
 * @version 3/7/2023
 */
public class SSTable extends JTable {

    /**
     * Constructor for the table.
     * @param theModel theTableModel we are building the table off of.
     */
    public SSTable(final SSTableModel theModel) {
        super(theModel);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{

            // set alignment
            setHorizontalAlignment(SwingConstants.RIGHT);

            // set background color of column 0
            setBackground(new Color(219, 216, 216));

        }});

        // column header values
        final TableColumnModel columnModel = getColumnModel();
        columnModel.getColumn(0).setHeaderValue("");
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderValue(columnHeaderCalc(i));
        }

        // other settings
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getColumnModel().getColumn(0).setPreferredWidth(ROW_COLUMN_WIDTH);
        getTableHeader().setReorderingAllowed(false);
    }

    /**
     * Calculates column header value.
     *
     * @param theColumn Column number
     * @return String value of the column
     */
    private String columnHeaderCalc(final int theColumn) {
        String columnTitle = "";
        int test = theColumn;
        char value;
        if (theColumn == 0) {
            return columnTitle + 'A';
        }
        while (test > 0) {
            test--;
            int lowestValue = test % 26;
            value = (char) (lowestValue + 65);
            columnTitle = value + columnTitle;
            test /= 26;
        }
        return columnTitle;
    }

}
