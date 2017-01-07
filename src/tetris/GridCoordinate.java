/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author jsdratm
 */
public class GridCoordinate {
    private int rowNumber;
    private int columnNumber;

    public GridCoordinate(int rowNumberIn, int columnNumberIn)
    {
        rowNumber = rowNumberIn;
        columnNumber = columnNumberIn;
    }

    public void setRowNumber(int input)
    {
        rowNumber = input;
    }

    public void setColumnNumber(int input)
    {
        columnNumber = input;
    }

    public void shiftCoordinate(int deltaRow, int deltaColumn)
    {
        rowNumber += deltaRow;
        columnNumber += deltaColumn;

        if (rowNumber < 0)
        {
            rowNumber = 0;
        }
        
        if (columnNumber < 0)
        {
            columnNumber = 0;
        }
    }

    public int getRowNumber()
    {
        return rowNumber;
    }

    public int getColumnNumber()
    {
        return columnNumber;
    }
}
