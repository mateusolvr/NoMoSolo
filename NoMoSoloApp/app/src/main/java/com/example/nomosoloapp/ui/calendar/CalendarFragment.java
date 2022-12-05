package com.example.nomosoloapp.ui.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.Calendar01;
import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private static FragmentCalendarBinding binding;
    private static RecyclerView recyclerView;
    private static DBManager dbManager;
    private static String userID;

    private TextView selectedDateTV;
    private Button pickDateBtn;
    private Button postNoteBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        selectedDateTV = binding.idTVSelectedDate;
        pickDateBtn = binding.btnPickDate;
        postNoteBtn = binding.btnAddNote;

        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userID = bundle.getString("userID");
        }

        showNotes(getContext());

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), R.style.DatePickerStyle,
                        (view1, year1, month1, day1) -> selectedDateTV.setText(day1 + "-" + (month1 + 1) + "-" + year1),
                        year, month, day);

                datePickerDialog.show();
            }
        });

        postNoteBtn.setOnClickListener(view -> {
            EditText note = (EditText) binding.noteInput;

            String strNote, strDate;
            Date noteDate = null;
            strNote = note.getText().toString();
            strDate = selectedDateTV.getText().toString();
            if (strDate.equals(getString(R.string.chooseDate))) {
                Toast.makeText(getActivity(), "Please select the date!", Toast.LENGTH_SHORT).show();
            } else if (strNote.equals("")) {
                Toast.makeText(getActivity(), "Please write something!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    noteDate = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                binding.noteInput.getText().clear();
                selectedDateTV.setText(getString(R.string.chooseDate));
                dbManager.createNewNote(userID, strNote, noteDate);
                showNotes(getContext());
            }
        });

        return root;
    }

    public static void showNotes(Context c) {
        recyclerView = binding.notesRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));

        ArrayList<Note> notes = new ArrayList<>();
        try {
            notes = dbManager.getNotes(userID);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CalendarNotesAdapter adapter = new CalendarNotesAdapter(notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}