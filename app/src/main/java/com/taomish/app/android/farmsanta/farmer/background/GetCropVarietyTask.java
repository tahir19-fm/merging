package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CultivationType;

import java.util.ArrayList;
import java.util.Arrays;

public class GetCropVarietyTask extends FarmSantaBaseAsyncTask {

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
        CultivationType[] types = controller.getCropVariety();

        if (types != null) {
            ArrayList<CultivationType> list = new ArrayList<>(Arrays.asList(types));
            setData(list);
        } else
            setData(null);

        setTaskSuccess(types != null);

        return null;
    }
}
