package com.example.nomosoloapp.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.Message;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.User;

import java.text.ParseException;
import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private String userID;
    private DBManager dbManager;

    ArrayList<User> chatList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Populate chatList with only users who were sent message using backend algorithm here. Placeholder using matchlist for now
        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userID = bundle.getString("userID");
        }

//        ArrayList<User> usersMatched = dbManager.getMatches(userID);
//
//        for(int i = 0; i < usersMatched.size(); i++){
//            User currUser = usersMatched.get(i);
//            chatList.add(currUser);
//        }

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        try {
            loadRecyclerView(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
    }

    private void loadRecyclerView(View view) throws ParseException {
        ArrayList<Message> myTopMessage =  dbManager.getChatPeople(userID);
        ArrayList<String> myNames = new ArrayList<>();
        for(int i = 0; i < myTopMessage.size(); i++){
            User newUser = dbManager.getUserProfile(myTopMessage.get(i).getToUserId());
            myNames.add(newUser.getFn() + " " + newUser.getLn());
        }

        recyclerView = view.findViewById(R.id.chatListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ChatListAdapter adapter = new ChatListAdapter(myTopMessage, myNames);
        recyclerView.setAdapter(adapter);
    }
}