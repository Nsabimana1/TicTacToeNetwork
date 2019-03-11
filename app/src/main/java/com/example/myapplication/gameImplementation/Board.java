package com.example.myapplication.gameImplementation;

public class Board {
    //m=columns or X length
    //n=rows or Y length
    private Symbol[][] boardArray;
    private int m,n;

    Board(int m, int n) {
        this.m=m;
        this.n=n;
        boardArray = new Symbol[m][n];
        resetBoard();
    }

    boolean makeMove(Move move) {
        if(this.getSymbolAt(move.getCoord()) == Symbol.BLANK) {
            this.setSymbolAt(move.getSymbol(), move.getCoord());
            return true;
        }
        return false;
    }


    public void resetBoard() {
        for(int x = 0; x<m; x++) {
            for(int y = 0; y<n; y++) {
                boardArray[x][y] = Symbol.BLANK;
            }
        }
    }

    private Symbol getSymbolAt(Coord coord) {
        if(coord.getX()<m && coord.getY()<n) {
            return boardArray[coord.getX()][coord.getY()];
        } else {
            return null;
        }
    }
    private void setSymbolAt(Symbol symbol, Coord coord) {
        if(coord.getX()<m && coord.getY()<n) {
            boardArray[coord.getX()][coord.getY()] = symbol;
        }
    }

    //Getter Methods
    public Symbol[][] getBoardArray() {
        return boardArray;
    }
    public int getM() {
        return m;
    }
    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(int x = 0; x<m; x++) {
            for(int y = 0; y<n; y++)
                out.append(boardArray[x][y].toString());
            out.append("\n");
        }
        return out.toString();
    }
}