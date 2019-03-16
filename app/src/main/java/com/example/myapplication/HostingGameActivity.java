package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.gameImplementation.Coord;
import com.example.myapplication.gameImplementation.Move;
import com.example.myapplication.gameImplementation.Symbol;
import com.example.myapplication.gameImplementation.TicTacToeGame;
import com.example.myapplication.gameImplementation.WinState;
import com.example.myapplication.peertopeernetworking.Communication;
import com.example.myapplication.peertopeernetworking.Server;
import com.example.myapplication.peertopeernetworking.ServerListener;
import com.example.myapplication.peertopeernetworking.Utilities;

import java.io.IOException;
import java.net.Socket;

public class HostingGameActivity extends AppCompatActivity {
    public String connectedIpAddress;
    public String homeIpAddress;
    private TextView myIPView;
    private TextView opponentIPView;
    private String receivedMoveFromTheNetwork = "" ;
    private TicTacToeGame ticTacToeGame;
    private Symbol symbol = Symbol.X;
    private Symbol turn = Symbol.X;
    private String localPlayerSymbol;
    private boolean isMyTurn = false;
    private Button restartGame;
    private String winStatus = " ";

    //board buttons
    //00 10 20
    //01 11 21
    //02 12 22
    private Button boardButton00;
    private Button boardButton01;
    private Button boardButton02;
    private Button boardButton10;
    private Button boardButton11;
    private Button boardButton12;
    private Button boardButton20;
    private Button boardButton21;
    private Button boardButton22;
    public static String resetMessage = "reset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_game);
        connectedIpAddress = getIntent().getStringExtra(OpeningActivity.hostIpAddress);
        homeIpAddress = getIntent().getStringExtra(OpeningActivity.myLocalIpAddress);
        localPlayerSymbol = getIntent().getStringExtra(OpeningActivity.localMoveSymbol);
        setMySymbol();
        setComponents();
        setupServer();

        ticTacToeGame = new TicTacToeGame();
        updateBoard();
        String moveFromLocalPlayer = ticTacToeGame.getMoveString();
        ticTacToeGame.parseMoveString(moveFromLocalPlayer);
        ticTacToeGame.parseMoveString(receivedMoveFromTheNetwork);

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               restartGame();
               sendMove(connectedIpAddress, Server.APP_PORT, resetMessage);
            }
        });
    }


    public void setMySymbol(){
        if (localPlayerSymbol.equals("O")){
            symbol = Symbol.O;
        }else {
            symbol = Symbol.X;
            isMyTurn = true;
        }
    }
    public void restartGame(){
        redrawBoad();
        setComponents();
        setMySymbol();
    }

    public void redrawBoad(){
        ticTacToeGame.resetBoard();
        updateBoard();
    }

    public void setComponents() {
        myIPView = findViewById(R.id.MyIPAdress_View);
        opponentIPView = findViewById(R.id.oponentIP_View);
        myIPView.setText(homeIpAddress);
        opponentIPView.setText(connectedIpAddress);
        restartGame = findViewById(R.id.Restart_Button);
        setBoardButtons();
    }

    private void setBoardButtons() {
        boardButton00 = findViewById(R.id.button00);
        boardButton10 = findViewById(R.id.button10);
        boardButton20 = findViewById(R.id.button20);
        boardButton01 = findViewById(R.id.button01);
        boardButton11 = findViewById(R.id.button11);
        boardButton21 = findViewById(R.id.button21);
        boardButton02 = findViewById(R.id.button02);
        boardButton12 = findViewById(R.id.button12);
        boardButton22 = findViewById(R.id.button22);

        boardButton00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(0,0);
            }
        });
        boardButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(1,0);
            }
        });
        boardButton20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(2,0);
            }
        });
        boardButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(0,1);
            }
        });
        boardButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(1,1);
            }
        });
        boardButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(2,1);
            }
        });
        boardButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(0,2);
            }
        });
        boardButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(1,2);
            }
        });
        boardButton22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryMoveAt(2,2);
            }
        });
    }

    private void tryMoveAt(int x, int y) {
        if(isMyTurn) {
            makeMoveAt(x,y);
            toggleTurn();
        } else {
            Toast.makeText(getApplicationContext(), "It isn't your turn!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeMoveAt(int x, int y) {
        String winStatus = "[PH]";
        Move move = new Move(symbol, new Coord(x,y));
        boolean moveMade = ticTacToeGame.makeMove(move);
        if(moveMade){
            winStatus = "Move made.";
            String moveString = ticTacToeGame.getMoveString().substring(0,5);
            sendMove(connectedIpAddress, Server.APP_PORT, moveString);
        } else {
            if(ticTacToeGame.checkWin()!= WinState.NO_WIN) {
                winStatus = ticTacToeGame.checkWin().toString();
                redrawBoad();
                sendMove(connectedIpAddress, Server.APP_PORT, resetMessage);
            } else {
                winStatus = "Move not made.";
            }
        }
        Toast.makeText(getApplicationContext(), winStatus, Toast.LENGTH_SHORT).show();
        updateBoard();
    }

    public void displayReceivedMove(final String recMove){
        if (recMove.trim().equals(resetMessage)){
            winStatus  = ticTacToeGame.checkWin().toString();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    restartGame();
                }
              });
        }else {
            final String trimmedMove = recMove.substring(0, 5);
            toggleTurn();
            Log.e("StringParsing", "displayReceivedMove(" + trimmedMove + ")");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String status = ticTacToeGame.parseMoveString(trimmedMove);
                    updateBoard();
                }
            });
        }
    }

    public void sendMove(final String hostIpAddress, final int portNumber, final String move){
        Log.e("StringParsing", "Sending " + move);
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

    public void setupServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                Server.get().addListener(new ServerListener() {
                    @Override
                    public void notifyMessage(String msg) {
                        displayReceivedMove(msg);
                    }
                });
            } catch (IOException e) {
                Log.e(HostingGameActivity.class.getName(), "Could not start server");
                }
            }
        }).start();
    }

    private void updateBoard() {
        Symbol[][] boardArray = ticTacToeGame.getBoard().getBoardArray();
        boardButton00.setText(boardArray[0][0].toString());
        boardButton10.setText(boardArray[1][0].toString());
        boardButton20.setText(boardArray[2][0].toString());
        boardButton01.setText(boardArray[0][1].toString());
        boardButton11.setText(boardArray[1][1].toString());
        boardButton21.setText(boardArray[2][1].toString());
        boardButton02.setText(boardArray[0][2].toString());
        boardButton12.setText(boardArray[1][2].toString());
        boardButton22.setText(boardArray[2][2].toString());
    }

    private void toggleTurn() {
        isMyTurn = !isMyTurn;
    }
}
