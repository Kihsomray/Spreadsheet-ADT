package model.element.value;

import model.Cell;
import model.SpreadSheet;
import model.element.OperationElement;

public class CellElement implements ValueElement {

    private int row;
    private int column;

    private final SpreadSheet ss;

    public CellElement(final SpreadSheet ss) {
        this.ss = ss;
    }

    public static int applyValues(final String formula, int index, final CellElement element) {
        int column = 0;
        while (formula.length() > index && Character.isAlphabetic(formula.charAt(index))) {
            final char c = formula.charAt(index);
            column = column * 26 + c - (Character.isLowerCase(c) ? 'a' : 'A') + 1;
            index++;
        }
        column -= 1;

        boolean noRow = true;
        int row = 0;
        while (formula.length() > index && Character.isDigit(formula.charAt(index))) {
            noRow = false;
            final char c = formula.charAt(index);
            row = row * 10 + c - '0';
            index++;
        }
        if (formula.length() > index) {
            char c = formula.charAt(index);
            if (!OperationElement.OPERATORS.contains(formula.charAt(index)) && c != ')' && !Character.isWhitespace(c))
                throw new IllegalArgumentException("Invalid formula!");
        }
        if (noRow) throw new IllegalArgumentException("Cell references must contain a Column and Row!");
        if (row < 0) throw new IllegalArgumentException("Rows cannot be less than 0!");
        element.row = row;
        element.column = column;
        return index;
    }

    @Override
    public int getValue() {
        return getCell().getCellValue();
    }

    public Cell getCell() {
        return ss.getCellAt(row, column);
    }

    @Override
    public String toString() {
        return "[CE: r-" + row + ", c-" + column + "]";
    }

}
