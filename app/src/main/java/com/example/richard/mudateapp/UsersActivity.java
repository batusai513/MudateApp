package com.example.richard.mudateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
    }

    public void onClickHandler(View view){
        String whereToGo = view.getTag().toString();
        Intent intent = new Intent("com.example.richard.parcial." + whereToGo);
        startActivity(intent);
    }
}
