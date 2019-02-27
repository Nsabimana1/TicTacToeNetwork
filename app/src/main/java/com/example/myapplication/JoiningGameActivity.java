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

import com.example.myapplication.peertopeernetworking.*;

import java.net.SocketException;


public class JoiningGameActivity extends AppCompatActivity {

    private EditText ipEntry;
    private Button gameConnect;
    private TextView myIpView;
    public  static final String  hostIpAddress  = "HostIpAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_game);

        ipEntry = findViewById(R.id.ipField);
        gameConnect = findViewById(R.id.connectButton);
        myIpView = findViewById(R.id.myIpView);

        try {
            myIpView.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }

        gameConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ipEntry.getText().toString().equals("")){
                    Intent proceeding_To_Game_Screen = new Intent(JoiningGameActivity.this, HostingGameActivity.class);
                    proceeding_To_Game_Screen.putExtra(hostIpAddress, ipEntry.getText().toString());
                    startActivity(proceeding_To_Game_Screen);
                    finish();
                }else{
                    displayToast("Enter The Host Ip Address Please");
                }
            }
        } );
    }

    public void displayToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
