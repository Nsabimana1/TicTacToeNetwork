package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.peertopeernetworking.Communication;
import com.example.myapplication.peertopeernetworking.ConnectionInitiator;
import com.example.myapplication.peertopeernetworking.Server;
import com.example.myapplication.peertopeernetworking.ServerListener;
import com.example.myapplication.peertopeernetworking.Utilities;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class OpeningActivity extends AppCompatActivity {

    private Button connectButton;
    private Button enterGameButton;
    private TextView localIpView;
    private TextView connectedIpView;
    private ConnectionInitiator connectionInitiator;
    private String localIpAddress;
    private String connectedIpAddress = "";
    private EditText OtherPlayerIpEntry;
    private String otherPlayerIp;
    private Button acceptConnButton;
    private Server server;

    public static String connectionMessage = "letUSConnect";
    public static final String hostIpAddress = "connectedIpAddress";
    public static final String myLocalIpAddress = "MyIpAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        setupComponents();

        setupClient();

        setupServer();

        acceptConnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!connectedIpAddress.equals("")) {
                        send("I want to connect", connectedIpAddress, Server.APP_PORT);
                    }else {
                            displayToast("You have not received a connection request");
                        }
                }
            });



        enterGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionInitiator.isConnected()) {
                    Intent proceeding_To_Game_Screen = new Intent(OpeningActivity.this, HostingGameActivity.class);
                    proceeding_To_Game_Screen.putExtra(hostIpAddress, connectedIpAddress);
                    proceeding_To_Game_Screen.putExtra(myLocalIpAddress, localIpAddress);
                    startActivity(proceeding_To_Game_Screen);
                } else {
                    displayToast("Still waiting for connection");
                }
           }
        });
    }



    private void setupComponents() {
        localIpView = findViewById(R.id.local_UserIP_View);
        connectedIpView = findViewById(R.id.connectedIP);
        enterGameButton = findViewById(R.id.gameButton);
        OtherPlayerIpEntry = findViewById(R.id.otherIP_Entry);
        acceptConnButton = findViewById(R.id.Accept_Connection);
        connectButton = findViewById(R.id.connectButton);
        try {
            localIpAddress = Utilities.getLocalIpAddress();
            localIpView.setText(localIpAddress);
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }

        connectionInitiator = new ConnectionInitiator(localIpAddress);
        enterGameButton.setEnabled(false);
        acceptConnButton.setEnabled(false);
    }

    private void setupClient() {
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("I want to connect", OtherPlayerIpEntry.getText().toString(), Server.APP_PORT);
            }
        });
    }



    private void setupServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new Server();
                    server.addListener(new ServerListener() {
                        @Override
                        public void notifyMessage(String msg) {
                            showIncoming(msg);
                        }
                    });
                    server.listen();
                } catch (IOException e) {
                    Log.e(OpeningActivity.class.getName(), "Could not start server");
                }
            }
        }).start();
    }

    private void showIncoming(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!msg.equals(" ")){
                    String incomingIP = server.getIncomingIpAddress();
                    displayConnectedIp(incomingIP.substring(1, incomingIP.length()));

//                    if (incomingIP != null) {
//                        displayConnectedIp(incomingIP.substring(1, incomingIP.length()));
//                    }
//                    try {
//                        String incomingIP = server.getIncomingIpAddress();
//                        if (incomingIP != null) {
//                            displayConnectedIp(incomingIP.substring(1, incomingIP.length()));
//                        }
//                    } catch (IOException e) {
//                        Log.e(OpeningActivity.class.getName(), "OOps, exception! " + e.getMessage());
//                    }
                }
//                    connectedIpView.setText(msg);
            }
        });
    }

    public void send(final String message, final String host, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket target = new Socket(host, port);
                    Communication.sendOver(target, message);
                    showIncoming(Communication.receive(target));
                    target.close();
                } catch (final Exception e) {
                    OpeningActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(OpeningActivity.this, e);
                        }
                    });
                }

            }
        }.start();
    }


    public void displayConnectedIp(String connectedIpAddress){
        this.connectedIpAddress = connectedIpAddress;
        connectionInitiator.setConnectedIP(connectedIpAddress);
        connectionInitiator.initiateConnection();
        connectedIpView.setText(connectedIpAddress);
        enterGameButton.setEnabled(true);
        acceptConnButton.setEnabled(true);
    }


    public void displayToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    // if (connection is attempted from another device) {
    // displayToast("connection has been attempted from " + otherIPAddress);
    // }
}

