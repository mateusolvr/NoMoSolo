package com.example.nomosoloapp.ui.calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;
import com.example.nomosoloapp.databinding.FragmentCalnedarNotesViewBinding;


public class CalnedarNotesView extends Fragment {

    private Note note;
    private FragmentCalnedarNotesViewBinding binding;

    public CalnedarNotesView() {
        // Required empty public constructor
        //this.note = note;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCalnedarNotesViewBinding.inflate(inflater, container, false);
        loadNote();

        return inflater.inflate(R.layout.fragment_calnedar_notes_view, container, false);
    }

    public void loadNote() {
//        Intent i = getActivity().getIntent();
//        String a = i.getStringExtra("Note");
        String a = note.getNote();
        TextView note = binding.viewNote;
        note.setText(a);
    }
}