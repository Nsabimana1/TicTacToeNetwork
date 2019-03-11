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

    @Test
    public void checkWin() {
        checkHorizWin();
        checkVertWin();
        checkDiagWin();
    }

    @Test
    public void checkHorizWin() {
        for(int y=0; y<3;y++) {
            ticTacToeGame.resetBoard();
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(0,y)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(1,y)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(2,y)));
            System.out.println(ticTacToeGame.getBoard().toString());
            assertEquals(WinState.X_WIN, ticTacToeGame.checkWin());

        }
        for(int y=0; y<3;y++) {
            ticTacToeGame.resetBoard();
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(0,y)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(1,y)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(2,y)));
            System.out.println(ticTacToeGame.getBoard().toString());
            assertEquals(WinState.O_WIN, ticTacToeGame.checkWin());

        }
    }

    @Test
    public void checkVertWin() {
        for(int x=0; x<3; x++) {
            ticTacToeGame.resetBoard();
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(x,0)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(x,1)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(x,2)));
            System.out.println(ticTacToeGame.getBoard().toString());
            assertEquals(WinState.X_WIN, ticTacToeGame.checkWin());
        }
        for(int x=0; x<3; x++) {
            ticTacToeGame.resetBoard();
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(x,0)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(x,1)));
            ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(x,2)));
            System.out.println(ticTacToeGame.getBoard().toString());
            assertEquals(WinState.O_WIN, ticTacToeGame.checkWin());
        }
    }

    @Test
    public void checkDiagWin() {
        ticTacToeGame.resetBoard();
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(0,0)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(1,1)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(2,2)));
        System.out.println(ticTacToeGame.getBoard().toString());
        assertEquals(WinState.X_WIN, ticTacToeGame.checkWin());
        ticTacToeGame.resetBoard();
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(0,2)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(1,1)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.X, new Coord(2,0)));
        System.out.println(ticTacToeGame.getBoard().toString());
        assertEquals(WinState.X_WIN, ticTacToeGame.checkWin());
        ticTacToeGame.resetBoard();
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(0,0)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(1,1)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(2,2)));
        System.out.println(ticTacToeGame.getBoard().toString());
        assertEquals(WinState.O_WIN, ticTacToeGame.checkWin());
        ticTacToeGame.resetBoard();
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(0,2)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(1,1)));
        ticTacToeGame.getBoard().makeMove(new Move(Symbol.O, new Coord(2,0)));
        System.out.println(ticTacToeGame.getBoard().toString());
        assertEquals(WinState.O_WIN, ticTacToeGame.checkWin());

    }

    @Test
    public void moveTest1() {
        Move testMove;
        for(int x=0; x<3;x++) {
            for(int y=0; y<3;y++) {
                testMove = new Move(Symbol.X, new Coord(x,y));
                System.out.println("Making move " + testMove.toString());
                ticTacToeGame.getBoard().makeMove(testMove);
                Symbol checkSymbol = ticTacToeGame.getBoard().getBoardArray()[x][y];
                System.out.println("checkSymbol = " + checkSymbol.toString());
                assertEquals(Symbol.X, checkSymbol);
            }
        }
        System.out.println("Test1");
    }

    @Test
    public void overrideMoveTest() {
        moveTest1();
        Move testMove;
        for(int x=0; x<3;x++) {
            for(int y=0; y<3;y++) {
                testMove = new Move(Symbol.O, new Coord(x,y));
                System.out.println("Making move " + testMove.toString());
                ticTacToeGame.getBoard().makeMove(testMove);
                Symbol checkSymbol = ticTacToeGame.getBoard().getBoardArray()[x][y];
                System.out.println("checkSymbol = " + checkSymbol.toString());
                assertEquals(Symbol.X, checkSymbol);
            }
        }
        System.out.println("Test1");
    }
}