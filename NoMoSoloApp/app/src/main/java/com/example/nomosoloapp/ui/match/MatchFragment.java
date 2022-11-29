package com.example.nomosoloapp.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomosoloapp.R;
import com.example.nomosoloapp.databinding.FragmentMatchBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MatchFragment extends Fragment {

    //private FragmentMatchBinding binding;

    private RecyclerView recyclerView;

    ArrayList<String[]> matchesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        matchesList.add(new String[]{"John Doe","Guitar","Blues"});

        MatchViewModel matchViewModel =
                new ViewModelProvider(this).get(MatchViewModel.class);

        View view = inflater.inflate(R.layout.fragment_match, container, false);

        recyclerView = view.findViewById(R.id.rView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MatchAdapter adapter = new MatchAdapter(matchesList);
        recyclerView.setAdapter(adapter);

        //Implement ViewModel here for backend data transferring
        //matchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
    }
}