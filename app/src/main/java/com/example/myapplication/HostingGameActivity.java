package com.example.myapplication;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.peertopeernetworking.Communication;
import com.example.myapplication.peertopeernetworking.Server;
import com.example.myapplication.peertopeernetworking.ServerListener;
import com.example.myapplication.peertopeernetworking.Utilities;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HostingGameActivity extends AppCompatActivity {
    public String hostIpAddress;
    private Button sendMove;
    private TextView receivedMove;
    private EditText moveEntry;
    private TextView myIPView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_game);
        hostIpAddress = getIntent().getStringExtra(JoiningGameActivity.hostIpAddress);
        setComponents();
    }


    public void setComponents() {
        moveEntry = findViewById(R.id.moveEntry);
        receivedMove = findViewById(R.id.Recieve_Move);
        myIPView = findViewById(R.id.myIpView);

        try {
            myIPView.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("HostingGameActivity", "Threw exception when finding ip address");
        }
    }


    public void setUpClient(){
        sendMove = findViewById(R.id.send_Move);
        sendMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMove(hostIpAddress, Server.APP_PORT, moveEntry.getText().toString());
            }
        });
    }


    public void setupServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new Server();
                    server.addListener(new ServerListener() {
                        @Override
                        public void notifyMessage(String msg) {
                            displayReceivedMove(msg);
                        }
                    });
                    server.listen();
                } catch (IOException e) {
                    Log.e(HostingGameActivity.class.getName(), "Could not start server");
                }

            }
        }).start();
    }


    public void displayReceivedMove(final String recMove){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                receivedMove.setText(recMove);
            }
        });
    }

    public void sendMove(final String hostIpAddress, final int portNumber, final String move){
        new Thread(){
            @Override
            public void run() {
                try {
                    Socket target = new Socket(hostIpAddress, portNumber);
                    Communication.sendOver(target, move);
                    displayReceivedMove(Communication.receive(target));
                    target.close();
                } catch (final Exception e) {
                    HostingGameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(HostingGameActivity.this, e);
                        }
                    });
                }
            }
        }.start();
    }

}
