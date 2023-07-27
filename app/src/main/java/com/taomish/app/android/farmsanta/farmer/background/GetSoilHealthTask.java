package com.taomish.app.android.farmsanta.farmer.background;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.SoilHealth;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_SoilHealth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetSoilHealthTask extends FarmSantaBaseAsyncTask {

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
    public ArrayList<SoilHealth> doInBackground(String... strings) {
        List<DB_SoilHealth> dbList              = new DBController(getContext()).getAllSoilHealth();
        if (dbList != null && dbList.size() > 0) {
            String arrayString                  = new Gson().toJson(dbList);
            SoilHealth[] soilHealths            = new Gson().fromJson(arrayString, SoilHealth[].class);
            ArrayList<SoilHealth> list          = new ArrayList<>();
            Collections.addAll(list, soilHealths);

            return list;
        } else {
            ArrayList<SoilHealth> serviceList   = new ServiceController(getContext()).getSoilHealth();
            String arrayString                  = new Gson().toJson(serviceList);
            DB_SoilHealth[] soilHealths         = new Gson().fromJson(arrayString, DB_SoilHealth[].class);
            ArrayList<DB_SoilHealth> list       = new ArrayList<>();
            Collections.addAll(list, soilHealths);
            new DBController(getContext()).saveAllSoilHealth(list);

            return serviceList;
        }
    }
}
