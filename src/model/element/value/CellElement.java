package model.element.value;

import model.SpreadSheet;
import model.Utility;

public class CellElement implements ValueElement {

    private int row;
    private int column;

    private final SpreadSheet ss;

    public CellElement(final SpreadSheet ss) {
        this.ss = ss;
    }

    public CellElement(final int row, final int column, final SpreadSheet ss) {
        this.row = row;
        this.column = column;
        this.ss = ss;
    }

    public static int applyValues(final String formula, int index, final CellElement element) {
        int column = 0;
        while (Utility.isAlphabetical(formula.charAt(index))) {
            final char c = formula.charAt(index);
            column = column * 26 + c - (Utility.isLowercase(c) ? 'a' : 'A') + 1;
            index++;
        }
        column -= 1;

        int row = 0;
        while (Utility.isNumerical(formula.charAt(index))) {
            final char c = formula.charAt(index);
            row = row * 10 + c - (Utility.isLowercase(c) ? 'a' : 'A');
            index++;
        }
        if (row < 0) throw new IllegalArgumentException("Rows cannot be less than 0!");
        element.row = row;
        element.column = column;
        return index;
    }

    @Override
    public int getValue() {
        return 0;
    }

}
