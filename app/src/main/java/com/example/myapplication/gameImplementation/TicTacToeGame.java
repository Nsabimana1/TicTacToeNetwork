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
            //return makeMove(receivedMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean makeMove(Move move) {
        boolean moveMade;
        if(move.toString().equals(recentMove.toString())) {
            moveMade =  false;
        } else {
            if(board.makeMove(move)) {
                recentMove = move;
                moveMade =  true;
            } else {
                moveMade = false;
            }
        }
        return moveMade;
    }

    private WinState checkRowWin() {

        return WinState.NO_WIN;
    }
    private WinState checkColumnWin() {
        return WinState.NO_WIN;
    }
    private WinState checkDiagWin() {
        return WinState.NO_WIN;
    }

    public Move getRecentMove() {return recentMove;}
    public Board getBoard() {
        return board;
    }
    public MoveParser getMoveParser() {
        return moveParser;
    }
}
