package com.example.myapplication.gameImplementation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeGameTest {

    public TicTacToeGame ticTacToeGame;

    @Before
    public void setUp() throws Exception {
        ticTacToeGame = new TicTacToeGame();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMoveString() {
        Move testMove;
        for(int x=0; x<3;x++) {
            for(int y=0; y<3;y++) {
                testMove = new Move(Symbol.X, new Coord(x,y));
                System.out.println("Making move at (" + x +"," + y + ")");
                ticTacToeGame.getBoard().makeMove(testMove);
                Symbol checkSymbol = ticTacToeGame.getBoard().getBoardArray()[x][y];
                System.out.println("checkSymbol = " + checkSymbol.toString());
                assertEquals(Symbol.X, checkSymbol);
            }
        }
        System.out.println("Test1");

    }

    @Test
    public void parseMoveString() {
    }

    @Test
    public void makeMove() {
    }

    @Test
    public void checkWin() {
    }

    @Test
    public void getRecentMove() {
    }

    @Test
    public void getBoard() {
    }

    @Test
    public void getMoveParser() {
    }
}