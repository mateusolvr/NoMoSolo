package com.example.nomosoloapp.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.R;

import java.util.ArrayList;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerView;

    ArrayList<String[]> matchesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        matchesList.add(new String[]{"Potential Match","Guitar","Blues"});


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