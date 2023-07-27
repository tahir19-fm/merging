package com.taomish.app.android.farmsanta.farmer.background.db;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.DB_Cultivar;

public class GetCultivarByIdFromDBTask extends FarmSantaBaseAsyncTask {
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
    public Cultivar doInBackground(String... strings) {
        DB_Cultivar item                        = new DBController(getContext()).getCultivarById(strings[0]);
        String arrayString                      = new Gson().toJson(item);
        return new Gson().fromJson(arrayString, Cultivar.class);
    }
}
