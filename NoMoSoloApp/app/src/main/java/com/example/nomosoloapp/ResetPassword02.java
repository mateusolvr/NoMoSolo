package com.example.nomosoloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassword02 extends AppCompatActivity {

    private Button button2;
    private DBManager dbManager;
    private String email;
    private boolean passwordUpdatedFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password02);

        dbManager = new DBManager(this);
        dbManager.open();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));


        button2 = findViewById(R.id.rp2Btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                setPassword();
                if (passwordUpdatedFlag) {
                    finish();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPassword() {
        TextView passwordTV = findViewById(R.id.rp2inputPassword);
        TextView repeatPasswordTV = findViewById(R.id.rp2inputConfirmPassword);

        String password = passwordTV.getText().toString();
        String repeatPassword = repeatPasswordTV.getText().toString();

        if (!password.equals(repeatPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbManager.getSalt(email);
        cursor.moveToFirst();
        String salt = cursor.getString(0);

        passwordHelper myPasswordHelper = new passwordHelper(dbManager);
        String encryptedPassword = myPasswordHelper.getEncryptedPassword(password, salt);
        dbManager.updatePassword(email, encryptedPassword);
        passwordUpdatedFlag = true;
        Toast.makeText(getApplicationContext(), "Password updated!", Toast.LENGTH_SHORT).show();
    }
}