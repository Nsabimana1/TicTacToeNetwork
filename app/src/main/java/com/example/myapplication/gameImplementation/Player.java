package com.example.myapplication.gameImplementation;

public class Player {
    int ipAddress;
    int port;
    Symbol symbol;
    public Player(int ipAddress, int port, Symbol symbol){
        this.ipAddress = ipAddress;
        this.port = port;
        this.symbol = symbol;
    }

    public int getIpAddress() {return ipAddress;}
    public int getPort() {return port;}
    public Symbol getSymbol() {return symbol;}
}
