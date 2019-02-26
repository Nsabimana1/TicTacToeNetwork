package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningActivity extends AppCompatActivity {

    private Button connect;
    private Button enterGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        connect = findViewById(R.id.connectButton);
        enterGame = findViewById(R.id.gameButton);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewRecipeIntent = new Intent(OpeningActivity.this, JoiningGameActivity.class);
                startActivity(NewRecipeIntent);
            }
        } );

        enterGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShoppingListIntent = new Intent(OpeningActivity.this, HostingGameActivity.class);
                startActivity(ShoppingListIntent);
            }
        } );
    }
}
