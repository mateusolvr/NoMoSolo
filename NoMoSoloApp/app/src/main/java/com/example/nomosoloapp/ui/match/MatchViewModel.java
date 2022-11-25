package com.example.nomosoloapp.ui.match;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MatchViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MatchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is match fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}