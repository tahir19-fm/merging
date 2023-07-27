package com.taomish.app.android.farmsanta.farmer.background.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;

import java.lang.reflect.Type;
import java.util.List;

public class GetRecommendationsBySoilCardTask extends FarmSantaBaseAsyncTask {
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
    public List<CropRecommendation> doInBackground(String... strings) {
        List<DB_CropRecommendation> item        = new DBController(getContext()).getCropRecommendationBySoilHealth(strings[0]);
        String arrayString                      = new Gson().toJson(item);
        Type listType                           = new TypeToken<List<CropRecommendation>>() {}.getType();
        return new Gson().fromJson(arrayString, listType);
    }
}
