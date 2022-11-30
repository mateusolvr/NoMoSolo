package com.example.nomosoloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Calendar01 extends AppCompatActivity {

    private Button pickDateBtn, postNoteBtn;
    private TextView selectedDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar01);

        pickDateBtn = findViewById(R.id.idBtnPickDate);
        postNoteBtn = findViewById(R.id.btnAddNote);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Calendar01.this, com.google.android.material.R.style.Theme_Material3_Light_Dialog ,
                        (view1, year1, month1, day1) -> selectedDateTV.setText(day1 + "-" + (month1 + 1) + "-" + year1),
                        year, month, day);

                datePickerDialog.show();
            }
        });

        postNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText note = (EditText) findViewById(R.id.emailInput);

                String strNote, strDate;
                strNote = note.getText().toString();
                strDate = selectedDateTV.getText().toString();

                ((EditText) findViewById(R.id.emailInput)).getText().clear();

                String toastMessage = strNote + " on " + strDate;
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}