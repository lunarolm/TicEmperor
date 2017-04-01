/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {  // save as Cell.java
    
   

   
    // Private 
   private int  content; // content of this cell: Could be any of the three 
                 // EMPTY, CROSS, or NOUGHT.
   
   private int row, col; // row and column of this cell, not used in this program
 
   /** Constructor to initialize this cell */
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      clear();  // clear content
   }
 
   /** Clear the cell content to EMPTY */
   public void clear() {
      content = Symbols.EMPTY;
   }
   
   /** Mutator for Content. 
    * Set the cell content to an appropriate value supplied in v. */
   public void setContent(int v){
       switch (v) {
         case Symbols.CROSS:  content = Symbols.CROSS; break;
         case Symbols.NOUGHT: content = Symbols.NOUGHT; break;
         case Symbols.EMPTY:  content = Symbols.EMPTY; break;
             
         default : content = Symbols.EMPTY; // at this moment EMPTY; but, more  
                                    // appropriate through an exception or enum.
      }
   }
 
   /** Accessor method for content
     * @return  */
   
   public int getContent(){
        return content;
    
    }

    
   /** Paint itself */
   public void paint() {
      switch (content) {
         case Symbols.CROSS:  System.out.print(" X "); break;
         case Symbols.NOUGHT: System.out.print(" O "); break;
         case Symbols.EMPTY:  System.out.print("   "); break;
             
         default: ; // should throw an exception, because this should not
                    // have happend. Cell content should not have any values
                    // other than those three; (integrity of the object is 
                    // compromised).
      }
   }
}