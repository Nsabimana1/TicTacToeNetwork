package com.example.myapplication.gameImplementation;

import java.util.ArrayList;

public class Board {
    Symbol[][] boardArray;
    int m,n;
    //m=columns or X length
    //n=rows or Y length
    public Board(int m, int n) {
        this.m=m;
        this.n=n;
        boardArray = new Symbol[m][n];
        resetBoard();
    }

    private boolean makeMove(Move move) {
        
        return false;
    }

    private void resetBoard() {
        for(Symbol[] rows: boardArray) {
            for(Symbol symbol: rows) {
                symbol = Symbol.BLANK;
            }
        }
    }
}
