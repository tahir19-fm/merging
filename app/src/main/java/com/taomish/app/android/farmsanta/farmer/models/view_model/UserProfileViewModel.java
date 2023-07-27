package com.taomish.app.android.farmsanta.farmer.models.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;


public class UserProfileViewModel extends ViewModel {

    private MutableLiveData<Farmer> mProfile;

    public UserProfileViewModel() {
        mProfile = new MutableLiveData<>();
    }

    public LiveData<Farmer> getProfile() {
        return mProfile;
    }

    public void setProfile(Farmer profile) {
        DataHolder.getInstance().setFarmerRegion(profile);
        mProfile.postValue(profile);
    }
}