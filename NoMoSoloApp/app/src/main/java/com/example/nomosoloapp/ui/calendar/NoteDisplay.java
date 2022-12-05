package com.example.nomosoloapp.ui.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;

public class NoteDisplay extends AppCompatActivity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        String receivedNote = getIntent().getStringExtra("Note");
        TextView displayeNote = (TextView) findViewById(R.id.viewNote);
        displayeNote.setText(receivedNote);

    }
}