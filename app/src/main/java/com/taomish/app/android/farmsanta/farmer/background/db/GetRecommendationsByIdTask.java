package com.taomish.app.android.farmsanta.farmer.background.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;

import java.lang.reflect.Type;
import java.util.List;

public class GetRecommendationsByIdTask extends FarmSantaBaseAsyncTask {
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
    public CropRecommendation doInBackground(String... strings) {
        DB_CropRecommendation item              = new DBController(getContext()).getCropRecommendationById(strings[0]);
        String jsonString                       = new Gson().toJson(item);
        return new Gson().fromJson(jsonString, CropRecommendation.class);
    }
}
