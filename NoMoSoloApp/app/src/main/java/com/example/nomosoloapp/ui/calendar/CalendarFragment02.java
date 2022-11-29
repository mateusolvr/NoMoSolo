package com.example.nomosoloapp.ui.calendar;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;

public class CalendarFragment02 extends Fragment {

    private FragmentCalendarBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //CalendarViewModel calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return inflater.inflate(R.layout.fragment_calendar02, container, false);
    }
}