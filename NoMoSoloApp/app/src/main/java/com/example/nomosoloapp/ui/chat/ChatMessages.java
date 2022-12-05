package com.example.nomosoloapp.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.nomosoloapp.Message;
import com.example.nomosoloapp.R;

import java.util.ArrayList;

public class ChatMessages extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<Message> messages = new ArrayList<>();

    public ChatMessages() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        View view = inflater.inflate(R.layout.fragment_chat_messages, container, false);

        messages.add(new Message("From", "To", "Hello"));

        recyclerView = view.findViewById(R.id.chatMessagesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ChatMessagesAdapter adapter = new ChatMessagesAdapter(messages);
        recyclerView.setAdapter(adapter);

        return view;
    }
}