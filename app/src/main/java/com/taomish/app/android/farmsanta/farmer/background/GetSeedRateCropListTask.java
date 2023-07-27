package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.SeedRateCrop;

public class GetSeedRateCropListTask extends FarmSantaBaseAsyncTask {

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
    public String doInBackground(String... strings) {
        ServiceController controller = new ServiceController(getContext());
        SeedRateCrop[] crops = controller.getSeedRateCropList();

        setData(crops);
        setTaskSuccess(crops != null);

        return null;
    }
}
