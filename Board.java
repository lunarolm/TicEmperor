/**
 * The Board class models the game-board.
 */
public class Board {  // save as Board.java
   // Named-constants for the dimensions
   private static final int ROWS = 3;
   private static final int COLS = 3;
 
   // package access
   private Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances
   private int currentRow, currentCol;  // the current symbol's row and column
 
   /** Constructor to initialize the game board */
   public Board() {
      cells = new Cell[ROWS][COLS];  // allocate the array
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(row, col); // allocate element of the array
         }
      }
   }
 
   public int getRows()
   {
       return ROWS;
   }
   
   public int getCols()
   {
       return COLS;
   }
   
   
   public boolean isValidCell(int row, int col){
       
   
       if (row >= 0 && row < ROWS && col >= 0 && col < COLS
               && cells[row][col].getContent() == Symbols.EMPTY) {
           return true;
       }
            
       return false;
         
   }

    public boolean setSymbolAt(int theSymbol, int row, int col){
        
        if(isValidCell(row, col)){
            currentRow = row;
            currentCol = col;
            
            cells[row][col].setContent(theSymbol);
            
            return true;
        }

        return false;
    }
           
           
   /** Initialize (or re-initialize) the contents of the game board */
   public void init() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].clear();  // clear the cell content
         }
      }
   }
 
   /** Return true if it is a draw (i.e., no more EMPTY cell) */
   public boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].getContent() == Symbols.EMPTY) {
               return false; // an empty seed found, not a draw, exit
            }
         }
      }
      return true; // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public boolean hasWon(int theSeed) {
      return (cells[currentRow][0].getContent() == theSeed         // 3-in-the-row
                   && cells[currentRow][1].getContent() == theSeed
                   && cells[currentRow][2].getContent() == theSeed
              || cells[0][currentCol].getContent() == theSeed      // 3-in-the-column
                   && cells[1][currentCol].getContent() == theSeed
                   && cells[2][currentCol].getContent() == theSeed
              || currentRow == currentCol            // 3-in-the-diagonal
                   && cells[0][0].getContent() == theSeed
                   && cells[1][1].getContent() == theSeed
                   && cells[2][2].getContent() == theSeed
              || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                   && cells[0][2].getContent() == theSeed
                   && cells[1][1].getContent() == theSeed
                   && cells[2][0].getContent() == theSeed);
   }
 
   
   /** Paint itself */
   public void paint() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].paint();   // each cell paints itself
            if (col < COLS - 1) System.out.print("|");
         }
         System.out.println();
         if (row < ROWS - 1) {
            System.out.println("-----------");
         }
      }
      System.out.println("\n");
   }
}