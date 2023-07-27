package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.login.User;
import com.taomish.app.android.farmsanta.farmer.models.api.user.UserToken;

public class AuthenticateUserTask extends FarmSantaBaseAsyncTask {

    User user;

    public AuthenticateUserTask(User user) {
        this.user = user;
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
            getOnTaskCompletion().onTaskFailure(reason, getContext().getString(R.string.otp_error_msg));
        }
    }

    @Override
    public UserToken doInBackground(String... strings) {
        ServiceController controller = new ServiceController(getContext());
        return controller.authenticateUser(user);
    }
}
