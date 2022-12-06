package com.example.nomosoloapp.ui.chat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.Message;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentCalendarBinding;
import com.example.nomosoloapp.databinding.FragmentChatMessagesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatMessages extends Fragment {

//    private static FragmentChatMessagesBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Message> messages = new ArrayList<>();
    private String toUserId, toUserName, fromUserId;
    private static DBManager dbManager;
    private NestedScrollView myScrollView;

    public ChatMessages(String toUserId, String toUserName) {
        this.toUserId = toUserId;
        this.toUserName = toUserName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        binding = FragmentChatMessagesBinding.inflate(inflater, container, false);
        View myChatMessages = inflater.inflate(R.layout.fragment_chat_messages, container, false);
        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            fromUserId = bundle.getString("userID");
        }

        try {
            loadRecyclerView(myChatMessages);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        LinearLayout sendMessage = binding.sendMessage;
        LinearLayout sendMessage = (LinearLayout) myChatMessages.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendMessageToChat(myChatMessages);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return myChatMessages;
    }

    private void loadRecyclerView(View view) throws ParseException {
        messages = dbManager.getMessages(fromUserId, toUserId);



        recyclerView = view.findViewById(R.id.chatMessagesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ChatMessagesAdapter adapter = new ChatMessagesAdapter(messages, toUserName, fromUserId);
        recyclerView.setAdapter(adapter);

        myScrollView = view.findViewById(R.id.scrollChatMessages);
        myScrollView.scrollTo(0,myScrollView.getHeight());
        myScrollView.post(new Runnable() {
            @Override
            public void run() {
                myScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void sendMessageToChat(View view) throws ParseException {
        EditText myInput = view.findViewById(R.id.sendMessageInput);
        String currMessage = myInput.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dbManager.insertMessage(fromUserId, toUserId, currMessage, formatter.format(date));
        myInput.setText("");
        loadRecyclerView(view);
    }
}