package view.menu;

import controller.SpreadSheetBuilder;
import model.SpreadSheet;
import view.SSFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * File menu contained within SpreadSheet menu bar.
 *
 * @author Elroy O Mbabazi
 * @version 3/7/2023
 */
public class FileMenu extends JMenu {

    /**
     * JFrame containing spreadsheet application.
     */
    private SSFrame myFrame;

    /**
     * MenuItem to open a file in the spreadsheet.
     */
    private final JMenuItem openFile;

    /**
     * MenuItem to save spreadsheet to a file.
     */
    private final JMenuItem saveFile;

    /**
     * MenuItem to exit Spreadsheet Application.
     */
    private final JMenuItem exitApp;

    /**
     * Initializes a new FileMenu.
     *
     * @param theFrame the current JFrame
     */
    public FileMenu(final SSFrame theFrame) {

        super("File");

        myFrame = theFrame;

        openFile = new JMenuItem("Open...");
        saveFile = new JMenuItem("Save...");
        exitApp = new JMenuItem("Exit");

        setupFileMenu();
    }

    /**
     * Adds menu items to FileMenu and sets a Mnemonic.
     */
    private void setupFileMenu() {

        buildOpenFile();
        buildSaveFile();
        buildExitApp();

        add(openFile);
        add(saveFile);
        addSeparator();
        add(exitApp);
    }

    /**
     * Adds ActionListener to openFile menu item. (WIP)
     */
    private void buildOpenFile() {

        openFile.setEnabled(true);
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openFile.addActionListener(theEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String file = String.valueOf((fileChooser.getSelectedFile()));

                String[] fileData = myFrame.getMySpreadSheet().loadSheet(file); //read file

                int rowSize = Integer.parseInt(fileData[0]);
                int colSize = Integer.parseInt(fileData[1]); //retrieve col and row size

                //initialize new spreadsheet
                SpreadSheet ss = new SpreadSheet(new SpreadSheetBuilder(colSize,rowSize),colSize,rowSize);

                for (int i = 2; i < fileData.length; i++) {
                    String[] elements = (fileData[i].split(";")); //split the string representation up again, based on the elements of the cell.
                    int row = Integer.parseInt(elements[0]); //first element is row
                    int column = Integer.parseInt(elements[1]); //second is col
                    String input = elements[2]; //third is the actual input of the cell.
                    ss.addCell(input, row, column);
                }
                myFrame = new SSFrame(ss);
            }
        });
    }
    /**
     * Adds ActionListener to saveFile menu item.
     */
    private void buildSaveFile() {
        saveFile.setEnabled(true);
        saveFile.setMnemonic(KeyEvent.VK_S);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(theEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String file = String.valueOf((fileChooser.getSelectedFile()));
                myFrame.getMySpreadSheet().saveSheet(file);
                JOptionPane.showMessageDialog(null, "Save Successful");
            }
        });
    }

    /**
     * Adds ActionListener to exitApp menu item.
     */
    private void buildExitApp() {

        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        exitApp.addActionListener(theEvent -> {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        });
    }

}

