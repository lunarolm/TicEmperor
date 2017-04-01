import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Made by Travis "Olm" Bellmore
 * Finished December 1st, 20x6
 */
public class GameController{
    
   private Board board;            // the game board
   private int currentState; // the current state of the game (of enum GameState)
   private int currentPlayer;     // the current player (of enum Symbol)
   private int difficulty; // difficulty of game
   private TicEmperor tic9000;
   private final int BOARD_WIDTH;
   private final int BOARD_HEIGHT;

   //gui stuff
   private JFrame window;
   private TicPanel mainPanel;
   private MenuPanel menu;

   /************************************Conversion Functions*****************************************/
   //linear system to coordinate conversion
   private int[] linToCoord(int i){
      int[] tempCoords = new int[2];

      switch (i){
         case 0:
            tempCoords[0] = 0;
            tempCoords[1] = 0;
            break;
         case 1:
            tempCoords[0] = 0;
            tempCoords[1] = 1;
            break;
         case 2:
            tempCoords[0] = 0;
            tempCoords[1] = 2;
            break;
         case 3:
            tempCoords[0] = 1;
            tempCoords[1] = 0;
            break;
         case 4:
            tempCoords[0] = 1;
            tempCoords[1] = 1;
            break;
         case 5:
            tempCoords[0] = 1;
            tempCoords[1] = 2;
            break;
         case 6:
            tempCoords[0] = 2;
            tempCoords[1] = 0;
            break;
         case 7:
            tempCoords[0] = 2;
            tempCoords[1] = 1;
            break;
         case 8:
            tempCoords[0] = 2;
            tempCoords[1] = 2;
            break;

      }

      return tempCoords;
   }
   //coordinate system to linear conversion
   private int coordToLin(int row, int col){

      if (row == 0){
         if(col == 0){
            return 0;
         }
         else if (col == 1){
            return 1;
         }
         else if(col == 2){
            return 2;
         }
      }
      else if (row == 1){
         if(col == 0){
            return 3;
         }
         else if (col == 1){
            return 4;
         }
         else if(col == 2){
            return 5;
         }
      }
      else if (row == 2){
         if(col == 0){
            return 6;
         }
         else if (col == 1){
            return 7;
         }
         else if(col == 2){
            return 8;
         }
      }
      System.err.println("coordToLin returned invalid index");
      return 9;

   }
 
   /** Constructor to setup the game */
   public GameController() {
      BOARD_HEIGHT = 600;
      BOARD_WIDTH = 800;

      board = new Board();  // allocate game-board
      tic9000 = new TicEmperor(); // new AI
      window = new JFrame("TicEmperor");//window holding the game
      mainPanel = new TicPanel();
      menu = new MenuPanel();

      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      window.setVisible(true);
      window.setSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

      window.add(menu);


   }

   public void start(){
      
      // Initialize the game-board and current status
      initGame();
      updateGame(currentPlayer); // update currentState
   }

   private void switchPlayer(){
      currentPlayer = (currentPlayer == Symbols.CROSS) ? Symbols.NOUGHT : Symbols.CROSS;
      mainPanel.changePlayer(currentPlayer);
   }

   /** Initialize the game-board contents and the current states */
   private void initGame() {

      board.init();  // clear the board contents
      currentPlayer = Symbols.CROSS;       // CROSS plays first
      currentState = GameState.PLAYING; // ready to play

   }
 
   /** The player with "theSymbol" makes one move, with input validation.
       Update Cell's content, Board's currentRow and currentCol. */

   private void initialMove(){
      if(difficulty == 0){
         playerMove();
      }
      else if (difficulty == 1){
         hardMove();
      }
   }

   private void playerMove(){

      if(currentState == 0) {
         boolean validInput = false;
         int row = 0;
         int col = 0;

         while (!validInput) {
            row = tic9000.randCoord();
            col = tic9000.randCoord();

            if (board.isValidCell(row, col)) {
               validInput = true;
            }
         }

         board.setSymbolAt(currentPlayer, row, col);
         mainPanel.setButtonByCoord(currentPlayer, row, col);
         updateGame(currentPlayer);
         switchPlayer();
      }
   }

