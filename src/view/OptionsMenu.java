package view;

import model.SpreadSheet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OptionsMenu implements PropertyChangeListener {

    /** JMenu to hold Options menu items. */
    private final JMenu myOptionsMenu;

    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;

    /** The Spreadsheet. */
    private final SpreadSheet mySpreadsheet;

    /** The table that holds the cells for the spreadsheet. */
    private final JTable myTable;

    /** MenuItem to clear the entire spreadsheet. */
    private final JMenuItem clearAll;

    /** MenuItem to display formulas to the spreadsheet. */
    private final JMenuItem displayFormula;

    /** MenuItem to add row(s) to spreadsheet. */
    private final JMenuItem addRows;

    /** MenuItem to add column(s) to spreadsheet. */
    private final JMenuItem addColumns;

    /**
     * Constructor to initialize fields.
     *
     * @param theFrame the MenuPanel
     */
    public OptionsMenu(final JFrame theFrame, final SpreadSheet theSpreadsheet, final JTable theTable) {

        myOptionsMenu = new JMenu("Options");

        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        myTable = theTable;

        clearAll = new JMenuItem("Clear All");
        displayFormula = new JMenuItem("Formula");
        addRows = new JMenuItem("Add Row(s)");
        addColumns = new JMenuItem("Add Column(s)");

        setupOptionsMenu();
    }

    /**
     * Method to get myOptionsMenu field.
     *
     * @return the Options menu
     */
    public JMenu getOptionsMenu() {

        return myOptionsMenu;
    }

    /**
     * Adds ActionListener to clearAll menu item.
     */
    private void clearAll() {

        clearAll.setEnabled(true);
        clearAll.setMnemonic(KeyEvent.VK_C);
        clearAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                //TODO
            }
        });
    }
    private void displayformula() {

        displayFormula.setEnabled(true);
        displayFormula.setMnemonic(KeyEvent.VK_C);
        displayFormula.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                //TODO
            }
        });
    }

    /**
     * Adds ActionListener to addRows menu item.
     */
    private void addRows() {

        addRows.setEnabled(true);
        addRows.setMnemonic(KeyEvent.VK_R);
        addRows.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                //TODO
            }
        });
    }

    /**
     * Adds ActionListener to addColumns menu item.
     */
    private void addColumns() {

        addColumns.setEnabled(true);
        addColumns.setMnemonic(KeyEvent.VK_C);
        addColumns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                //TODO
            }
        });
    }

    /** Add menu items to OptionsMenu and sets a Mnemonic. */
    private void setupOptionsMenu() {

        clearAll();
        displayformula();
        addRows();
        addColumns();

        myOptionsMenu.add(clearAll);
        myOptionsMenu.addSeparator();
        myOptionsMenu.add(displayFormula);
        myOptionsMenu.addSeparator();
        myOptionsMenu.add(addRows);
        myOptionsMenu.addSeparator();
        myOptionsMenu.add(addColumns);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if ("TableState".equals(theEvent.getPropertyName())) {

            displayFormula.setEnabled((boolean) theEvent.getNewValue());
        }
    }
}

