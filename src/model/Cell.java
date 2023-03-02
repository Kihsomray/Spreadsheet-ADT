package model;

import java.util.LinkedList;

public class Cell {

    //field to contain the actual value of the cell.
    private int value;

    //field to contain the expression of the cell.
    private ExpressionTree expression;

    //field to contain the cells that depend on this cell.
    private LinkedList<Cell> dependencyList = new LinkedList<>();

    /**
     * Constructor for the cell object.
     * @param input the expression stored in the cell.
     */
    public Cell (final String input) {
        expression = new ExpressionTree(input);
        value = calculate(expression);
    }

    /**
     * Calculates the value of the expression and returns it.
     *
     * @param eTree the expression of the cell.
     * @return the value of the expression.
     */
    private int calculate(ExpressionTree eTree) {
        return 0;
    }

    /**
     * Returns the value of the cell.
     *
     * @return the value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Method to update the values of dependent cells.
     */
    public void updateDependents(){
        for (Cell c : dependencyList) {
            c.setValue(c.expression);
        }
    }

    /**
     * Helper method to update the value of the cell.
     * @param theExpression used to update the value.
     */
    private void setValue(ExpressionTree theExpression) {
        value = calculate(theExpression);
    }
}
