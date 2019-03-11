package com.example.myapplication.gameImplementation;

public enum Symbol {
    X {
        @Override
        public String toString() {
            return "X";
        }
        @Override
        public WinState getWinner() {return WinState.X_WIN;}
    }, O {
        @Override
        public String toString() {
            return "O";
        }
        @Override
        public WinState getWinner() {return WinState.O_WIN;}
    }, BLANK {
        @Override
        public String toString() {
            return "-";
        }
        @Override
        public WinState getWinner() {return WinState.NO_WIN;}
    };
    abstract public String toString();
    abstract public WinState getWinner();
}
