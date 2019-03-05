package com.example.myapplication.gameImplementation;


import android.util.Log;

public class MoveParser {

    //Move Format: <Symbol>@x,y

    public MoveParser() {

    }

    public String parseMoveToString(Move move) {
        return move.toString();
    }

    public Move parseStringToMove(String moveString) {
        Log.e("StringParsing", "running parseStringToMove(" + moveString + ")");
        Move outMove = new Move(Symbol.BLANK, new Coord(0,0));
        if(moveString.length() == 5 && moveString.charAt(1)=='@') {
            Symbol moveSymbol=Symbol.BLANK;
            for(Symbol symbol: Symbol.values()) {
                if(moveString.charAt(0)==symbol.toString().charAt(0)) {
                    moveSymbol=symbol;
                }
            }
            if(moveString.charAt(3)==',') {
                Coord moveCoord;
                try {
                    int xCoord = Integer.parseInt(moveString.substring(2,3));
                    int yCoord = Integer.parseInt(moveString.substring(4));
                    moveCoord = new Coord(xCoord,yCoord);
                    outMove = new Move(moveSymbol, moveCoord);
                } catch (Exception e) {
                }
            }
        }
        return outMove;
    }
}