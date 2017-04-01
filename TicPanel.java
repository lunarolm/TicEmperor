import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Olm on 11/27/2016.
 */

/*
public class TicPanel extends JPanel {

    private JPanel topThing;
    private JPanel grid;
    private JLabel currentPlayer;
    private ArrayList<JButton> buttonList;
    private String xo;
    private int lastClicked;

    //private ArrayList<JButton> buttonList;

    TicPanel(){

        this.setLayout(new BorderLayout());
        //buttonList = new ArrayList<>();
        topThing = new JPanel(new FlowLayout());
        grid = new JPanel(new GridLayout(3,3));
        currentPlayer = new JLabel("Current Player: ");
        buttonList = new ArrayList<>();
        xo = "X";
        lastClicked = 9;

        this.add(topThing, BorderLayout.PAGE_START);
        this.add(grid, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++){
            buttonList.add(new JButton(""));
            grid.add(buttonList.get(i));

            //changing the look of the grid
            buttonList.get(i).setFont(new Font("Tahoma", Font.BOLD, 100));
            buttonList.get(i).setBackground(Color.black);
            buttonList.get(i).setForeground(Color.red);

            //actionlistener for button
            buttonList.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tempButton = (JButton) e.getSource();

                    if (tempButton.getText() != "X" && tempButton.getText() != "O") {
                        tempButton.setText(xo);
                        lastClicked = buttonList.indexOf(tempButton);
                        System.out.println(lastClicked);
                    }
                }
            });
        }

        topThing.add(currentPlayer);

    }

    public void changePlayerLabel(String str){
        currentPlayer.setText(str);
    }

    public void changePlayer(int player){
        if (player == 1){
            xo = "X";
        }
        else if (player == 2){
            xo = "O";
        }
    }

    public void clickButton(int row, int col) throws IndexOutOfBoundsException{
        buttonList.get(coordToLin(row, col)).doClick();
    }

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

}

*/