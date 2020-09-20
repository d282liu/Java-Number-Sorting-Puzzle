//	File Name:   PuzzleStartingPoint.java
//	Names:       David Liu and Raymond Cao 
//	Class:       ICS3U1-11 (A)
//	Date:        December 19, 2018 
//	Description: This program recreates the popular 4 x 4 sliding number puzzle in Java.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PuzzleStartingPoint implements MouseListener
{
	// Constant declarations for allowSwap[] and randomDirection.
	final int TOP = 0;					 
	final int BOTTOM = 1;
	final int LEFT = 2;
	final int RIGHT = 3;
   Drawing draw = new Drawing();
   int [][] board = {{1,2,3,4},                    // 4X4 puzzle board 
                     {5,6,7,8},                    // 0 is the blank spot
                     {9,10,11,12},
                     {13,14,15,0}};
   ImageIcon[] boardPictures = new ImageIcon[16];  // stores the 16 tile pictures
   int blankRow, blankCol;
	
   public PuzzleStartingPoint()  // constructor
   {
      for (int i = 0; i < boardPictures.length; i++)
         boardPictures[i] = new ImageIcon(i + ".jpg");
      JFrame frame = new JFrame("Puzzle"); 
      frame.add(draw);
      draw.addMouseListener(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 440);
      initializeBoard();
      frame.setVisible(true);
   }

	//-----------------------------------------------------------------------------------------------------------------------
	
	// Method Name:   initializeBoard
	// Parameter(s):  None.
	// Return Value:  None.
	// Description:   Prepares the 4 x 4 board to allow the user to play.

   public void initializeBoard()    
   {
      // Shuffles the starting unscrambled board a set amount of times.
		
	   for (int shuffleCount = 0; shuffleCount < 30; shuffleCount++)		// Shuffles thirty times.
			shuffleBoard();
   } // initializeBoard
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	// Method Name:   shuffleBoard
	// Parameter(s):  None.
	// Return value:  None.
	// Description:   Randomly chooses a tile next to the blank and swaps their position.

   public void shuffleBoard() 
   {
		int randomDirection;							// Stores the direction of the swap. (TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3)
		boolean[] allowSwap = new boolean[4];	// Stores whether or not the given direction can be swapped with the blank. 
		findBlank();									// Determines location of the blank.
		
		
      // Allows or disallows directions around the blank to swap with the it, depending on its position.
		
		if (blankRow != 0)						// If the blank is not in the top row,
			allowSwap[TOP] = true; 		   	// The tile above the blank can be swapped.
         
		if (blankRow != 3)						// If the blank is not in the bottom row,      
			allowSwap[BOTTOM] = true;	 	 	// The tile below the blank can be swapped.
         	
		if (blankCol != 0)						// If the blank is not in the left column,      
			allowSwap[LEFT] = true;				// The tile left of the blank can be swapped.
         
		if (blankCol != 3)						// If the blank is not in the right column,
	   	allowSwap[RIGHT] = true;	  		// The tile right of the blank can be swapped.
 		
		
      // Randomly chooses a direction from the allowed directions to swap with the blank.
		
		do
		{
			 randomDirection = (int)(Math.random()*4);   // Randomly generates a direction.
		}	while (!allowSwap[randomDirection]);         // If the generated direction can not be swapped, 
																		// Then generate a new direction.
		
		
      // Swaps the tile in the selected direction with the blank.
		
		switch(randomDirection)
		{
			case TOP:                                                      // If the selected direction is above the blank,
				board[blankRow][blankCol] = board[blankRow-1][blankCol];    // Then swap the blank with the tile above it.
				board[blankRow-1][blankCol] = 0;
				break;
			case BOTTOM:                                                   // If the selected direction is below the blank,
				board[blankRow][blankCol] = board[blankRow+1][blankCol];	   // Then swap the blank with the tile below it.
				board[blankRow+1][blankCol] = 0;
				break;		                        
			case LEFT:                                                     // If the selected direction is left of the blank,
				board[blankRow][blankCol] = board[blankRow][blankCol-1];    // Then swap the blank with the tile left of it.
				board[blankRow][blankCol-1] = 0;
				break;
			case RIGHT:                                                    // If the selected direction is right of the blank,
				board[blankRow][blankCol] = board[blankRow][blankCol+1];    // Then swap the blank with the tile right of it.
				board[blankRow][blankCol+1] = 0;
		}
   } // shuffleBoard
	
	//-----------------------------------------------------------------------------------------------------------------------
	
   
	//-----------------------------------------------------------------------------------------------------------------------
	
	// Method Name:   findBlank
	// Parameter(s):  None.
	// Return value:  None.
	// Description:   Locates the blank within the board, and stores its row and column into blankRow and blankCol.
   
   public void findBlank()
   {
      // Searches for the blank.
		
		for (int row = 0; row < board.length; row++)			// Searches each row in the board.
		{
			for (int col = 0; col < board.length; col++)		// Searches each column in the selected row.
		   {
				if (board[row][col] == 0)								// If the selected tile is the blank,
			   {																
					blankRow = row;                              // Then store the value of the current row into blankRow
					blankCol = col;                              // And store the value of the current column into blankCol.
		      }
		   }
		}  
   } // findBlank
	
	//-----------------------------------------------------------------------------------------------------------------------
	

	//-----------------------------------------------------------------------------------------------------------------------
	
	// Method Name: 	moveTile
	// Parameter(s): 	row - The row of the clicked tile.
	//						col - The column of the clicked tile.
	// Return value: 	None.
	// Description: 	This method swaps the clicked tile with the blank if the clicked tile is next to it.
	
   public void moveTile(int row, int col)
   {
		findBlank();														// Determines the location of the blank.
		
		
		// Swaps the clicked tile with the blank if the clicked tile is next to it.
		
		if (col == blankCol)												// If the clicked tile is in the same column as the blank,
		{
			if (Math.abs (row-blankRow) == 1)						// And there is no row between the clicked tile and blank,
			{
				board[blankRow][blankCol] = board[row][col];		// Then swap the value of the blank with the clicked tile. 
				board[row][col] = 0;
			}
		}
		else if (row == blankRow)										// If the clicked tile is in the same row as the blank,
		{
			if (Math.abs (col-blankCol) == 1)						// And there is no column between the clicked tile and blank,
			{
				board[blankRow][blankCol] = board[row][col];		// Then swap the value of the blank with the clicked tile.
				board[row][col] = 0;			
			}
		}
		
      handleWin();														// After each move, check if the user has completed the puzzle.
															 
   } // moveTile
	
	//-----------------------------------------------------------------------------------------------------------------------
	

	//-----------------------------------------------------------------------------------------------------------------------
	
	// Method Name: 	handleWin
	// Parameter(s): 	None.
	// Return value: 	None.
	// Description: 	Checks whether the user has completely solved the puzzle, and sllows them to reshuffle if they have.  
	
	public void handleWin ()
	{
		boolean win = true;				// Stores whether or not the user has won.						
		int correctValue = 1;			// Stores what the tile values should be if the puzzle is completed.
		
		
		// Checks to see whether the user has completed the puzzle.
		
		for (int row = 0; row < 4; row++)						// Compares each row in the board with the correct value.
		{		
			for (int col = 0; col < 4; col++)					// Compares each column in the selected row with the correct value.
			{
            if (row != 3 || col != 3)						 	// If the selected tile is not the last tile, 
			      if (board[row][col] != correctValue) 		// And the selected tile is not the correct value,
                  win = false;									// Then the user has not solved the puzzle. 
				correctValue ++;										// After each comparison, increment the correct value.
			}
		}
		
		
		// Gives the user the choice to reshuffle the board if they have completed the puzzle.  
		
		if (win)																			// If the user has won, congragulate them, and give 
																							// the user the option to reshuffle the board.
			if (JOptionPane.showConfirmDialog(null, "You have won! Would you like to reshuffle the board?", "WINNER",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)		// If the user chooses to reshuffle the board,
				initializeBoard();													// Then reshuffle the board.
		} // handleWin
	
	//-----------------------------------------------------------------------------------------------------------------------
	

   class Drawing extends JComponent
   {
      public void paint(Graphics g)
      {
         for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++)
               g.drawImage(boardPictures[board[row][col]].getImage(),col * 100, row * 100,100,100,this);
      }
   }

// --> starting implementing MouseListener - it has 5 methods 
   public void mousePressed(MouseEvent e)
   {
   }
      
   public void mouseReleased(MouseEvent e)
   {
      // find coords of mouse click
      int row = e.getY()/100;
      int col = e.getX()/100;
       
      moveTile(row, col);
      
      // get paint to be called to reflect your mouse work
      draw.repaint();
   }
   
   public void mouseClicked(MouseEvent e)
   {
   }
   
   public void mouseEntered(MouseEvent e)
   {
   }
   
   public void mouseExited(MouseEvent e)
   {
   }
// finishing implementing MouseListener  <---
   public static void main(String[] args)
   {
      new PuzzleStartingPoint();
   }
}