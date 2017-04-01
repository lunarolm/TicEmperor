/**
 * Created by Olm on 10/13/2006.
 * Updated 12/01/2006 to fix exploit
 */
import java.util.Random;

public class TicEmperor {
    private int[][] boardState;
    private byte gameTurn;
    private byte gameState;

    //constructor
    public TicEmperor() {
        boardState = new int[3][3]; //a reference for what tiles have what pieces in them
        gameTurn = 0; //how many turns have elapsed
        gameState = 0; //keeps track of the branching path the game has taken
    }

    //this is where I hardcode the entire decision tree because I couldn't think of a more elegant way
    public int[] play(){
        int[] temp = new int[2];
        if (this.gameTurn ==0){ //turn 0 block
            temp[0] = 2;
            temp[1] = 0;
            boardState[temp[0]][temp[1]]=1;
        }
        if(this.gameTurn ==1){ //turn 1 block
            //modification for side exploits
            if(boardState[2][1] == 2 || boardState[0][1] == 2){
                temp[0] = 1;
                temp[1] = 1;
                boardState[temp[0]][temp[1]]=1;
                gameState = 11;// player has chosen bottom/top side
            }
            else if(boardState[1][2] == 2){
                temp[0] = 1;
                temp[1] = 1;
                boardState[temp[0]][temp[1]]=1;
                gameState = 12;// player has chosen right side
            }
            else if(boardState[1][1]!=2){
                if(boardState[2][2] !=2){
                    temp[0] = 2;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;
                }
                else{
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1;
                    gameState = 1;// lower left and upper left occupied
                }
            }
            else{
                temp[0] = 0;
                temp[1]= 2;
                boardState[temp[0]][temp[1]]=1;
                gameState = 2; // lower left and upper right occupied
            }
        }
        if(this.gameTurn == 2){ //turn 2 block
            if(gameState == 0){ //state 0 block
                if(boardState[2][1] !=2){
                    temp[0] = 2;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; //END GAME
                }
                else if( boardState[0][2] !=2){
                    temp[0] = 0;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1; // bottom left, bottom right, top right taken
                }
                else{
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1;
                    gameState = 3; // bottom left, bottom right, top left taken
                }
            }
            if(gameState == 1){//state 1 block
                if(boardState[1][0] != 2){
                    temp[0] = 1;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1; //END GAME
                }
                else if(boardState[0][2] !=2){
                    temp[0] = 0;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;
                    gameState = 4;// top left and top right corners taken, also bottom left
                }
            }
            if(gameState == 2){//state 2 block
                if(boardState[0][0] == 2 || boardState[2][2] == 2){
                    if(boardState[0][0] != 2){
                        temp[0] = 0;
                        temp[1] = 0;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 5; // top left, top right, bottom left
                    }
                    else{
                        temp[0] = 2;
                        temp[1] = 2;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 6; // bottom right, left, and top right
                    }
                }
                else{ // tie condition activated
                    if(boardState[0][1] == 2) {
                        temp[0] = 2;
                        temp[1] = 1;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 7; //top right, bottom left, and bottom held
                    }
                    else if(boardState[2][1] == 2){
                        temp[0] = 0;
                        temp[1] = 1;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 8; //top right, bottom left, and top held
                    }
                    else if(boardState[1][0] == 2){
                        temp[0] = 1;
                        temp[1] = 2;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 9; //top right, bottom left, and right held
                    }
                    else if(boardState[1][2] == 2){
                        temp[0] = 1;
                        temp[1] = 0;
                        boardState[temp[0]][temp[1]]=1;
                        gameState = 10; //top right, bottom left, and left held
                    }
                }
            }
            if(gameState == 11){//state 11 block
                if(boardState[0][2] != 2) {
                    temp[0] = 0;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;//this is an end condition
                }
                else{
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1;
                }
            }
            if(gameState == 12){//state 12 block
                if(boardState[0][2] != 2) {
                    temp[0] = 0;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;//this is an end condition
                }
                else{
                    temp[0] = 2;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;
                }
            }
        }
        if(this.gameTurn == 3){//turn 3 block
            if(gameState == 0){//gamestate 0 block
                if(boardState[1][2] !=2){
                    temp[0] = 1;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else if (boardState[1][1] !=2){
                    temp[0] = 1;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
            }
            if(gameState == 3){//gamestate 3 block, state 0 threads end
                if(boardState[1][1] !=2){
                    temp[0] = 1;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else if (boardState[1][0] !=2){
                    temp[0] = 1;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
            }
            if(gameState == 4){//gamestate 4 block; all possible non-centre 1 threads end
                if(boardState[1][1] !=2){
                    temp[0] = 1;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else if (boardState[0][1] !=2){
                    temp[0] = 0;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
            }
            if(gameState == 5){//gamestate 5; all end
                if(boardState[1][0] !=2){
                    temp[0] = 1;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else if (boardState[0][1] !=2){
                    temp[0] = 0;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
            }
            if(gameState == 6){//gamestate 6; all end
                if(boardState[1][2] !=2){
                    temp[0] = 1;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else if (boardState[2][1] !=2){
                    temp[0] = 2;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
            }//tie imminent, final logic
            if(gameState == 7 || gameState == 9){
                if(boardState[2][2] !=2){
                    temp[0] = 2;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else{
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1;
                }
            }
            if(gameState == 8 || gameState == 10){
                if(boardState[0][0] !=2){
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else{
                    temp[0] = 2;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;
                }
            }
            if(gameState == 11){//gamestate 11 block
                if(boardState[1][0] !=2){
                    temp[0] = 1;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1; // END GAME
                }
                else{
                    temp[0] = 2;
                    temp[1] = 2;
                    boardState[temp[0]][temp[1]]=1;//end game
                }
            }
            if(gameState == 12){//state 12 block
                if(boardState[0][0] != 2) {
                    temp[0] = 0;
                    temp[1] = 0;
                    boardState[temp[0]][temp[1]]=1;//end game
                }
                else{
                    temp[0] = 2;
                    temp[1] = 1;
                    boardState[temp[0]][temp[1]]=1;//end game
                }
            }
        }
        if(this.gameTurn >= 4){ //all threads end, fill remaining squares until the game ties
            temp[0] = randCoord();
            temp[1] = randCoord();
        }
        return temp;
    }

    //keeps track of the player's moves
    public void analyze(int row, int col){
        boardState[row][col] = 2;
        gameTurn++;
    }

    //random coordinate function; used for easy mode
    public static int randCoord(){
        Random rand = new Random();
        return rand.nextInt(3);
    }

    //resets the bot
    public void reset(){
        boardState = new int[3][3];
        gameState = 0;
        gameTurn = 0;
    }

}
