package com.example.nomosoloapp.ui.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private ArrayList<User> allChat;

    public ChatListAdapter(ArrayList<User> data) {
        this.allChat = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView matchName, topMessage;
        public ImageView matchAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.matchName = (TextView) itemView.findViewById(R.id.matchName);
            this.topMessage = (TextView) itemView.findViewById(R.id.topMessage);
            this.matchAvatar = (ImageView) itemView.findViewById(R.id.matchAvatar);
        }

        public TextView getMatchName() {
            return matchName;
        }

        public TextView getTopMessage() {
            return topMessage;
        }

        public ImageView getMatchAvatar() {
            return matchAvatar;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.chat_list_recycler_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User myUser = allChat.get(position);
        if (myUser.getPhoto()[0] != 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(myUser.getPhoto(), 0, myUser.getPhoto().length);
            holder.getMatchAvatar().setImageBitmap(bmp);
        }
        holder.getMatchName().setText(myUser.getFn() + " " + myUser.getLn());

        //Backend change getInstrument to getTopMessage from convos
        holder.getTopMessage().setText("Testing top message");


        //Change MatchProfile to ChatMessages here
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                MatchProfile matchProfile = new MatchProfile(myUser);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.match_constraint, matchProfile).addToBackStack(null).commit();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return allChat.size();
    }
}
