package com.example.nomosoloapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nomosoloapp.DBManager;
import com.example.nomosoloapp.R;
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

        dbManager = new DBManager(getContext());
        dbManager.open();

        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userID = bundle.getString("userID");
        }

        final Button editProfileBtn = binding.editProfileBtn;

        editProfileBtn.setOnClickListener(view -> {
            Fragment fragment = new EditProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.profile_constraint, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        loadPersonalProfile();

        return binding.getRoot();
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