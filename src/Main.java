import model.SpreadSheet;

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
     * @param theArgs system commands
     */
    public static void main(final String[] theArgs) {

        SpreadSheet test = new SpreadSheet(10, 10);
        test.addCell("5 - 3", 0, 0);
        test.printCellFormula(0,0);
        System.out.println(test.getCellAt(0, 0).getCellValue());
        test.addCell("1+A0", 2, 2);
        test.printCellFormula(2,2);
        System.out.println("C2 = " + test.getCellAt(2, 2).getCellValue());

        test.addCell("1+C2", 2, 2);
        test.printCellFormula(2,2);
        System.out.println("C2 = " + test.getCellAt(2, 2).getCellValue());

        test.addCell("2^C2^2", 1, 1);
        test.printCellFormula(1,1);
        System.out.println(test.getCellAt(1, 1).getCellValue());

    }

}
