package com.example.nomosoloapp.ui.match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nomosoloapp.User;
import com.example.nomosoloapp.databinding.FragmentMatchProfileBinding;

public class MatchProfile extends Fragment {

    private FragmentMatchProfileBinding binding;
    private User user;

    public MatchProfile(User user) {
        // Required empty public constructor
        this.user = user;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMatchProfileBinding.inflate(inflater, container, false);
        loadProfile();
        return binding.getRoot();
    }

    public void loadProfile() {
        TextView profileNameTV = binding.matchProfileName;
        TextView profileUserBioTV = binding.matchProfileUserBio;
        TextView profileUserInstrumentTV = binding.matchProfileInstrument;
        TextView profileUserSkillTV = binding.matchProfileUserSkill;
        TextView profileUserGenre1TV = binding.matchProfileGenre1;
        TextView profileUserGenre2TV = binding.matchProfileGenre2;

        profileNameTV.setText(user.getFn() + " " + user.getLn());
        profileUserBioTV.setText(user.getBio());
        profileUserInstrumentTV.setText(user.getInstrument());
        profileUserSkillTV.setText(user.getSkillLevel());
        profileUserGenre1TV.setText(user.getGenre1());
        profileUserGenre2TV.setText(user.getGenre2());
    }
}