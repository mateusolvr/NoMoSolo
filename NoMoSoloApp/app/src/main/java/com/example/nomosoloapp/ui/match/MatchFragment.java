package com.example.nomosoloapp.ui.match;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.R;
import com.example.nomosoloapp.User;

import java.util.ArrayList;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerView;
    private String userID;
    private DBManager dbManager;

    ArrayList<User> matchesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userID = bundle.getString("userID");
        }

        ArrayList<User> usersMatched = dbManager.getMatches(userID);

        for(int i = 0; i < usersMatched.size(); i++){
            User currUser = usersMatched.get(i);
            matchesList.add(currUser);
        }

        View view = inflater.inflate(R.layout.fragment_match, container, false);

        recyclerView = view.findViewById(R.id.rView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MatchAdapter adapter = new MatchAdapter(matchesList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
    }
}