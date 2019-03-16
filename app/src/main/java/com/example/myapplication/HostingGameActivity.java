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
    private String localMove;
    private TicTacToeGame ticTacToeGame;
    private Symbol symbol = Symbol.X;
    private Symbol turn = Symbol.X;
    private String localPlayerSymbol;
    private TextView xwincount;
    private TextView owincount;
    private TextView tiecount;
    private boolean isMyTurn = false;
    private Button restartGame;
    private Integer xWins = 0;
    private Integer oWins = 0;
    private Integer ties = 0;
    private String winStatus = "No winner";

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
    public static String restartGameMessage = "restart";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_game);
        connectedIpAddress = getIntent().getStringExtra(OpeningActivity.hostIpAddress);
        homeIpAddress = getIntent().getStringExtra(OpeningActivity.myLocalIpAddress);
        localPlayerSymbol = getIntent().getStringExtra(OpeningActivity.localMoveSymbol);
        if (localPlayerSymbol.equals("O")){
            symbol = Symbol.O;
        }else {
            symbol = Symbol.X;
            isMyTurn = true;
        }
        setComponents();
        setupServer();

        ticTacToeGame = new TicTacToeGame();
        updateBoard();
        String moveFromLocalPlayer = ticTacToeGame.getMoveString();

        ticTacToeGame.parseMoveString(moveFromLocalPlayer);
        localMove = moveFromLocalPlayer;
        ticTacToeGame.parseMoveString(receivedMoveFromTheNetwork);

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               restartGame();
//               sendMove(connectedIpAddress, Server.APP_PORT, restartGameMessage);
            }
        });
    }

    public void restartGame(){
        redrawBoad();
        xWins = 0;
        oWins = 0;
        ties = 0;
        setComponents();
    }

    public void setWinCounts(){
        xwincount.setText(xWins.toString());
        owincount.setText(oWins.toString());
        tiecount.setText(ties.toString());
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
        xwincount = findViewById(R.id.xWins);
        owincount = findViewById(R.id.oWins);
        tiecount = findViewById(R.id.ties);
        restartGame = findViewById(R.id.Restart_Button);
        xwincount.setText(xWins.toString());
        owincount.setText(oWins.toString());
        tiecount.setText(ties.toString());
        //setting board components
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

    public void setWinner(){
        if (winStatus.equals("X wins")){
            xWins += 1;
            xwincount.setText(xWins.toString());
        }else if (winStatus.equals("O wins")){
            oWins += 1;
            owincount.setText(oWins.toString());
        }else if(winStatus.equals("No winner")){
            ties += 1;
            tiecount.setText(ties.toString());
        }
    }

    private void displayWins(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            setWinner();
            setWinCounts();
            }
        });
    }

    private void makeMoveAt(int x, int y) {
        String winStatus = "[PH]";
        Move move = new Move(symbol, new Coord(x,y));
        boolean moveMade = ticTacToeGame.makeMove(move);

        if(moveMade) {
            winStatus = "Move made.";
            String moveString = ticTacToeGame.getMoveString().substring(0,5);
            sendMove(connectedIpAddress, Server.APP_PORT, moveString);
        } else {
            if(ticTacToeGame.checkWin()!= WinState.NO_WIN) {
                winStatus = ticTacToeGame.checkWin().toString();
//                ticTacToeGame.resetBoard();
//                updateBoard();
                displayWins();
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
                    Log.e("the winner is", "winner is (" + winStatus + ")");
                    displayWins();
                    redrawBoad();
                    toggleTurn();
                    setWinCounts();
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

//    private boolean isTurn(Symbol symbol) {
//        return turn == symbol;
//    }

    private void toggleTurn() {
        isMyTurn = !isMyTurn;
    }
}
