package com.example.myapplication.gameImplementation;

public class TicTacToeGame {

    private MoveParser moveParser;
    private Board board;
    private Move recentMove = new Move(Symbol.BLANK, new Coord(0,0));

    public TicTacToeGame() {
        board= new Board(3,3);
        moveParser = new MoveParser();
    }

    public String getMoveString() {
        return moveParser.parseMoveToString(recentMove);
    }

    public String parseMoveString(String moveString) {
        String status = "[PH]";
        Move receivedMove = moveParser.parseStringToMove(moveString);
        boolean moveMade =  makeMove(receivedMove);
        if(moveMade) {
            status = receivedMove.toString();
        } else {
            status = "Error";
        }
        return status;
    }

    public boolean makeMove(Move move) {
        boolean moveMade;
        if(checkWin()==WinState.NO_WIN) {
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
        } else {
            moveMade = false;
        }
        return moveMade;
    }

    public WinState checkWin() {
        if(checkColumnWin()!=WinState.NO_WIN)
            return checkColumnWin();
        else if(checkRowWin()!=WinState.NO_WIN)
            return checkRowWin();
        else if(checkDiagWin()!=WinState.NO_WIN)
            return checkDiagWin();
        return WinState.NO_WIN;
    }

    private WinState checkRowWin() {
        for(int i = 0; i<3;i++) {
            if(board.getBoardArray()[0][i] == board.getBoardArray()[1][i] && board.getBoardArray()[1][i] == board.getBoardArray()[2][i]) {
                if(board.getBoardArray()[0][i]!=Symbol.BLANK)
                    return board.getBoardArray()[0][i].getWinner();
            }
        }
        return WinState.NO_WIN;
    }
    private WinState checkColumnWin() {
        for(int i = 0; i<3;i++) {
            if(board.getBoardArray()[i][0] == board.getBoardArray()[i][1] && board.getBoardArray()[i][1] == board.getBoardArray()[i][2]) {
                if(board.getBoardArray()[i][0]!=Symbol.BLANK)
                    return board.getBoardArray()[i][0].getWinner();
            }
        }
        return WinState.NO_WIN;
    }
    private WinState checkDiagWin() {
        if(board.getBoardArray()[0][0] == board.getBoardArray()[1][1] && board.getBoardArray()[1][1] == board.getBoardArray()[2][2]) {
            if(board.getBoardArray()[0][0]!=Symbol.BLANK)
                return board.getBoardArray()[0][0].getWinner();
        }
        if(board.getBoardArray()[2][0] == board.getBoardArray()[1][1] && board.getBoardArray()[1][1] == board.getBoardArray()[0][2]) {
            if(board.getBoardArray()[0][0]!=Symbol.BLANK)
                return board.getBoardArray()[0][0].getWinner();
        }
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
