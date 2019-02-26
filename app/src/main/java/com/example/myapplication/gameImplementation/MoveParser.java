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
        String symbolString = moveString.substring(0,moveString.indexOf('@'));
        if(0<moveString.indexOf('@')&&moveString.indexOf('@')<moveString.indexOf(',')) {

        }

        return null;
    }

}
