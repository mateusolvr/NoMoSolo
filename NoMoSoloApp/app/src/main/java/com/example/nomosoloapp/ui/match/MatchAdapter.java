package com.example.nomosoloapp.ui.match;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.R;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private ArrayList<String[]> allMatches;
    public MatchAdapter(ArrayList<String[]> data)
    {
        this.allMatches = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView matchName, matchInstrument, matchGenre;
        public ViewHolder(View itemView) {
            super(itemView);
            this.matchName = (TextView) itemView.findViewById(R.id.matchName);
            this.matchInstrument = (TextView) itemView.findViewById(R.id.matchInstrument);
            this.matchGenre = (TextView) itemView.findViewById(R.id.matchGenre);
        }

        public TextView getMatchName() {
            return matchName;
        }
        public TextView getMatchInstrument() {
            return matchInstrument;
        }
        public TextView getMatchGenre() {
            return matchGenre;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.match_recycler_layout,parent,false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String myMatch[] = allMatches.get(position);
        holder.getMatchName().setText(myMatch[0]);
        holder.getMatchInstrument().setText(myMatch[1]);
        holder.getMatchGenre().setText(myMatch[2]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return allMatches.size();
    }
}
