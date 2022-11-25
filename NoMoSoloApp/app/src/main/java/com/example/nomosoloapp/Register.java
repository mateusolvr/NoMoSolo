package com.example.nomosoloapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));
    }

    public void SetUpProfile (View v)
    {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}