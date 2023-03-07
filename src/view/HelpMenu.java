package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URI;

public class HelpMenu {

    /** JMenu to hold Help menu items. */
    private final JMenu myHelpMenu;

    /** MenuItem to display help contents. */
    private final JMenuItem helpContents;

    /** MenuItem to display info about PowerPaint. */
    private final JMenuItem about;

    /** Constructor to initialize fields. */
    public HelpMenu() {

        myHelpMenu = new JMenu("Help");

        helpContents = new JMenuItem("Help Contents");
        about = new JMenuItem("About");

        setupHelpMenu();
    }

    /**
     * Returns the help menu.
     *
     * @return the Options menu
     */
    public JMenu getHelpMenu() {

        return myHelpMenu;
    }

    /**
     * Adds ActionListener to helpContents menu item.
     */
    private void buildHelpContents() {

        helpContents.setEnabled(true);
        helpContents.setMnemonic(KeyEvent.VK_H);
        helpContents.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                try {
                    Desktop.getDesktop().browse(new URI("https://example.com/help"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds ActionListener to about menu item.
     */
    private void buildAbout() {

        about.setEnabled(true);
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                JOptionPane.showMessageDialog(null, "TCSS 342 Spreadsheet");
            }
        });
    }

    /**
     * Adds menu items to HelpMenu and sets a Mnemonic.
     */
    private void setupHelpMenu() {

        buildHelpContents();
        buildAbout();

        myHelpMenu.add(helpContents);
        myHelpMenu.add(about);
    }
}


