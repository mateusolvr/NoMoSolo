package com.example.nomosoloapp.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.Message;
import com.example.nomosoloapp.R;

import java.util.ArrayList;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    private ArrayList<Message> messages;

    public ChatMessagesAdapter(ArrayList<Message> data) {
        this.messages = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatUserName, chatMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.chatUserName = (TextView) itemView.findViewById(R.id.chatUserName);
            this.chatMessage = (TextView) itemView.findViewById(R.id.chatMessage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.chat_messages_recycler_layout, parent, false);
        return new ChatMessagesAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message myMessage = messages.get(position);
        holder.chatUserName.setText(myMessage.getFromUserId());
        holder.chatMessage.setText(myMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
