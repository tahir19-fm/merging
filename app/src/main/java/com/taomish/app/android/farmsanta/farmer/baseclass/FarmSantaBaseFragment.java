package com.taomish.app.android.farmsanta.farmer.baseclass;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taomish.app.android.farmsanta.farmer.controller.NavigationController;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnFragmentInteractionListener;

public abstract class FarmSantaBaseFragment extends Fragment {

    private View rootView;
    private Context context;

    private OnFragmentInteractionListener oFIL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        setRootView(initContentView(inflater, container, savedInstanceState));
        initViewsInLayout();
        initListeners();
        initData();
        return getRootView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public abstract void init();

    public abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedState);

    public abstract void initViewsInLayout();

    public abstract void initListeners();

    public abstract void initData();

    public <T extends View> T initThisView(int layoutId) {
        return getRootView().findViewById(layoutId);
    }

    public void update(String tag) {

    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener oFIL) {
        this.oFIL = oFIL;
    }

    public Context getFarmSantaContext() {
        if (getActivity() != null) {
            context = requireActivity().getBaseContext();
        }
        return context;
    }

    public OnFragmentInteractionListener getOnFragmentInteractionListener() {
        return oFIL;
    }

    public NavigationController getFragmentChangeHelper() {
        return NavigationController.getInstance(getActivity());
    }

}