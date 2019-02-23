package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HostingGameActivity extends AppCompatActivity {
    public String hostIpAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_game);
        hostIpAddress = getIntent().getStringExtra(JoiningGameActivity.hostIpAddress);

    }
}
