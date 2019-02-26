package com.example.myapplication.gameImplementation;

public class TicTacToeGame {

    private MoveParser moveParser;
    private Board board;
    private Move recentMove = null;

    public TicTacToeGame() {
        board= new Board(3,3);
        moveParser = new MoveParser();
    }
    public TicTacToeGame(int m, int n, int k) {
        board= new Board(m,n);
        moveParser = new MoveParser();
    }

    //TODO
    public String getMoveString() {
        return moveParser.parseMoveToString(new Move(Symbol.X, new Coord(1,1)));
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
