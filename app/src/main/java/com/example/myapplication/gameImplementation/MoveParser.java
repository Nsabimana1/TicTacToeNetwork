package com.example.myapplication.gameImplementation;


public class MoveParser {

    //Move Format: <Symbol>@x,y

    public MoveParser() {

    }

    public String parseMoveToString(Move move) {
        return move.toString();
    }

    public Move parseStringToMove(String moveString) {
        Move outMove = null;
        if(moveString.charAt(1)=='@') {
            Symbol moveSymbol;
            for(Symbol symbol: Symbol.values()) {
                if(moveString.charAt(0)==symbol.toString().charAt(0)) {
                    moveSymbol=symbol;
                }
            }
            if(moveString.charAt(3)==',') {
                Coord moveCoord;
                try {
                    
                } catch (Exception e) {

                }
            }
        }
        return outMove;
    }
}