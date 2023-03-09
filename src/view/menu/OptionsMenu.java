package view.menu;

import view.SSFrame;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Option menu contained within SpreadSheet menu bar.
 *
 * @author Elroy O Mbabazi
 * @version 3/7/2023
 */
public class OptionsMenu extends JMenu implements PropertyChangeListener {

    /**
     * The JFrame that the spreadsheet is displayed on.
     */
    private final SSFrame myFrame;

    /**
     * MenuItem to clear the entire spreadsheet.
     */
    private final JMenuItem clearAll;

    /**
     * MenuItem to display formulas to the spreadsheet.
     */
    private final JMenuItem displayFormula;

    /**
     * MenuItem to add row(s) to spreadsheet.
     */
    private final JMenuItem addRows;

    /**
     * MenuItem to add column(s) to spreadsheet.
     */
    private final JMenuItem addColumns;

    /**
     * Constructor to initialize fields.
     *
     * @param theFrame the MenuPanel
     */
    public OptionsMenu(final SSFrame theFrame) {

        super("Options");

        myFrame = theFrame;

        clearAll = new JMenuItem("Clear All");
        displayFormula = new JMenuItem("Formula");
        addRows = new JMenuItem("Add Row(s)");
        addColumns = new JMenuItem("Add Column(s)");

        setupOptionsMenu();
    }

    /**
     * Add menu items to OptionsMenu and sets a Mnemonic.
     */
    private void setupOptionsMenu() {

        clearAll();
        displayformula();
        addRows();
        addColumns();

        add(clearAll);
        addSeparator();
        add(displayFormula);
        addSeparator();
        add(addRows);
        addSeparator();
        add(addColumns);
    }

    /**
     * Adds ActionListener to clearAll menu item.
     */
    private void clearAll() {

        adjustValues(clearAll, KeyEvent.VK_D, e -> {

            // TODO add this

        });

    }

    /**
     * A method to display the formula.
     */
    private void displayformula() {

        adjustValues(displayFormula, KeyEvent.VK_F, e -> {

        });

    }

    /**
     * Adds ActionListener to addRows menu item.
     */
    // Not currently implemented.
    private void addRows() {

        adjustValues(addRows, KeyEvent.VK_R, e -> {

        });

    }

    /**
     * Adds ActionListener to addColumns menu item.
     */
    // Not currently implemented.
    private void addColumns() {

        adjustValues(addColumns, KeyEvent.VK_C, e -> {


        });

    }

    /**
     * Streamlines the process of adding MenuItems.
     *
     * @param theMenuItem Menu item to adjust
     * @param theMnemonic Mnemonic to attach
     * @param theActionEvent Action event
     */
    private void adjustValues(final JMenuItem theMenuItem, final int theMnemonic, final ActionListener theActionEvent) {
        theMenuItem.setEnabled(true);
        theMenuItem.setMnemonic(theMnemonic);
        theMenuItem.addActionListener(theActionEvent);
    }

    /**
     * Shows the formula if a propertyChange is detected in the Table.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if ("TableState".equals(theEvent.getPropertyName())) {
            displayFormula.setEnabled((boolean) theEvent.getNewValue());
        }
    }
}

