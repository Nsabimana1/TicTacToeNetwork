package com.example.myapplication.gameImplementation;

public class TicTacToeGame {

    private MoveParser moveParser;

    public TicTacToeGame() {
        moveParser = new MoveParser();
    }


    //TODO
    public String makeMoveString(Move move) {
        return moveParser.parseMoveToString(move);
    }

    //TODO
    public void recieveMoveString(String moveString) {
        
    }

    //TODO
    private boolean makeMove() {
        return true;
    }
}
