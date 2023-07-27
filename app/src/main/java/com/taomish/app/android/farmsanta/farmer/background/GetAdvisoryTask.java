package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory;

public class GetAdvisoryTask extends FarmSantaBaseAsyncTask {
    private final String scoutingId;

    public GetAdvisoryTask(String scoutingId) {
        this.scoutingId = scoutingId;
    }

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
    public Advisory doInBackground(String... strings) {
        return new ServiceController(getContext()).getAdvisory(scoutingId);
    }
}
