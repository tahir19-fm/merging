package com.taomish.app.android.farmsanta.farmer.baseclass;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.taomish.app.android.farmsanta.farmer.activities.FetchLocationActivity;
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult;

public abstract class FarmSantaBaseActivity extends FetchLocationActivity {

    public MutableLiveData<LocationRequestResult.LocationData> locationDataMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initContentView());
        init();
        initUIElements();
        initListeners();
        initData();
    }

    @Override
    protected void onPause() {
        doTheseOnPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        doTheseOnDestroy();
        super.onDestroy();
    }

    public abstract View initContentView();

    public abstract void init();

    public abstract void initUIElements();

    public abstract void initListeners();

    public abstract void initData();

    public void doTheseOnPause() {

    }

    public void doTheseOnDestroy() {

    }

    @Override
    public void onLocationAvailable(@NonNull LocationRequestResult.LocationData locationData) {
        super.onLocationAvailable(locationData);
        locationDataMutableLiveData.postValue(locationData);
        stopLocationRequest();
    }

    @Override
    public void onLocationFailure() {
        super.onLocationFailure();
        setRequestIfGpsRejectedByUser(true);
    }
}
