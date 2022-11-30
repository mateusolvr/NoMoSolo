package com.example.nomosoloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.content.Intent;

import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

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

    public void GoToRegister(View v) {
//        dbManager.close();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Login(View v) {
        TextView emailTV = findViewById(R.id.emailInput);
        TextView passwordTV = findViewById(R.id.passInput);
        String email = emailTV.getText().toString();
        String password = passwordTV.getText().toString();

        PasswordHelper myPasswordHelper = new PasswordHelper(dbManager);

        if (email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Please inform both email and password.", Toast.LENGTH_SHORT).show();
        } else if (myPasswordHelper.authenticateUser(email, password)) {
            String userID = dbManager.getUserId(email);
            Intent intent = new Intent(this, Fragments.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Wrong password. Try again or click Reset password.", Toast.LENGTH_SHORT).show();
        }
    }
}