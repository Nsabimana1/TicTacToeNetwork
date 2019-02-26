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
        Move receivedMove = null;
        try {
            receivedMove = moveParser.parseStringToMove(moveString);
            makeMove(receivedMove);
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
