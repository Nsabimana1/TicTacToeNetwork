package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    private String connectedIpAddress = " ";
    private EditText OtherPlayerIpEntry;
    private String otherPlayerIp;
//    private Button acceptConnButton;
    private String myMoveSymbol = "X";

    public static String confirmingMessage = "letUSConnect";
    public static String rejectingMessage = "Don't_want_To_connect";
    public static final String hostIpAddress = "connectedIpAddress";
    public static final String myLocalIpAddress = "MyIpAddress";
    public static final String localMoveSymbol = "initialMoveSymbol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        setupComponents();
        setupClient();
        setupServer();

//        acceptConnButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                if(!connectedIpAddress.equals("")) {
//                    send("I want to connect", connectedIpAddress, Server.APP_PORT);
//                }else {
//                        displayToast("You have not received a connection request");
//                    }
//                }
//            });


        enterGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionInitiator.isConnected()) {
                    Intent proceeding_To_Game_Screen = new Intent(OpeningActivity.this, HostingGameActivity.class);
                    proceeding_To_Game_Screen.putExtra(hostIpAddress, connectedIpAddress);
                    proceeding_To_Game_Screen.putExtra(myLocalIpAddress, localIpAddress);
                    proceeding_To_Game_Screen.putExtra(localMoveSymbol, myMoveSymbol);
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
//        acceptConnButton = findViewById(R.id.Accept_Connection);
        connectButton = findViewById(R.id.connectButton);
        try {
            localIpAddress = Utilities.getLocalIpAddress();
            localIpView.setText(localIpAddress);
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }
        connectionInitiator = new ConnectionInitiator(localIpAddress);
        enterGameButton.setEnabled(false);
    }

    private void setupClient() {
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherPlayerIpEntry.getText().toString().length() == 14) {
                    send("let us connect", OtherPlayerIpEntry.getText().toString(), Server.APP_PORT);
                }else {
                    displayToast("Please Enter a Valid Ip Address");
                }
            }
        });
    }



    private void setupServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyMessage(String msg) {
                            showIncoming(msg);
                        }
                    });
                    Server.get().listen();
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
                    Log.e(OpeningActivity.class.getName(), "sent message is: " + msg);
                    Log.e(OpeningActivity.class.getName(), "the message to compare is: " + confirmingMessage);
                        try {
                            String incomingIP = Server.get().getIncomingIpAddress();
                            if (incomingIP != null) {
                                displayConnectedIp(incomingIP.trim(), msg.trim());
                            }
                        }catch (IOException e) {
                            Log.e(OpeningActivity.class.getName(), "OOps, exception! " + e.getMessage());
                        }
                }
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

    public void displayConnectedIp(String incomingIpAddress, String message){
        Log.e(OpeningActivity.class.getName(), "Incoming Ip address is: " + incomingIpAddress);
        Log.e(OpeningActivity.class.getName(), "Local Ip address is: " + localIpAddress);
        if(this.connectedIpAddress.equals(" ") && !incomingIpAddress.equals(localIpAddress) ){
            if(message.equals(confirmingMessage)){
                this.connectedIpAddress = incomingIpAddress;
                connectionInitiator.setConnectedIP(incomingIpAddress);
                connectionInitiator.initiateConnection();
                connectedIpView.setText(incomingIpAddress);
                enterGameButton.setEnabled(true);
                messageStatusDialog("Your Request Has Been Accepted :)");
            } else if (message.equals(rejectingMessage)){
                this.connectedIpAddress = " ";
                messageStatusDialog("Your Request Has Been Rejected :)");
            }else {
                connectionPromptDialogBox(incomingIpAddress);
            }
        }
    }

    public void displayToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void connectionPromptDialogBox(final String incomingIpAddress) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(OpeningActivity.this);
    builder.setCancelable(false);
    builder.setTitle("Connection Request");
    builder.setMessage(incomingIpAddress + " Would like to connect with you");
    builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            connectedIpAddress = incomingIpAddress;
            connectionInitiator.setConnectedIP(connectedIpAddress);
            connectionInitiator.initiateConnection();
            connectedIpView.setText(connectedIpAddress);
            enterGameButton.setEnabled(true);
            myMoveSymbol = "O";
            send(confirmingMessage, connectedIpAddress, Server.APP_PORT);
        }
    })
            .setNegativeButton("Reject ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connectedIpAddress = " ";
                    send(rejectingMessage, incomingIpAddress, Server.APP_PORT);
                }
            });
    // Create the AlertDialog object and return it
    builder.create().show();
    }


    public void messageStatusDialog(String notificationMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OpeningActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Connection Request Status");
        builder.setMessage(notificationMessage);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // nothing needs to be implemented here
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }
}

