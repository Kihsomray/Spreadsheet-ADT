package model.element.value;

import model.SpreadSheet;
import model.Utility;
import model.element.Element;

/**
 * An interface that allows treating multiple distinct Elements as their integer value.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 2/27/2023
 */
public interface ValueElement extends Element {

    /**
     * Getter for whatever value the valueElement contains.
     * @return An integer value representing the element.
     */
    int getValue();

    /**
     * Generates a ValueElement based on a char array passed and
     * parsed from the ExpressionTree class parser.
     *
     * @param chars array of chars to parse
     * @param ss spreadsheet in use
     * @return correctly generated ValueElement
     */
    static ValueElement generateFromArray(final char[] chars, final SpreadSheet ss) {
        return Utility.isNumerical(chars[0]) ? generalLiteralElement(chars) : generateCellElement(chars, ss);
    }

    private static LiteralElement generalLiteralElement(final char[] chars) {

        int value = 0;
        for (char c : chars) value = value * 10 + c - 48;
        return new LiteralElement(value);

    }

    private static CellElement generateCellElement(final char[] chars, final SpreadSheet ss) {
        int i = 0;
        int column = 0;
        while (Utility.isAlphabetical(chars[i])) {
            final char c = chars[i];
            column = column * 26 + c - (Utility.isLowercase(c) ? 96 : 64);
            i++;
        }
        column -= 1;

        int row = 0;
        while (i < chars.length) {
            final char c = chars[i];
            row = row * 10 + c - (Utility.isLowercase(c) ? 96 : 64);
            i++;
        }
        row -= 1;

        return new CellElement(row, column, ss);
    }

}
