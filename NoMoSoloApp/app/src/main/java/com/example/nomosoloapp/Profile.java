package com.example.nomosoloapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private DBManager dbManager;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        dbManager = new DBManager(this);
        dbManager.open();
    }

    private void getElements(){
        TextView userBioTV = findViewById(R.id.userBio);
        Spinner userInstrumentSpinner = (Spinner) findViewById(R.id.userInstrumentSpinner);
        Spinner userSkillSpinner = (Spinner) findViewById(R.id.userSkillSpinner);
        Spinner userGenre1Spinner = (Spinner) findViewById(R.id.userGenre1Spinner);
        Spinner userGenre2Spinner = (Spinner) findViewById(R.id.userGenre2Spinner);
        Spinner seekingInstrumentSpinner = (Spinner) findViewById(R.id.seekingInstrumentSpinner);
        Spinner seekingSkillSpinner = (Spinner) findViewById(R.id.seekingSkillSpinner);
        Spinner seekingGenreSpinner = (Spinner) findViewById(R.id.seekingGenreSpinner);

        String userBio = userBioTV.getText().toString();
        String userInstrument = userInstrumentSpinner.getSelectedItem().toString();
        String userSkill = userSkillSpinner.getSelectedItem().toString();
        String userGenre1 = userGenre1Spinner.getSelectedItem().toString();
        String userGenre2 = userGenre2Spinner.getSelectedItem().toString();
        String seekingInstrument = seekingInstrumentSpinner.getSelectedItem().toString();
        String seekingSkill = seekingSkillSpinner.getSelectedItem().toString();
        String seekingGenre = seekingGenreSpinner.getSelectedItem().toString();

        String userId = dbManager.getUserId(userEmail);
        dbManager.setNewProfile(Integer.parseInt(userId), userBio, userInstrument, userSkill, userGenre1, userGenre2, seekingInstrument, seekingSkill, seekingGenre);
    }

    public void saveProfile(View v){
        getElements();
        finish();
    }
}