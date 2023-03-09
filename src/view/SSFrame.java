package view;

import model.SpreadSheet;
import view.menu.FileMenu;
import view.menu.HelpMenu;
import view.menu.OptionsMenu;
import view.table.SSTable;
import view.table.SSTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * SpreadSheet frame containing main contents of GUI.
 *
 * @author Elroy O Mbabazi
 * @author Matt Bauchspies
 * @author Michael N Yarmoshik
 * @version 3/7/2023
 */
public class SSFrame extends JFrame {

    /**
     * Spreadsheet attached to this GUI.
     */
    private final SpreadSheet mySpreadSheet;

    /**
     * Table attached to this frame.
     */
    private final SSTable myTable;

    /**
     * Insert text field (paired with insert button).
     */
    private final JTextField textField;

    /**
     * Menu bar attached to this frame.
     */
    private final JMenuBar myMenuBar;

    /**
     * Insert panel at top of frame.
     */
    private final JPanel insertPanel;


    /**
     * The width of the column of row numbers in pixels.
     */
    public static final int ROW_COLUMN_WIDTH = 40;


    public SSFrame(final SpreadSheet theSpreadSheet) {
        // initialize the frame
        super("SpreadSheet");

        // set field(s)
        mySpreadSheet = theSpreadSheet;

        // basic GUI elements
        myMenuBar = new JMenuBar();

        // insert field/panel
        textField = new JTextField(50);
        insertPanel = new JPanel(new FlowLayout());

        // insert panel
        insertPanel();

        // creates necessary spreadsheet table
        myTable = new SSTable(new SSTableModel(mySpreadSheet));

        // finalize
        createFrame();
//        addFormulaMenu();
    }

    /**
     * Insert panel (text + button).
     */
    private void insertPanel() {
        insertPanel.add(textField);
        final JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(theEvent -> insertData());
        insertPanel.add(insertButton);
    }

    /**
     * Inserts data at selected row and column.
     */
    private void insertData() {
        // retrieve the indices of the selected cell
        final int row = myTable.getSelectedRow();
        final int column = myTable.getSelectedColumn();

        // checking if the cell has been selected
        if (row == -1 || column == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cell to insert the data.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.setValueAt(textField.getText(), row, column);
        textField.setText("");
    }

    /**
     * Adds the formula menu.
     */
    private void addFormulaMenu() {

            JMenu formulaMenu = new JMenu("Formula");
            JMenuItem getFormulaItem = new JMenuItem("Get Formula");
            getFormulaItem.addActionListener(e -> {
                final int row = myTable.getSelectedRow();
                final int col = myTable.getSelectedColumn();
                if (row != -1 && col != -1) {
                    String formula = mySpreadSheet.getCellAt(row, col - 1).getFormula();
                    textField.setText(formula);
                }
            });
            formulaMenu.add(getFormulaItem);



            // Add mouse clicked event to the cells in the JTable
            myTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    int row = myTable.rowAtPoint(evt.getPoint());
                    int col = myTable.columnAtPoint(evt.getPoint());
                    if (row != -1 && col != -1) {
                        if (mySpreadSheet.getCellAt(row, col - 1) == null) {
                            textField.setText("");
                        } else {
                            String formula = mySpreadSheet.getCellAt(row, col - 1).getFormula();
                            if (formula != null && !formula.isEmpty()) {
                                // need fixing <-- add checker to display only when editing cell!
                                // table.isEditing or whatever doesn't work right
                                ((SSTableModel) myTable.getModel()).setGUIValue(formula, row, col);
                                textField.setText(formula);
                            }
                        }
                    }
                }
            });
            myMenuBar.add(formulaMenu);
        }

    /**
     * Adds the menu bar to the Frame.
     */
    private void addMenuBar() {

        // file menu
        myMenuBar.add(new FileMenu(this));

        // options menu
//        myMenuBar.add(new OptionsMenu(this));

        // help menu
        myMenuBar.add(new HelpMenu());

        // set the menu bar
        setJMenuBar(myMenuBar);
    }

    /**
     * Creates the JFrame to hold the JTable.
     */
    private void createFrame() {
        // table creation
        final JScrollPane table = new JScrollPane(
                myTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // menu bar
        addMenuBar();

        // other settings
        add(insertPanel,BorderLayout.NORTH);
        Dimension tableSize = myTable.getSize();
        setSize(tableSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(table, BorderLayout.CENTER);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Gets underlying SpreadSheet instance.
     *
     * @return Underlying SpreadSheet instance
     */
    public SpreadSheet getMySpreadSheet() {
        return mySpreadSheet;
    }

    /**
     * Gets SpreadSheet table.
     *
     * @return SpreadSheet table
     */
    public JTable getTable() {
        return myTable;
    }

}
