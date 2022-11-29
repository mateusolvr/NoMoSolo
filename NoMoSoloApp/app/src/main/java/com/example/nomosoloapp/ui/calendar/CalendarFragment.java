package com.example.nomosoloapp.ui.calendar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.nomosoloapp.Calendar01;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView selectedDateTV = binding.idTVSelectedDate;
        final Button pickDateBtn = binding.btnPickDate;
        final Button postNoteBtn = binding.btnAddNote;
        final Button transitionBtn = binding.btnTransition;

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), com.google.android.material.R.style.Theme_Material3_Light_Dialog ,
                        (view1, year1, month1, day1) -> selectedDateTV.setText(day1 + "-" + (month1 + 1) + "-" + year1),
                        year, month, day);

                datePickerDialog.show();
            }
        });

        postNoteBtn.setOnClickListener(view -> {
            EditText note = (EditText) binding.noteInput;

            String strNote, strDate;
            strNote = note.getText().toString();
            strDate = selectedDateTV.getText().toString();

            binding.noteInput.getText().clear();

            String toastMessage = strNote + " on " + strDate;
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
        });

        transitionBtn.setOnClickListener(view -> {
            Fragment fragment = new CalendarFragment02();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        calendarViewModel.getText().observe(getViewLifecycleOwner(), selectedDateTV::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}