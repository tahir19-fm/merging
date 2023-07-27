package com.taomish.app.android.farmsanta.farmer.nutrifragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.taomish.app.android.farmsanta.farmer.R;

public class RentFragment extends Fragment {
    private String title;//String for tab title
    public RentFragment() {
    }
    public RentFragment(String title) {
        this.title = title;//Setting tab title
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragments_rent, container, false);
    }
}
