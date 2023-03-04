package model;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * The GUI class represents the graphical user interface for a spreadsheet application.
 * It allows the user to interact with a spreadsheet object by setting the number of rows and columns
 * and adjusting the window size based on the dimensions of the spreadsheet.
 * The class initializes the JFrame, JMenuBar, JToolBar, WindowMenu, and Spreadsheet objects and
 * handles their resizing based on the user input.
 */

public class SpreadSheetGUI {

    private JFrame mySpreadSheetFrame;

    /** The minimum number of rows and columns allowed. */
    private static final int MIN_DIMENSION = 3;


    /** Horizontal offset for initial frame resizing. */
    private static final int WIDTH_OFFSET = 49;

    /** Vertical offset for initial frame resizing. */
    private static final int HEIGHT_OFFSET = 127;

    /** The width of a cell in pixels. Default = 75 */
    private static final int CELL_WIDTH = 75;

    /** The height of a cell in pixels. Default = 16 */
    private static final int CELL_HEIGHT = 16;

    /** The JMenuBar will display the Menu.*/
    private final JMenuBar  myMenuBar;

//    /** JToolBar to display used tools*/
//    private final JToolBar myToolBar;

    /** JPanel for the dialog */
    JPanel mainPanel = new JPanel();

    /** JPanel to Hold the text fields for the row and column inputs*/
    JPanel inputPanel = new JPanel();

    /** The SpreadSheet that contains all the data.*/
    private SpreadSheet mySpreadsheet;

    /** number of rows in the SpreadSheet*/
    private int rows;

    /** number of columns in the SpreadSheet.*/
    private int cols;

    /**
     * Initializes the GUI object by creating a JFrame, JMenuBar, JToolBar, WindowMenu, and Spreadsheet object.
     * It prompts the user to input the desired number of rows and columns, adjusts the initial and minimum
     * component size based on the user input, and fills the GUI with its contents.
     */
    public SpreadSheetGUI() {
        /* Creating the frame for the spreadSheet */
        mySpreadSheetFrame = new JFrame("model.SpreadSheet");

        /** user input of desired rows and columns*/
        final Dimension dimension = initialize();

        /** creates a new spreadsheet with the desired input*/
        mySpreadsheet = new SpreadSheet((int)dimension.getWidth(), (int) dimension.getHeight());

        /** resizing the components in the GUI*/
        resizeComponents();
        myMenuBar = null;
    }

    /**
     * This method adjusts the initial and minimum component size based on the
     * number of rows and columns.
     */
    private void resizeComponents() {
        /** Calculating the width and height of all components based on the table dimensions*/
        int minWidth = (WIDTH_OFFSET + CELL_WIDTH * cols);
        int minHeight = (HEIGHT_OFFSET + CELL_HEIGHT * rows);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth();
        final double height = screenSize.getHeight();
        mySpreadSheetFrame.setSize((int) Math.min(minWidth, width), (int) Math.min(minHeight, height));
    }

    /** user-prompt to enter required SpreadSheet Size.
     * @return the size of the SpreadSheet
     * */
    private Dimension initialize() {
        /** text fields*/
        final Dimension size = new Dimension();

        /** Create a number format for the row and column size fields*/
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();

        /** Create the Formatted text field with the number format and sets the intial value of 0*/
        JFormattedTextField rowSize = new JFormattedTextField(numberFormat);
        //rowSize.setValue(0);
        JFormattedTextField columnSize = new JFormattedTextField(numberFormat);
        //columnSize.setValue(0);

        /**Set teh preferred size of the text fields*/
        Dimension fieldSize = new Dimension(60, 25);
        rowSize.setPreferredSize(fieldSize);
        columnSize.setPreferredSize(fieldSize);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(new JPanel().add(new JLabel("Sizes must be at least 3 rows x 3 columns.")));

        inputPanel.add(new JLabel("Rows:"));
        inputPanel.add(rowSize);
        /** Box.createVerticalStrut(int height),creates a fixed-height invisible component, which can be used to
         * add vertical spacing between components in a container.
         */
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(new JLabel("Column:"));
        inputPanel.add(columnSize);
        mainPanel.add(inputPanel);

        /** JoptionPane user prompt*/

        try{
            int result = JOptionPane.showConfirmDialog(mySpreadSheetFrame, mainPanel, "Enter the spreadsheet's size:",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                /** gets user input and creates a spreadsheet according to the input*/
                int numColumns = Math.max(Integer.parseInt(columnSize.getText()), MIN_DIMENSION);
                int numRows = Math.max(Integer.parseInt(rowSize.getText()), MIN_DIMENSION);

            } else {
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            // If invalid input is entered
            // Both dimensions set to minimum size
            rows = cols = MIN_DIMENSION;
            JOptionPane.showMessageDialog(mySpreadSheetFrame.getContentPane(),
                    "Invalid dimensions entered! Using default 3 x 3 table.",
                    "Error! Invalid Dimensions!",
                    JOptionPane.ERROR_MESSAGE);
        }
        size.setSize(cols , rows);
        return size;
    }

    public void run(){
        mySpreadSheetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mySpreadSheetFrame.add(new JScrollPane(mySpreadsheet.getTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        addMenuBar();
        mySpreadSheetFrame.setVisible(true);
        mySpreadSheetFrame.setLocationRelativeTo(null);
    }

    private void addMenuBar() {

    }
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> new SpreadSheetGUI().run());
    }
}
