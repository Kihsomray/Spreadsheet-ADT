package controller;

import model.SpreadSheet;
import view.SSFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Used to build a SpreadSheet.
 */
public class SpreadSheetBuilder {

    /**
     * SpreadSheet object to build it on.
     */
    private final SpreadSheet mySpreadSheet;

    /**
     * SpreadSheet frame for model access.
     */
    private SSFrame mySpreadSheetFrame;

    /**
     * Default SpreadSheetBuilder. Will prompt the
     * user for initial dimensions of the frame.
     */
    public SpreadSheetBuilder() {
        mySpreadSheet = promptUserForDimensions();
    }

    /**
     * Custom SpreadSheetBuilder. constructs a SpreadSheet
     * based on a set size for rows and columns.
     *
     * @param theRows Number of rows in spreadsheet
     * @param theColumns Number of columns in spreadsheet
     */
    public SpreadSheetBuilder(final int theRows, final int theColumns) {
        mySpreadSheet = new SpreadSheet(this, theColumns, theRows);
    }

    public SSFrame build() {
        return mySpreadSheetFrame = new SSFrame(mySpreadSheet);
    }

    /**
     * Prompts the user for the number of rows and columns to create.
     * Returns a new SpreadSheet object based on information.
     */
    private SpreadSheet promptUserForDimensions() {

        // creates JPanel to display
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // rows label/field
        panel.add(new JLabel("Enter the number of rows:"));
        final JTextField rowsField = new JTextField(5);
        panel.add(rowsField);

        // columns label/field
        panel.add(new JLabel("Enter the number of columns:"));
        final JTextField columnsField = new JTextField(5);
        panel.add(columnsField);

        // display the panel
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Enter spreadsheet dimensions",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int rows = Integer.parseInt(rowsField.getText());
                final int columns = Integer.parseInt(columnsField.getText());
                return new SpreadSheet(this, rows, columns);
            } catch (NumberFormatException e) {
                // creates JPanel to display
                JPanel errorPanel = new JPanel(new GridLayout(0, 1));

                // rows label/field
                errorPanel.add(new JLabel("Invalid row/column entry!"));

                // display the panel
                JOptionPane.showConfirmDialog(
                        null,
                        errorPanel,
                        "Error!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                System.exit(0);
                return null;
            }
        } else {

            // If they decided to quit
            System.exit(0);
            return null;
        }
    }

    /**
     * Gets SpreadSheet JFrame instance.
     *
     * @return SpreadSheet JFrame instance
     */
    public SSFrame getMySpreadSheetFrame() {
        return mySpreadSheetFrame;
    }

}
