package model;

public class SpreadSheet {

    private Cell[][] sheet;

    /**
     * Getter for a Cell object in the Spreadsheet.
     * @param theColumn The column we want.
     * @param theRow The row we want.
     * @return The cell at (theColumn, theRow).
     */
    public Cell getCell(int theColumn, int theRow) {
        return sheet[theColumn][theRow];
    }
}
