package com.example.nomosoloapp.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.Note;
import com.example.nomosoloapp.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CalendarNotesAdapter extends RecyclerView.Adapter<CalendarNotesAdapter.ViewHolder> {

    private DBManager dbManager;

    private ArrayList<Note> allNotes;

    public CalendarNotesAdapter(ArrayList<Note> data) {
        this.allNotes = data;
    }

    @NonNull
    @Override
    public CalendarNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater =LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.calendar_notes_recycler_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(noteView);

        dbManager = new DBManager(context);
        dbManager.open();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = allNotes.get(position);

        TextView date = holder.date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(dateFormat.format(note.getDate()));

        Button nextBtn = holder.btnNext;
        Button deleteNoteBtn = holder.deleteBtn;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), note.getNote(), Toast.LENGTH_SHORT).show();
            }
        });

        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.deleteNote(note.getId());
                CalendarFragment.showNotes(view.getContext());
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, noteText;
        public Button btnNext, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.noteDate);
            btnNext = (Button) itemView.findViewById(R.id.btnViewNote);
            deleteBtn = (Button) itemView.findViewById(R.id.btnDeleteNote);
        }

        public TextView getNoteDate() {return date;}
        public TextView getNoteText() {return noteText;}
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }
}
