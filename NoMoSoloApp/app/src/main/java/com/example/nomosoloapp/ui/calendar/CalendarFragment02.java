package com.example.nomosoloapp.ui.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;

import java.util.ArrayList;

public class CalendarFragment02 extends Fragment {

    private FragmentCalendarBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        CalendarViewModel calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_calendar02, container, false);

        recyclerView = view.findViewById(R.id.notes_rView_2);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Note> notes = new ArrayList<>();

        CalendarNotesAdapter adapter = new CalendarNotesAdapter(notes);
        recyclerView.setAdapter(adapter);

        return view;
    }
}