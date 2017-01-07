/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.util.Vector;

/**
 *
 * @author jsdratm
 */
public class Grid
{

    private boolean gridMutexProtect;
    private int numRows;
    private int numColumns;

    private Vector gridVector[];

    public Grid(int startNumRows, int startNumColumns)
    {
        gridMutexProtect = false;
        numRows = startNumRows;
        numColumns = startNumColumns;

        gridVector = new Vector[numColumns];
        for (int i = 0; i < numColumns; i++)
        {
            gridVector[i] = new Vector(numRows);
            for (int j = 0; j < numRows; j++)
            {
                GridBox newBox = new GridBox();
                gridVector[i].addElement(newBox);
            }
        }
    }

    public void protectGrid()
    {
        gridMutexProtect = true;
    }

    public void unprotectGrid()
    {
        gridMutexProtect = false;
    }

    public boolean isGridProtected()
    {
        return gridMutexProtect;
    }

    public int getNumberOfRows()
    {
        return numRows;
    }

    public int getNumberOfColumns()
    {
        return numColumns;
    }

    public void addRemoveShapeFromGrid(Shape inputShape, boolean addShape)
    {
        for(int i = 0; i < inputShape.getNumberOfCoordinates(); i++)
        {
            setBoxStatus(inputShape.getCoordinate(i).getRowNumber(),
                         inputShape.getCoordinate(i).getColumnNumber(),
                         addShape);
        }
    }

    public boolean collisionDetectionRotation(Shape inputShape)
    {
        boolean returnVal = false;

        for(int i = 0; i < inputShape.getNumberOfCoordinates(); i++)
        {
            int newRowNumber =  inputShape.getCoordinate(i).getRowNumber() + inputShape.getRotationRowShift(i);
            int newColumnNumber = inputShape.getCoordinate(i).getColumnNumber() + inputShape.getRotationColumnShift(i);
            if (inputShape.isCoordinateInShape(newRowNumber, newColumnNumber))
            {
                // no collision since coordinate is already occupied by shape
            }
            else
            {
                // check if the row or number is invalid
                if (newRowNumber >= numRows || newColumnNumber >= numColumns || newRowNumber < 0 || newColumnNumber < 0)
                {
                    // Collision with grid boundary detected
                    returnVal = true;
                }
                else if (isBoxFilled(newRowNumber, newColumnNumber))
                {
                    // Collision with existing blocks detected
                    returnVal = true;
                }
            }
        }

        return returnVal;
    }

    public boolean collisionDetection(Shape inputShape, int deltaRows, int deltaColumns)
    {
        boolean returnVal = false;

        for(int i = 0; i < inputShape.getNumberOfCoordinates(); i++)
        {
            int newRowNumber =  inputShape.getCoordinate(i).getRowNumber() + deltaRows;
            int newColumnNumber = inputShape.getCoordinate(i).getColumnNumber() + deltaColumns;
            if (inputShape.isCoordinateInShape(newRowNumber, newColumnNumber))
            {
                // no collision since coordinate is already occupied by shape
            }
            else
            {
                // check if the row or number is invalid
                if (newRowNumber >= numRows || newColumnNumber >= numColumns || newRowNumber < 0 || newColumnNumber < 0)
                {
                    // Collision with grid boundary detected
                    returnVal = true;
                }
                else if (isBoxFilled(newRowNumber, newColumnNumber))
                {
                    // Collision with existing blocks detected
                    returnVal = true;
                }
            }
        }

        return returnVal;
    }

    public boolean isBoxFilled(int rowNumber, int columnNumber)
    {
        GridBox boxReference = (GridBox) gridVector[columnNumber].get(rowNumber);
        return boxReference.isFilled();
    }

    public void setBoxStatus(int rowNumber, int columnNumber, boolean setFill)
    {
        GridBox boxReference = (GridBox) gridVector[columnNumber].get(rowNumber);
        if (setFill)
        {
            boxReference.setFilled();
        }
        else
        {
            boxReference.setEmpty();
        }

        gridVector[columnNumber].set(rowNumber, boxReference);
    }

    public void ClearRow(int rowNumber)
    {
        for (int i = rowNumber; i >= 0; i--)
        {
            if (i > 0)
            {
                for (int j = 0; j < numColumns; j++)
                {
                    setBoxStatus(i, j, (isBoxFilled(i - 1, j)));
                }
            }
            else
            {
                for (int j = 0; j < numColumns; j++)
                {
                    setBoxStatus(i, j, false);
                }
            }
        }
    }

    public int findNextFilledRow()
    {
        int returnVal = -1;
        boolean isThisRowFilled = true;

        for (int i = 0; i < numRows; i++)
        {
            isThisRowFilled = true;
            
            for (int j = 0; j < numColumns; j++)
            {
                if (isBoxFilled(i, j))
                {
                    
                }
                else
                {
                    isThisRowFilled = false;
                }

                if (j == (numColumns - 1) && isThisRowFilled)
                {
                    returnVal = i;
                }
            }
        }
        
        return returnVal;
    }

    public void clearGrid()
    {
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                setBoxStatus(i, j, false);
            }
        }
    }

    public void printGridValues()
    {
        String outputString = "";
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                if (isBoxFilled(i, j))
                {
                    outputString += " Y";
                }
                else
                {
                    outputString += " N";
                }
            }
            System.out.println(outputString);
            outputString = "";
        }
        System.out.println("              ");
    }

}