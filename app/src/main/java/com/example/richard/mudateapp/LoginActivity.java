package com.example.richard.mudateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
    }

    public void onClickHandler(View view){
        String whereToGo = view.getTag().toString();
        Intent intent = new Intent("com.example.richard.parcial." + whereToGo);
        startActivity(intent);
    }
}
