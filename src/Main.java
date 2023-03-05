import model.Cell;
import model.ExpressionTree;
import model.SpreadSheet;

import java.io.*;

/**
 * Main class of the entire program.
 *
 * @author Matt Bauchspies
 * @author Elroy O Mbabazi
 * @author Michael N Yarmoshik
 * @author Max Y Yim
 * @version 3/4/2023
 */
public class Main {

    /**
     * Main method of the program.
     *
     * @param args system commands
     */
    public static void main(final String[] args) {

        // start of code
        ExpressionTree tree = new ExpressionTree("6-1 1 - 4      2      0                 - 6     9 ", null);
        System.out.println(tree.calculate());
    }

}
