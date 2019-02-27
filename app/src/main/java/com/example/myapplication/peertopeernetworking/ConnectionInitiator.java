package com.example.myapplication.peertopeernetworking;

public class ConnectionInitiator {
    private boolean connectionIsEnabled;
    private String localIp;
    private String connectedIP;

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
}
