package com.example.nomosoloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResetPassword01 extends AppCompatActivity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password01);

        button1 = findViewById(R.id.rp1Btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                startActivity(new Intent(ResetPassword01.this, ResetPassword02.class));
            }
        });
    }

    public void getQuestions(){

    }
}