/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.InputEvent;


class SimpleThread extends Thread {
    private Grid gridPointer;
    private Shape currentShape;
    private int nextFilledRow;
    private int shapeRowNumber;
    private int shapeColNumber;
    private boolean generateNewShape;
    Random randomNumberGenerator;

    public SimpleThread(Grid inputGrid, Shape inputShape)
    {
        gridPointer = inputGrid;
        currentShape = inputShape;
        generateNewShape = true;
        randomNumberGenerator = new Random();
    }
    public void run()
    {
	while (true)
        {
            gridPointer.protectGrid();

            // Check if a new shape needs to be created
            if (generateNewShape)
            {
                currentShape.changeShape(randomNumberGenerator.nextInt(7));
                generateNewShape = false;

                for(int i = 0; i < currentShape.getNumberOfCoordinates(); i++)
                {
                    if (gridPointer.isBoxFilled(currentShape.getCoordinate(i).getRowNumber(),
                                                currentShape.getCoordinate(i).getColumnNumber()))
                    {
                        gridPointer.clearGrid();
                        generateNewShape = true;
                        currentShape.stopShape();
                        break;
                    }
                    else
                    {
                        gridPointer.setBoxStatus(currentShape.getCoordinate(i).getRowNumber(),
                                                 currentShape.getCoordinate(i).getColumnNumber(),
                                                 true);
                    }
                }
            }
            // Otherwise move the existing shape down a row if possible
            else
            {
                if (gridPointer.collisionDetection(currentShape, 1, 0))
                {
                    generateNewShape = true;
                    currentShape.stopShape();
                }
                else
                {
                    // Erase the current shape
                    gridPointer.addRemoveShapeFromGrid(currentShape, false);

                    // Shift the shape down
                    currentShape.shiftShape(1, 0);

                    // Redraw the shape
                    gridPointer.addRemoveShapeFromGrid(currentShape, true);
                }
            }

            // Scan for filled rows and remove them if found
            nextFilledRow = gridPointer.findNextFilledRow();
            while (nextFilledRow != -1)
            {
                gridPointer.ClearRow(nextFilledRow);
                generateNewShape = true;
                nextFilledRow = gridPointer.findNextFilledRow();
            }

            gridPointer.unprotectGrid();
            
            // Wait 100ms
            try {
		sleep(150);
	    }
            catch (InterruptedException e)
            {
                // Do nothing
            }
	}
    }
}


/**
 *
 * @author jsdratm
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int gridBoxSize = 25;
        int gridBoxOutlineSize = 3;
        int numberOfRows = 25;
        int numberOfColumns = 14;
        final Shape droppingShape = new Shape();
        final Grid tetrisGrid = new Grid(numberOfRows, numberOfColumns);
        new SimpleThread(tetrisGrid, droppingShape).start();

        JFrame mainFrame = new JFrame("Tetris by Dunderware");
        mainFrame.setResizable(false);
        GraphicsEngine graphicsEngine = new GraphicsEngine();
        graphicsEngine.setGridReference(tetrisGrid);
        graphicsEngine.setBoxSize(gridBoxSize);
        graphicsEngine.setBoxOutlineSize(gridBoxOutlineSize);

        mainFrame.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                int keyCode = e.getKeyCode();

                if (!tetrisGrid.isGridProtected())
                {
                    // left arrow key
                    if (keyCode == KeyEvent.VK_LEFT)
                    {
                        if (!tetrisGrid.collisionDetection(droppingShape, 0, -1) &&
                            droppingShape.canShapeMove())
                        {
                            // Erase the current shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, false);

                            // Shift the shape down
                            droppingShape.shiftShape(0, -1);

                            // Redraw the shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, true);
                        }
                    }
                    // right arrow key
                    else if (keyCode == KeyEvent.VK_RIGHT)
                    {
                        if (!tetrisGrid.collisionDetection(droppingShape, 0, 1) &&
                            droppingShape.canShapeMove())
                        {
                            // Erase the current shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, false);

                            // Shift the shape down
                            droppingShape.shiftShape(0, 1);

                            // Redraw the shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, true);
                        }
                    }
                    // down arrow key
                    else if (keyCode == KeyEvent.VK_DOWN)
                    {
                        while (!tetrisGrid.collisionDetection(droppingShape, 1, 0))
                        {
                            // Erase the current shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, false);

                            // Shift the shape down
                            droppingShape.shiftShape(1, 0);

                            // Redraw the shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, true);
                        }
                    }
                    // up arrow key
                    else if (keyCode == KeyEvent.VK_UP)
                    {
                        if (!tetrisGrid.collisionDetectionRotation(droppingShape) &&
                            droppingShape.canShapeMove())
                        {
                            // Erase the current shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, false);

                            // Rotate the shape
                            droppingShape.rotateShape();

                            // Redraw the shape
                            tetrisGrid.addRemoveShapeFromGrid(droppingShape, true);
                        }
                    }
                }
            }
        });

        mainFrame.getContentPane().add(graphicsEngine);
        mainFrame.setSize((gridBoxSize * numberOfColumns) + 6,
                          (gridBoxSize * numberOfRows) + 28);
        //mainFrame.pack();
        mainFrame.setVisible(true);

        while (true)
        {
            mainFrame.repaint();
            //tetrisGrid.printGridValues();
            try {
		Thread.currentThread().sleep(50);
	    }
            catch (InterruptedException e)
            {

            }
        }
    }

}