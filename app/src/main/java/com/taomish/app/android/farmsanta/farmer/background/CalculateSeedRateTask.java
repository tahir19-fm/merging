package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Calculate;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Properties;

public class CalculateSeedRateTask extends FarmSantaBaseAsyncTask {

    private final Calculate calculate;

    public CalculateSeedRateTask(Calculate calculate) {
        this.calculate = calculate;
    }

    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {

    }

    @Override
    public Properties doInBackground(String... strings) {
        return new ServiceController(getContext()).calculateSeedRate(calculate);
    }
}
