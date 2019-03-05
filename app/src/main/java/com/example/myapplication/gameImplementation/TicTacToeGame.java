package com.example.myapplication.gameImplementation;

<<<<<<< HEAD
import android.widget.Button;

import java.util.ArrayList;
=======
import android.widget.Toast;
>>>>>>> 0126139d8d48ffeef19aa68f1f0b288bbe9395cb

public class TicTacToeGame {

    private MoveParser moveParser;
    private Board board;
    private Move recentMove = new Move(Symbol.BLANK, new Coord(0,0));
    private ArrayList<Button> boardPositions = new ArrayList<>();

    public TicTacToeGame() {
        board= new Board(3,3);
        moveParser = new MoveParser();
    }
    public TicTacToeGame(int m, int n, int k) {
        board= new Board(m,n);
        moveParser = new MoveParser();
    }

    public void addButtonPosition(Button buttonPosition){
        boardPositions.add(buttonPosition);
    }

    public String getMoveString() {
        return moveParser.parseMoveToString(recentMove);
    }

    public boolean parseMoveString(String moveString) {
        try {
            Move receivedMove = moveParser.parseStringToMove(moveString);
            return makeMove(receivedMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean makeMove(Move move) {
        boolean b;
        if(move.toString().equals(recentMove.toString())) {
            if(move.toString().equals(recentMove.toString())){
                b=false;
            } else {
                b = board.makeMove(move);
                recentMove = move;
            }
        } else {
            b=false;
        }
        return b;
    }
}
