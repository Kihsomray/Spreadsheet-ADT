package model.element.value;

import model.SpreadSheet;

public class CellElement implements ValueElement {

    private final SpreadSheet ss;
    private final int row;
    private final int column;

    public CellElement(final int row, final int column, final SpreadSheet ss) {
        this.row = row;
        this.column = column;
        this.ss = ss;
    }

    @Override
    public int getValue() {
        return 0;
    }

}
