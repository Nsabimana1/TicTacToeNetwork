package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningActivity extends AppCompatActivity {

    private Button openJoinActivity;
    private Button openHostActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        openJoinActivity = findViewById(R.id.joinButton);
        openHostActivity = findViewById(R.id.hostButton);

        openJoinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewRecipeIntent = new Intent(OpeningActivity.this, joiningGameActivity.class);
                startActivity(NewRecipeIntent);
            }
        } );

        openHostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShoppingListIntent = new Intent(OpeningActivity.this, hostingGameActivity.class);
                startActivity(ShoppingListIntent);
            }
        } );
    }
}
