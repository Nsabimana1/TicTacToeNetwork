package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OpeningActivity extends AppCompatActivity {

    private Button connect;
    private Button enterGame;
    private TextView userIPAddress;
    private TextView otherIPAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        connect = findViewById(R.id.connectButton);
        enterGame = findViewById(R.id.gameButton);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        enterGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    // if (connection is attempted from another device) {
    // displayToast("connection has been attempted from " + otherIPAddress);
    // }
}
