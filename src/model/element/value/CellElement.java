package model.element.value;

import model.SpreadSheet;
import model.Utility;

public class CellElement implements ValueElement {

    private final SpreadSheet ss;

    private int row;
    private int column;

    public CellElement(final String location, final SpreadSheet ss) {
        setRowColumn(location);
        this.ss = ss;
    }

    @Override
    public int getValue() {
        return 0;
    }

    private void setRowColumn(final String location) {
        final char[] chars = location.toCharArray();
        int i = 0;
        int column = 0;
        while (Utility.isAlphabetical(chars[i])) {
            final char c = chars[i];
            column = column * 26 + c - (Utility.isLowercase(c) ? 96 : 64);
            i++;
        }
        this.column = column - 1;

        int row = 0;
        while (i < chars.length) {
            final char c = chars[i];
            row = row * 10 + c - (Utility.isLowercase(c) ? 96 : 64);
            i++;
        }
        this.row = row - 1;
    }

}
