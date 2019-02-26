package com.example.myapplication.gameImplementation;

public class TicTacToeGame {

    private MoveParser moveParser;

    public TicTacToeGame() {
        moveParser = new MoveParser();
    }

    //TODO
    public String getMoveString() {
        return moveParser.parseMoveToString(new Move(new Coord(1,1), Symbol.X));
    }

    //TODO
    public void receiveMoveString(String moveString) {

    }

    //TODO
    private void parseMoveString() {

    }

    //TODO
    private boolean makeMove(Move move) {
        return true;
    }
}
