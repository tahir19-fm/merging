package com.taomish.app.android.farmsanta.farmer.background;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetCropRecommendationsTask extends FarmSantaBaseAsyncTask {
    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskFailure(reason, getErrorReason());
        }
    }

    @Override
    public ArrayList<CropRecommendation> doInBackground(String... strings) {
        List<DB_CropRecommendation> dbList      = new DBController(getContext()).getAllCropRecommendation();
        if (dbList != null && dbList.size() > 0) {
            String arrayString                  = new Gson().toJson(dbList);
            CropRecommendation[] recommendations= new Gson().fromJson(arrayString, CropRecommendation[].class);
            ArrayList<CropRecommendation> list  = new ArrayList<>();
            Collections.addAll(list, recommendations);

            return list;
        } else {
            ArrayList<CropRecommendation> list  = new ServiceController(getContext()).getCropSpecificRecommendations();
            String arrayString                  = new Gson().toJson(list);
            DB_CropRecommendation[] dbArray     = new Gson().fromJson(arrayString, DB_CropRecommendation[].class);
            ArrayList<DB_CropRecommendation> dList = new ArrayList<>();
            Collections.addAll(dList, dbArray);
            new DBController(getContext()).saveAllCropRecommendations(dList);

            return list;
        }
    }
}
