package com.taomish.app.android.farmsanta.farmer.background.db;


import androidx.annotation.WorkerThread;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.DBController;


public class ClearDBTask extends FarmSantaBaseAsyncTask {
    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskFailure("Service Error", getErrorReason());
        }
    }

    @Override
    public Boolean doInBackground(String... strings) {
        new DBController(getContext()).clearDb();
        return Boolean.TRUE;
    }
}
