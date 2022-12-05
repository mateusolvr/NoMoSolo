package com.example.nomosoloapp.ui.match;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.R;
import com.example.nomosoloapp.User;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private ArrayList<User> allMatches;

    public MatchAdapter(ArrayList<User> data) {
        this.allMatches = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView matchName, matchInstrument, matchGenre;
        public ImageView matchAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.matchName = (TextView) itemView.findViewById(R.id.matchName);
            this.matchInstrument = (TextView) itemView.findViewById(R.id.matchInstrument);
            this.matchGenre = (TextView) itemView.findViewById(R.id.matchGenre);
            this.matchAvatar = (ImageView) itemView.findViewById(R.id.matchAvatar);
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

        public ImageView getMatchAvatar() {
            return matchAvatar;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.match_recycler_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User myUser = allMatches.get(position);
        if (myUser.getPhoto()[0] != 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(myUser.getPhoto(), 0, myUser.getPhoto().length);
            holder.getMatchAvatar().setImageBitmap(bmp);
        }
        holder.getMatchName().setText(myUser.getFn() + " " + myUser.getLn());
        holder.getMatchInstrument().setText(myUser.getInstrument());
        holder.getMatchGenre().setText(myUser.getGenre1());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MatchProfile matchProfile = new MatchProfile(myUser);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.match_constraint, matchProfile).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allMatches.size();
    }
}
