package com.taomish.app.android.farmsanta.farmer.models.api.profile;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

public class UserDocs {
    private Bitmap userProfile;
    private ArrayList<File> fileArrayList;

    public Bitmap getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Bitmap userProfile) {
        this.userProfile = userProfile;
    }

    public ArrayList<File> getFileArrayList() {
        return fileArrayList;
    }

    public void setFileArrayList(ArrayList<File> fileArrayList) {
        this.fileArrayList = fileArrayList;
    }
}
