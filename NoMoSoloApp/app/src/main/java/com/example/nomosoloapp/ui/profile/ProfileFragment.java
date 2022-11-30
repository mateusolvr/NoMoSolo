package com.example.nomosoloapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.User;
import com.example.nomosoloapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String userID;
    private DBManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            userID = getArguments().getString("userID");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            userID = bundle.getString("userID");
        }

        loadPersonalProfile();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loadPersonalProfile() {
        User user = dbManager.getUserProfile(userID);
        if (user == null){
            Toast.makeText(getActivity(), "Error loading user profile.", Toast.LENGTH_SHORT).show();
        }

        TextView profileNameTV = binding.profileName;
        TextView profileUserBioTV = binding.profileUserBio;
        TextView profileUserInstrumentTV = binding.profileUserInstrument;
        TextView profileUserSkillTV = binding.profileUserSkill;
        TextView profileUserGenre1TV = binding.profileUserGenre1;
        TextView profileUserGenre2TV = binding.profileUserGenre2;
        TextView profileSeekingInstrumentTV = binding.profileSeekingInstrument;
        TextView profileSeekingSkillTV = binding.profileSeekingSkill;
        TextView profileSeekingGenreTV = binding.profileSeekingGenre;

        profileNameTV.setText(user.getFn() + " " + user.getLn());
        profileUserBioTV.setText(user.getBio());
        profileUserInstrumentTV.setText(user.getInstrument());
        profileUserSkillTV.setText(user.getSkillLevel());
        profileUserGenre1TV.setText(user.getGenre1());
        profileUserGenre2TV.setText(user.getGenre2());
        profileSeekingInstrumentTV.setText(user.getSeekingInstrument());
        profileSeekingSkillTV.setText(user.getSeekingSkill());
        profileSeekingGenreTV.setText(user.getSeekingGenre());
    }
}