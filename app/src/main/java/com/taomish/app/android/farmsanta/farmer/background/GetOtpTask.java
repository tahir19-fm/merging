package com.taomish.app.android.farmsanta.farmer.background;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.user.MobileOtp;

public class GetOtpTask extends FarmSantaBaseAsyncTask {

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
    public MobileOtp doInBackground(String... strings) {
        ServiceController controller = new ServiceController(getContext());
        return controller.getOTP(strings[0]);
    }
}
