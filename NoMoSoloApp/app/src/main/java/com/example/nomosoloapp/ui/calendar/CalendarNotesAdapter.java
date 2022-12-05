package com.example.nomosoloapp.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.ui.match.MatchAdapter;

import java.util.ArrayList;

public class CalendarNotesAdapter extends RecyclerView.Adapter<CalendarNotesAdapter.ViewHolder> {

    private ArrayList<Note> allNotes;
    Note dummyNote = new Note("01-01-2022", "Guitar practice with Luca");
    Note dummyNote2 = new Note("01-05-2022", "Meet up with Liam @ 12pm");
    Note dummyNote3 = new Note("01-08-2022", "Ask out Georgia");
    Note dummyNote4 = new Note("01-22-2022", "Ask out Lyla if Georgia says no");

    public CalendarNotesAdapter(ArrayList<Note> data) {
        this.allNotes = data;
        allNotes.add(dummyNote);
        allNotes.add(dummyNote2);
        allNotes.add(dummyNote3);
        allNotes.add(dummyNote4);
    }

    @NonNull
    @Override
    public CalendarNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem = layoutInflater.inflate(R.layout.calendar_notes_recycler_layout, parent, false);

        Context context =parent.getContext();
        LayoutInflater inflater =LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.calendar_notes_recycler_layout, parent, false);
        ViewHolder viewHolder =new ViewHolder(noteView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = allNotes.get(position);

        TextView date = holder.date;
        date.setText(note.getDate());

        Button nextBtn = holder.btnNext;
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = null;
                i = new Intent(view.getContext(), NoteDisplay.class);
                i.putExtra("Note", note.getNote());
                view.getContext().startActivity(i);

//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                CalnedarNotesView notesView = new CalnedarNotesView();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.calendarNotes_layour,
//                        notesView).addToBackStack(null).commit();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, noteText;
        public Button btnNext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.noteDate);
            btnNext = (Button) itemView.findViewById(R.id.btnViewNote);
        }

        public TextView getNoteDate() {return date;}
        public TextView getNoteText() {return noteText;}
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }
}
