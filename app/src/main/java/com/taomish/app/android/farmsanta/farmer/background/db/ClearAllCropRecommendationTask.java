package com.taomish.app.android.farmsanta.farmer.background.db;


import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;

public class ClearAllCropRecommendationTask extends FarmSantaBaseAsyncTask {
    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskFailure("Service Error", "Error clearing db");
        }
    }

    @Override
    public Boolean doInBackground(String... strings) {
        new DBController(getContext()).deleteAllCropRecommendation();
        return Boolean.TRUE;
    }
}
