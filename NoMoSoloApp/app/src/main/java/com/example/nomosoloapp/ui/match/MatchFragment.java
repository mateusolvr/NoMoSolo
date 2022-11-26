package com.example.nomosoloapp.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nomosoloapp.databinding.FragmentMatchBinding;

public class MatchFragment extends Fragment {

    private FragmentMatchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MatchViewModel matchViewModel =
                new ViewModelProvider(this).get(MatchViewModel.class);

        binding = FragmentMatchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ScrollView scrollView = binding.scrollMatch;

        //Implement ViewModel here for backend data transferring
        //matchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}