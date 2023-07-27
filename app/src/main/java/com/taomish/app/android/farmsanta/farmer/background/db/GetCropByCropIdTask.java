package com.taomish.app.android.farmsanta.farmer.background.db;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop;
import com.taomish.app.android.farmsanta.farmer.models.db.crop.DB_Crop;

public class GetCropByCropIdTask extends FarmSantaBaseAsyncTask {
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
    public Crop doInBackground(String... strings) {
        DB_Crop item                      = new DBController(getContext()).getCrop(strings[0]);
        String arrayString                      = new Gson().toJson(item);
        return new Gson().fromJson(arrayString, Crop.class);
    }
}
