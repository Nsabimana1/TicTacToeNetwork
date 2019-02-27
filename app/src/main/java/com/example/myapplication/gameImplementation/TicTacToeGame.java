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

    //TODO
    public void receiveMoveString(String moveString) {
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
