package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class FileMenu {

    /** JMenu to hold File menu items. */
    private final JMenu myFileMenu;

    /** JFrame containing spreadsheet application. */
    private final JFrame myFrame;

    /** MenuItem to open a file in the spreadsheet. */
    private final JMenuItem openFile;

    /** MenuItem to save spreadsheet to a file. */
    private final JMenuItem saveFile;

    /** MenuItem to exit Spreadsheet Application. */
    private final JMenuItem exitApp;

    /**
     * Initializes a new FileMenu.
     *
     * @param theFrame the current JFrame
     */
    public FileMenu(final JFrame theFrame) {

        myFileMenu = new JMenu("File");

        myFrame = theFrame;

        openFile = new JMenuItem("Open...");
        saveFile = new JMenuItem("Save...");
        exitApp = new JMenuItem("Exit");

        setupFileMenu();
    }

    /**
     * Returns this file menu.
     *
     * @return the File menu
     */
    public JMenu getFileMenu() {

        return myFileMenu;
    }

    /**
     * Adds ActionListener to openFile menu item.
     */
    private void buildOpenFile() {

        openFile.setEnabled(true);
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                //Code here
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
        saveFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                //Code here
            }
        });
    }

    /**
     * Adds ActionListener to exitApp menu item.
     */
    private void buildExitApp() {

        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        exitApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    /**
     * Adds menu items to FileMenu and sets a Mnemonic.
     */
    private void setupFileMenu() {

        buildOpenFile();
        buildSaveFile();
        buildExitApp();

        myFileMenu.add(openFile);
        //myMenu.addSeparator();
        myFileMenu.add(saveFile);
        myFileMenu.addSeparator();
        myFileMenu.add(exitApp);
    }
}

