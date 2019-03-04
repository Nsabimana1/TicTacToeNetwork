package com.example.myapplication.gameImplementation;

public enum WinState {
    X_WIN {
        @Override
        public String toString() {
            return "X wins";
        }
    }, O_WIN {
        @Override
        public String toString() {
            return "O wins";
        }
    }, NO_WIN {
        @Override
        public String toString() {
            return "No winner";
        }
    };
    abstract public String toString();
}