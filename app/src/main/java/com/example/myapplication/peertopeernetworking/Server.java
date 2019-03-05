package com.example.myapplication.peertopeernetworking;

/**
 * Created by gabriel on 2/15/19.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static final int APP_PORT = 8888;

//    private static Server instance = null;
//
//    public static Server get() throws IOException {
//        Log.i(Server.class.getName(), "Entering Server.get()");
//        if (instance == null) {
//            instance = new Server();
//            Log.i(Server.class.getName(), "Creating Server instance");
//        }
//        return instance;
//    }

    private ServerSocket accepter;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    private String incomingIpAddress;

    public Server() throws IOException {
        accepter = new ServerSocket(APP_PORT);
    }

    public void addListener(ServerListener listener) {
        this.listeners.add(listener);
    }

    public void listen() throws IOException {
        for (;;) {
            listenOnce().start();
        }
    }

    public SocketEchoThread listenOnce() throws IOException {
        Socket s = accepter.accept();
        incomingIpAddress = s.getInetAddress().toString();
        Log.i(Server.class.getName(), "Incoming iP: " + s.getInetAddress());
        SocketEchoThread echoer = new SocketEchoThread(s, listeners);
        return echoer;
    }

    public String getIncomingIpAddress() {
        return incomingIpAddress;
    }
}
