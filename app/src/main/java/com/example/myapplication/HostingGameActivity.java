package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.gameImplementation.TicTacToeGame;
import com.example.myapplication.peertopeernetworking.Communication;
import com.example.myapplication.peertopeernetworking.Server;
import com.example.myapplication.peertopeernetworking.ServerListener;
import com.example.myapplication.peertopeernetworking.Utilities;

import java.io.IOException;
import java.net.Socket;

public class HostingGameActivity extends AppCompatActivity {
    public String connectedIpAddress;
    public String homeIpAddress;
    private Button sendMove;
    private TextView receivedMove;
    private EditText moveEntry;
    private TextView myIPView;
    private TextView oponentIPView;
    private String receivedMoveFromTheNetwork = "" ;

    //board buttons
    private Button boardButton00;
    private Button boardButton01;
    private Button boardButton02;
    private Button boardButton10;
    private Button boardButton11;
    private Button boardButton12;
    private Button boardButton20;
    private Button boardButton21;
    private Button boardButton22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_game);
        connectedIpAddress = getIntent().getStringExtra(OpeningActivity.hostIpAddress);
        homeIpAddress = getIntent().getStringExtra(OpeningActivity.myLocalIpAddress);
        setComponents();
        setupServer();
        setUpClient();

        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        String moveFromLocalPlayer = ticTacToeGame.getMoveString();
        ticTacToeGame.parseMoveString(receivedMoveFromTheNetwork);
    }


    public void setReceivedMoveFromTheNetwork(String move){
        this.receivedMoveFromTheNetwork = move;
    }

    public void setComponents() {
        moveEntry = findViewById(R.id.moveEntry);
        receivedMove = findViewById(R.id.Recieve_Move);
        myIPView = findViewById(R.id.MyIPAdress_View);
        oponentIPView = findViewById(R.id.oponentIP_View);
        myIPView.setText(homeIpAddress);
        oponentIPView.setText(connectedIpAddress);

        //setting board components
        boardButton00 = findViewById(R.id.button00);
        boardButton01 = findViewById(R.id.button01);
        boardButton02 = findViewById(R.id.button02);
        boardButton10 = findViewById(R.id.button10);
        boardButton11 = findViewById(R.id.button11);
        boardButton12 = findViewById(R.id.button12);
        boardButton20 = findViewById(R.id.button20);
        boardButton21 = findViewById(R.id.button21);
        boardButton22 = findViewById(R.id.button22);
    }


    public void setUpClient(){
        sendMove = findViewById(R.id.send_Move);
        sendMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMove(connectedIpAddress, Server.APP_PORT, moveEntry.getText().toString());
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
