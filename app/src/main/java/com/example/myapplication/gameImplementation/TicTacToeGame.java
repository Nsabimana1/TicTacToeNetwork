package com.example.myapplication.gameImplementation;

import android.widget.Button;

import java.util.ArrayList;

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

    //TODO
    public void parseMoveString(String moveString) {
        Move receivedMove = null;
        try {
            receivedMove = moveParser.parseStringToMove(moveString);
            boolean moveMade = makeMove(receivedMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //TODO
    private boolean makeMove(Move move) {
        boolean b;
        if(move.toString().equals(recentMove.toString())) {
            b=false;
        } else {
            b=false;
        }
        return b;
    }
}
