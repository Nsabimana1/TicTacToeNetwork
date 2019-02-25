package com.example.myapplication.gameImplementation;

public enum Symbol {
    X {
        @Override
        public String toString() {
            return "X";
        }
    }, O {
        @Override
        public String toString() {
            return "O";
        }
    }, BLANK {
        @Override
        public String toString() {
            return " ";
        }
    };
    abstract public String toString();
}
