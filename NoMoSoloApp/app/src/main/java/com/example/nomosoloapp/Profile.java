package com.example.nomosoloapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    private DBManager dbManager;
    private String userEmail;
    private TextView setAvatar;
    private ImageView avatar;
    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        dbManager = new DBManager(this);
        dbManager.open();

        setAvatar = findViewById(R.id.setAvatar);
        avatar = findViewById(R.id.avatar);

        //Prompting users to select photo in phone library
        setAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent,1);
            }
        });
    }

    //Pulling img as Uri and converting Uri to Bitmap abd set image using bitmap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!=null)
        {
            imagePath = data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
            avatar.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getElements(){
        ImageView imageIV = findViewById(R.id.avatar);
        TextView userBioTV = findViewById(R.id.userBio);
        Spinner userInstrumentSpinner = (Spinner) findViewById(R.id.userInstrumentSpinner);
        Spinner userSkillSpinner = (Spinner) findViewById(R.id.userSkillSpinner);
        Spinner userGenre1Spinner = (Spinner) findViewById(R.id.userGenre1Spinner);
        Spinner userGenre2Spinner = (Spinner) findViewById(R.id.userGenre2Spinner);
        Spinner seekingInstrumentSpinner = (Spinner) findViewById(R.id.seekingInstrumentSpinner);
        Spinner seekingSkillSpinner = (Spinner) findViewById(R.id.seekingSkillSpinner);
        Spinner seekingGenreSpinner = (Spinner) findViewById(R.id.seekingGenreSpinner);




        Bitmap bitmap = ((BitmapDrawable)imageIV.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] userImg = bos.toByteArray();

        String userBio = userBioTV.getText().toString();
        String userInstrument = userInstrumentSpinner.getSelectedItem().toString();
        String userSkill = userSkillSpinner.getSelectedItem().toString();
        String userGenre1 = userGenre1Spinner.getSelectedItem().toString();
        String userGenre2 = userGenre2Spinner.getSelectedItem().toString();
        String seekingInstrument = seekingInstrumentSpinner.getSelectedItem().toString();
        String seekingSkill = seekingSkillSpinner.getSelectedItem().toString();
        String seekingGenre = seekingGenreSpinner.getSelectedItem().toString();

        String userId = dbManager.getUserId(userEmail);
        dbManager.setNewProfile(Integer.parseInt(userId), userImg, userBio, userInstrument, userSkill, userGenre1, userGenre2, seekingInstrument, seekingSkill, seekingGenre);
    }

    public void saveProfile(View v){
        getElements();
        finish();
    }
}