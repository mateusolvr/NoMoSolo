package com.example.nomosoloapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

        resetPassword = findViewById(R.id.resetPwClickable);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResetPassword01.class));
            }
        });
    }

    public void GoToRegister(View v)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }

    public void Login(View v)
    {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }
}