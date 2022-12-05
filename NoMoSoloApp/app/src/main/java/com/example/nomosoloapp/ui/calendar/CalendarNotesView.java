package com.example.nomosoloapp.ui.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.databinding.FragmentCalendarNotesViewBinding;

import java.text.SimpleDateFormat;


public class CalendarNotesView extends Fragment {

    private Note note;
    private FragmentCalendarNotesViewBinding binding;

    public CalendarNotesView(Note note) {
        // Required empty public constructor
        this.note = note;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCalendarNotesViewBinding.inflate(inflater, container, false);
        loadNote();

        return getView();
    }

    public void loadNote() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binding.noteDateTV.setText(dateFormat.format(note.getDate()));
        binding.viewNote.setText(note.getNote());
    }
}