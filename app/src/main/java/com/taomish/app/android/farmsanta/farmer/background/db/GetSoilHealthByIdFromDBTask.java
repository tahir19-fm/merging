package com.taomish.app.android.farmsanta.farmer.background.db;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.SoilHealth;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_SoilHealth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetSoilHealthByIdFromDBTask extends FarmSantaBaseAsyncTask {
    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskFailure("Service Error", "Error getting data from service");
        }
    }

    @Override
    public SoilHealth doInBackground(String... strings) {
        DB_SoilHealth item                      = new DBController(getContext()).getSoilHealth(strings[0]);
        String arrayString                      = new Gson().toJson(item);
        return new Gson().fromJson(arrayString, SoilHealth.class);
    }
}
