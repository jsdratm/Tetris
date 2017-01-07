/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author jsdratm
 */
public class GridBox {
    boolean isFilled;

    public GridBox()
    {
        isFilled = false;
    }

    public boolean isFilled()
    {
        return isFilled;
    }

    public void setFilled()
    {
        isFilled = true;
    }

    public void setEmpty()
    {
        isFilled = false;
    }
}
