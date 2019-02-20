package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class joiningGameActivity extends AppCompatActivity {

    private EditText ipEntry;
    private Button gameConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_game);

        ipEntry = findViewById(R.id.ipField);
        gameConnect = findViewById(R.id.connectButton);

        gameConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        } );
    }
}
