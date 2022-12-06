package com.example.nomosoloapp.ui.chat;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.Message;
import com.example.nomosoloapp.R;

import java.util.ArrayList;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    private ArrayList<Message> messages;
    private String userName, fromUserId;
    private View listItem;

    public ChatMessagesAdapter(ArrayList<Message> data, String userName, String fromUserId) {
        this.messages = data;
        this.userName = userName;
        this.fromUserId = fromUserId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatUserName, chatMessage;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.chatUserName = (TextView) itemView.findViewById(R.id.chatUserName);
            this.chatMessage = (TextView) itemView.findViewById(R.id.chatMessage);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.chatBubble);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(R.layout.chat_messages_recycler_layout, parent, false);
        return new ChatMessagesAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message myMessage = messages.get(position);
        if (myMessage.getFromUserId().equals(fromUserId)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            holder.linearLayout.setLayoutParams(params);
//            holder.linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));

            holder.chatUserName.setVisibility(View.INVISIBLE);
            holder.chatMessage.setText(myMessage.getMessage());
        } else {
            holder.chatUserName.setText(userName);
            holder.chatMessage.setText(myMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
