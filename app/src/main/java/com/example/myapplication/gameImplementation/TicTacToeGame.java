package com.example.myapplication.gameImplementation;

public class TicTacToeGame {

    private MoveParser moveParser;
    private Board board;
    private Move recentMove = new Move(Symbol.BLANK, new Coord(0,0));

    public TicTacToeGame() {
        board= new Board(3,3);
        moveParser = new MoveParser();
    }
    public TicTacToeGame(int m, int n, int k) {
        board= new Board(m,n);
        moveParser = new MoveParser();
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
