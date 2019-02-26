package com.example.myapplication.gameImplementation;

public class Move {
    private Coord coord;
    private Symbol symbol;

    //TODO
    public Move(Coord coord, Symbol symbol) {
        this.symbol = symbol;
        this.coord = coord;
    }


    //TODO
    @Override
    public String toString() {
        return coord.toString()+"@"+coord.toString();
    }

    //Getter Methods
    public Coord getCoord() {
        return coord;
    }
    public Symbol getSymbol() {
        return symbol;
    }
}
