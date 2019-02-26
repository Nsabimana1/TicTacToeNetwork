package com.example.myapplication.gameImplementation;

public class MoveParser {

    //Move Format: <Symbol>@x,y

    public MoveParser() {

    }

    //TODO
    public String parseMoveToString(Move move) {
        return move.toString();
    }

    //TODO
    public Move parseStringToMove(String moveString) throws Exception {
        Move outMove = null;
        String symbolString = moveString.substring(0,moveString.indexOf('@'));
        String firstCoord = moveString.substring(moveString.indexOf('@'), moveString.indexOf(','));
        String secondCoord = moveString.substring(moveString.indexOf(',')+1);
        Coord coord = new Coord(Integer.parseInt(firstCoord), Integer.parseInt(secondCoord));
        for(Symbol symbol: Symbol.values())
            if (symbolString.contains(symbol.toString())) {
                outMove = new Move(symbol, coord);
                break;
            }

        return outMove;
    }

}