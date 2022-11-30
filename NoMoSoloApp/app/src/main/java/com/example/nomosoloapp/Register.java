package com.example.nomosoloapp;

import androidx.appcompat.app.ActionBar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private DBManager dbManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));


        dbManager = new DBManager(this);
        dbManager.open();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SetUpProfile (View v)
    {
        TextView fNameTV = findViewById(R.id.regFName);
        TextView lNameTV = findViewById(R.id.regLName);
        TextView emailTV = findViewById(R.id.regEmailInput);
        TextView phoneTV = findViewById(R.id.regPhone);
        TextView passwordTV = findViewById(R.id.regPasswordInput);
        TextView repeatPasswordTV = findViewById(R.id.regRepeatPassword);
        TextView securityQuestionTV = findViewById(R.id.regSecurityQuestion);
        TextView securityAnswerTV = findViewById(R.id.regSecurityAnswer);

        String fName = fNameTV.getText().toString();
        String lName = lNameTV.getText().toString();
        String email = emailTV.getText().toString();
        String phone = phoneTV.getText().toString();
        String password = passwordTV.getText().toString();
        String repeatPassword = repeatPasswordTV.getText().toString();
        String securityQuestion = securityQuestionTV.getText().toString();
        String securityAnswer = securityAnswerTV.getText().toString();

        if (!password.equals(repeatPassword)){
            Toast.makeText(getApplicationContext(),"Passwords don't match!",Toast.LENGTH_SHORT).show();
        } else if (!isEmailAvailable(email)){
            Toast.makeText(getApplicationContext(),"Email already taken. Use another one.",Toast.LENGTH_SHORT).show();
        } else {
            PasswordHelper myPasswordHelper = new PasswordHelper(dbManager);
            String salt = myPasswordHelper.getNewSalt();
            String encryptedPassword = myPasswordHelper.getEncryptedPassword(password, salt);

            dbManager.insertNewMusician(fName, lName, email, encryptedPassword, salt, phone, securityQuestion, securityAnswer);
//            dbManager.close();
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        }
    }

    private boolean isEmailAvailable(String email) {

        String dbEmail = dbManager.checkEmail(email);
        Log.d("->>>>>", "DB Email" + dbEmail + "Inserted Email: " + email);
        if (email.equalsIgnoreCase(dbEmail)) {
            return false;
        }
        return true;
    }


}