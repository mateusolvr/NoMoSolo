package com.example.nomosoloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void SetUpProfile (View v)
    {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}