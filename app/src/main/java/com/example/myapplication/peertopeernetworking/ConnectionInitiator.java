package com.example.myapplication.peertopeernetworking;

import com.example.myapplication.gameImplementation.Symbol;

public class ConnectionInitiator {
    private boolean connectionIsEnabled;
    private String localIp;
    private String connectedIP;
    private Symbol myMoveSymbol;

    public ConnectionInitiator(String localIp){
        this.localIp = localIp;
        connectionIsEnabled = false;
    }

    public void setConnectedIP(String connectedIP) {
        this.connectedIP = connectedIP;
    }

    public boolean isConnected(){
        return connectionIsEnabled;
    }

    public void initiateConnection(){
        this.connectionIsEnabled = true;
    }

    public void breakConnection(){
        this.connectionIsEnabled = false;
    }

    public void setMyMoveSymbol(Symbol moveSymbol){
        this.myMoveSymbol = moveSymbol;
    }
}
