package com.example.nomosoloapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassword01 extends AppCompatActivity {

    private Button button1;
    private DBManager dbManager;

    private String question, answer, email;
    private boolean hasQuestionFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password01);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#363D46\">" + getString(R.string.app_name) + "</font>"));

        dbManager = new DBManager(this);
        dbManager.open();

        button1 = findViewById(R.id.rp1Btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasQuestionFlag == false){
                    getQuestion();
                    if(question == null || answer == null){
                        return;
                    }
                    setQuestion();
                } else {
                    getAnswer();
                }
            }
        });
    }

    private void getQuestion(){
        TextView emailTV = findViewById(R.id.rp1EmailText);
        email = emailTV.getText().toString();
        Cursor cursor = dbManager.getQuestionAndAnswer(email);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            question =  cursor.getString(0);
            answer =  cursor.getString(1);
        } else {
            Toast.makeText(getApplicationContext(),"Couldn't find email!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setQuestion(){
        LinearLayout questionLayout = findViewById(R.id.questionLinearLayout);

        TextView questionTV = findViewById(R.id.rp1SecQuestionTxt);
        questionTV.setText(question);
        questionLayout.setVisibility(View.VISIBLE);

        hasQuestionFlag = true;
        button1.setText("Next");
    }

    private void getAnswer(){
        TextView giverAnswerTV = findViewById(R.id.rp1InputSecurityAnswer);
        String giverAnswer = giverAnswerTV.getText().toString();

        if(giverAnswer.equalsIgnoreCase(answer)){
            Intent intent = new Intent(this, ResetPassword02.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Answer doesn't match!",Toast.LENGTH_SHORT).show();
        }

    }
}