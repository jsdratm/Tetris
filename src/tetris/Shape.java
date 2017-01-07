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
public class Shape {
    private Vector shapeVector;
    private int shapeCategory;
    private boolean isShapeStopped;
    private int shapeOrientation;
    
    public Shape()
    {
        isShapeStopped = false;
        shapeOrientation = 0;
        shapeVector = new Vector();
        changeShape(0);
        shapeCategory = 0;
    }

    public boolean canShapeMove()
    {
        return !isShapeStopped;
    }

    public void stopShape()
    {
        isShapeStopped = true;
    }

    public int getRotationRowShift(int index)
    {
        int returnVal = 0;
        
        if (shapeCategory == 1 || shapeCategory == 2)
        {
            if (shapeOrientation == 0 || shapeOrientation == 3)
            {
                returnVal = -1;
            }
            else if (shapeOrientation == 1 || shapeOrientation == 2)
            {
                returnVal = 1;
            }

            returnVal *= index;
        }
        else if (shapeCategory == 4 || (shapeCategory == 6 && index < 3))
        {
            if (index == 1)
            {
                if (shapeOrientation == 0 || shapeOrientation == 3)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 1 || shapeOrientation == 2)
                {
                    returnVal = 1;
                }
            }
            else if (index == 2)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -2;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 2;
                }
            }
            else if (index == 3)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -3;
                }
                else if (shapeOrientation == 1)
                {
                    returnVal = 1;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 3;
                }
            }
        }
        else if (shapeCategory == 5)
        {
            if (index == 1)
            {
                if (shapeOrientation == 0 || shapeOrientation == 3)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 1 || shapeOrientation == 2)
                {
                    returnVal = 1;
                }
            }
            else if (index == 2)
            {
                if (shapeOrientation == 1)
                {
                    returnVal = 2;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = -2;
                }
            }
            else if (index == 3)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 1)
                {
                    returnVal = 3;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 1;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = -3;
                }
            }
        }
        else if ((shapeCategory == 6 && index == 3))
        {
            if (shapeOrientation == 0 || shapeOrientation == 3)
            {
                returnVal = -2;
            }
            else if (shapeOrientation == 1 || shapeOrientation == 2)
            {
                returnVal = 2;
            }
        }

        return returnVal;
    }

    public int getRotationColumnShift(int index)
    {
        int returnVal = 0;
        if (shapeCategory == 1 || shapeCategory == 2)
        {
            if (shapeOrientation == 0 || shapeOrientation == 1)
            {
                returnVal = -1;
            }
            else if (shapeOrientation == 2 || shapeOrientation == 3)
            {
                returnVal = 1;
            }

            returnVal *= index;
        }
        else if (shapeCategory == 4 || (shapeCategory == 6 && index < 3))
        {
            if (index == 1)
            {
                if (shapeOrientation == 0 || shapeOrientation == 1)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 2 || shapeOrientation == 3)
                {
                    returnVal = 1;
                }
            }
            else if (index == 2)
            {
                if (shapeOrientation == 1)
                {
                    returnVal = -2;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = 2;
                }
            }
            else if (index == 3)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 1)
                {
                    returnVal = -3;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 1;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = 3;
                }
            }
        }
        else if (shapeCategory == 5)
        {
            if (index == 1)
            {
                if (shapeOrientation == 0 || shapeOrientation == 1)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 2 || shapeOrientation == 3)
                {
                    returnVal = 1;
                }
            }
            else if (index == 2)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -2;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 2;
                }
            }
            else if (index == 3)
            {
                if (shapeOrientation == 0)
                {
                    returnVal = -3;
                }
                else if (shapeOrientation == 1)
                {
                    returnVal = -1;
                }
                else if (shapeOrientation == 2)
                {
                    returnVal = 3;
                }
                else if (shapeOrientation == 3)
                {
                    returnVal = 1;
                }
            }
        }
        else if ((shapeCategory == 6 && index == 3))
        {
            if (shapeOrientation == 0 || shapeOrientation == 1)
            {
                returnVal = -2;
            }
            else if (shapeOrientation == 2 || shapeOrientation == 3)
            {
                returnVal = 2;
            }
        }
        
        return returnVal;
    }

    public void rotateShape()
    {
        if (shapeCategory != 0 && shapeCategory != 3)
        {
            for (int i = 0; i < shapeVector.size(); i++)
            {
                ((GridCoordinate)shapeVector.get(i)).shiftCoordinate(getRotationRowShift(i),
                                                                     getRotationColumnShift(i));
            }
            shapeOrientation++;
            if (shapeOrientation > 3)
            {
                shapeOrientation = 0;
            }
        }
    }

    public int getNumberOfCoordinates()
    {
        return shapeVector.size();
    }

    public GridCoordinate getCoordinate(int coordinateIndex)
    {
        return (GridCoordinate) shapeVector.get(coordinateIndex);
    }

    public void shiftShape(int deltaRow, int deltaColumn)
    {
        for (int i = 0; i < shapeVector.size(); i++)
        {
            ((GridCoordinate) (shapeVector.get(i))).shiftCoordinate(deltaRow, deltaColumn);
        }
    }

    public boolean isCoordinateInShape(int rowNumber, int columnNumber)
    {
        boolean returnVal = false;

        for(int i = 0; i < getNumberOfCoordinates(); i++)
        {
            if (getCoordinate(i).getRowNumber() == rowNumber && getCoordinate(i).getColumnNumber() == columnNumber)
            {
                returnVal = true;
                break;
            }
        }

        return returnVal;
    }

    public void changeShape(int shapeType)
    {
        shapeVector.clear();

        switch (shapeType)
        {
            // single block
            case 0:
                shapeVector.add(new GridCoordinate(0,6));
                break;
            // two block line
            case 1:
                shapeVector.add(new GridCoordinate(0,6));
                shapeVector.add(new GridCoordinate(0,7));
                break;
            // three block line
            case 2:
                shapeVector.add(new GridCoordinate(0,5));
                shapeVector.add(new GridCoordinate(0,6));
                shapeVector.add(new GridCoordinate(0,7));
                break;
            // four block square
            case 3:
                shapeVector.add(new GridCoordinate(0,5));
                shapeVector.add(new GridCoordinate(0,6));
                shapeVector.add(new GridCoordinate(1,5));
                shapeVector.add(new GridCoordinate(1,6));
                break;
            // left side lightning bolt
            case 4:
                shapeVector.add(new GridCoordinate(0,5));
                shapeVector.add(new GridCoordinate(0,6));
                shapeVector.add(new GridCoordinate(1,6));
                shapeVector.add(new GridCoordinate(1,7));
                break;
            // right side lightning bolt
            case 5:
                shapeVector.add(new GridCoordinate(1,4));
                shapeVector.add(new GridCoordinate(1,5));
                shapeVector.add(new GridCoordinate(0,5));
                shapeVector.add(new GridCoordinate(0,6));
                break;
            // T shape
            case 6:
                shapeVector.add(new GridCoordinate(0,4));
                shapeVector.add(new GridCoordinate(0,5));
                shapeVector.add(new GridCoordinate(1,5));
                shapeVector.add(new GridCoordinate(0,6));
                break;
            default:
                break;
        }

        shapeOrientation = 0;
        isShapeStopped = false;
        shapeCategory = shapeType;
    }
}
