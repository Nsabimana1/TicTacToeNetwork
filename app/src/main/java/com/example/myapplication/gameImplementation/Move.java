package com.example.myapplication.gameImplementation;

public class Move {

    private Symbol symbol;
    private Coord coord;

    //TODO
    public Move(Symbol symbol, Coord coord) {
        this.symbol = symbol;
        this.coord = coord;
    }


    //TODO
    @Override
    public String toString() {
        return symbol.toString()+"@"+coord.toString();
    }

    //Getter Methods
    public Symbol getSymbol() {
        return symbol;
    }
    public Coord getCoord() {
        return coord;
    }
}