   /**movement set for HARD difficulty; created by Travis sometime in 2006(?)*/
   private void hardMove(){

      if (currentState ==0) {

         boolean validInput = false;
         int row = 0;
         int col = 0;
         while(!validInput) {
            int[] coords = tic9000.play();
            row = coords[0];
            col = coords[1];

            if(board.isValidCell(row, col)){
               validInput = true;
            }
         }
         board.setSymbolAt(currentPlayer, row, col);
         mainPanel.setButtonByCoord(currentPlayer, row, col);
         updateGame(currentPlayer);
         switchPlayer();
      }
   }

 
   /** Update the currentState after the player with "theSymbol" has moved */
   private void updateGame(int theSymbol) {
      if (board.hasWon(theSymbol)) {  // check for win
         currentState = (theSymbol == Symbols.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (board.isDraw()) {  // check for draw
         currentState = GameState.DRAW;
      }
      // Otherwise, no change to current state (still GameState.PLAYING).
      //prints the current victor to the top of the mainPanel
      if(currentState == 0){
         mainPanel.changeTopLabel("GAME PLAYING");
      }
      else if (currentState == 2){
         mainPanel.changeTopLabel("X IS VICTORIOUS");
      }
      else if (currentState == 3){
         mainPanel.changeTopLabel("O IS VICTORIOUS");
      }
      else if (currentState == 1){
         mainPanel.changeTopLabel("DRAW");
      }
   }


   /***********************************************MenuPanel***********************************************************/
   private class MenuPanel extends JPanel{
      private JPanel topImage;
      private JPanel difficultySelect;

      private JLabel title;
      private JLabel imageLabel;
      private ImageIcon image;
      private ArrayList<JButton> difficultyButtons;

      public MenuPanel() throws RuntimeException{
         this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

         topImage = new JPanel();
         //topImage.setLayout(new BoxLayout(topImage, BoxLayout.Y_AXIS));
         topImage.setLayout(new FlowLayout());
         difficultySelect = new JPanel(new FlowLayout());
         difficultyButtons = new ArrayList<>(2);
         difficultyButtons.add(new JButton("Easy"));
         difficultyButtons.add(new JButton("Impossible"));

         image = new ImageIcon(getClass().getResource("eye3.png"));
         imageLabel = new JLabel(image);
         title = new JLabel("   Tic Emperor");

         //adding elements to the menu
         this.add(title);//title

         this.add(topImage);//logo
         topImage.add(imageLabel);

         this.add(difficultySelect);

         for(JButton ele: difficultyButtons){
            difficultySelect.add(ele);
         }

         //formatting
         title.setFont(new Font("monospace", Font.BOLD, 50));
         title.setForeground(Color.red);
         topImage.setBackground(Color.black);
         this.setBackground(Color.black);

         difficultySelect.setBackground(Color.black);
         for(JButton ele: difficultyButtons){
            ele.setBackground(Color.black);
            ele.setForeground(Color.red);
            ele.setPreferredSize(new Dimension(100,50));
            ele.setFont(new Font("monospace", Font.BOLD, 12));

            //event handler lambda for buttons; selects difficulty mode
            ele.addActionListener(e -> {
               JButton thisButton = (JButton) e.getSource();

               int i = difficultyButtons.indexOf(thisButton);
               difficulty = i;
               menu.setVisible(false);
               window.add(mainPanel);
               mainPanel.setVisible(true);

               initialMove();
               //board.paint();

            });
         }
      }
   }
   /***********************************************TicPanel***********************************************************/
   /**
    * Created by Olm on 11/27/2016.
    */
   private class TicPanel extends JPanel {

      private JPanel topThing;
      private JPanel grid;
      private JLabel topLabel;
      private ArrayList<JButton> buttonList;
      private JButton reset;
      private String xo;

      TicPanel(){

         this.setLayout(new BorderLayout());
         //buttonList = new ArrayList<>();
         topThing = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 5));
         grid = new JPanel(new GridLayout(3,3));
         topLabel = new JLabel("");
         reset = new JButton("Reset Game");
         buttonList = new ArrayList<>();
         xo = "O";

         this.add(topThing, BorderLayout.PAGE_START);
         this.add(grid, BorderLayout.CENTER);
         topThing.add(topLabel);
         topThing.add(reset);

         //formatting
         topThing.setSize(new Dimension(BOARD_WIDTH, 50));
         topLabel.setForeground(Color.red);
         topThing.setBackground(Color.black);

         reset.setBackground(Color.black);
         reset.setForeground(Color.RED);

         for (int i = 0; i < 9; i++){
            buttonList.add(new JButton(""));
            grid.add(buttonList.get(i));

            //changing the look of the grid
            buttonList.get(i).setFont(new Font("Tahoma", Font.BOLD, 100));
            buttonList.get(i).setBackground(Color.black);
            buttonList.get(i).setForeground(Color.red);

            //actionlistener for button
            buttonList.get(i).addActionListener(e -> {//lambda function replaces anonymous class
               JButton tempButton = (JButton) e.getSource();

               if (tempButton.getText() != "X" && tempButton.getText() != "O" && currentState == 0) {
                  tempButton.setText(theSymbolToXO(currentPlayer));

                  int[] tempCoords = linToCoord(buttonList.indexOf(tempButton));
                  //System.out.println(Arrays.toString(tempCoords));

                  board.setSymbolAt(currentPlayer, tempCoords[0], tempCoords[1]);
                  updateGame(currentPlayer);
                  switchPlayer();

                  if(difficulty == 0) {
                     playerMove();
                  }
                  else if (difficulty == 1){
                     tic9000.analyze(tempCoords[0],tempCoords[1]);
                     hardMove();
                  }
                  //board.paint();
               }
            });
         }

         //actionlistening lambda function for reset button
         reset.addActionListener(e ->{
            board.init();
            start();
            //board.paint();

            for(JButton ele: buttonList){
               ele.setText("");
            }
            currentState = 0;
            tic9000.reset();
            initialMove();

         });

      }

      //changes the label at the top that declares the game state
      public void changeTopLabel(String str){
         topLabel.setText(str);
      }

      //decides which symbol to print on the tiles
      public void changePlayer(int player){
         if (player == 1){
            xo = "X";
         }
         else if (player == 2){
            xo = "O";
         }
      }

      //depreciated function that allows the AI to click buttons
      public void clickButton(int row, int col) throws IndexOutOfBoundsException{
         buttonList.get(coordToLin(row, col)).doClick();
      }

      //conversion of Dr Rahman's theSymbol enumeration to string
      private String theSymbolToXO(int theSymbol){
         if(theSymbol == 1){
            return "X";
         }
         else if(theSymbol == 2){
            return "O";
         }
         return "";
      }

      //the new method by which the AI sets buttons
      public void setButtonByCoord(int theSymbol ,int row, int col){
         buttonList.get(coordToLin(row, col)).setText(theSymbolToXO(theSymbol));
      }


   }/***TicPanel End**/


   /** The entry main() method */
   public static void main(String[] args) {
      new GameController().start();  // Lets start the game...
   }
}
