package com.example.nomosoloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    TextView resetPassword;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

        resetPassword = findViewById(R.id.resetPwClickable);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.close();
                startActivity(new Intent(MainActivity.this, ResetPassword01.class));
            }
        });

        dbManager = new DBManager(this);
        dbManager.open();

//        //        dbManager.deleteEverything();
//        Log.d("AUTHENTICATION ---->", String.valueOf(authenticateUser("mateus.olvr@gmail.com", "12345")));


    }

    public void GoToRegister(View v)
    {
//        dbManager.close();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }

    public void Login(View v)
    {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }
}