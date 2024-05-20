package com.example.adopcan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantallainicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);


        int tiempo = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(Pantallainicio.this, Login.class));
                finish();

            }
        },tiempo);
    }
}