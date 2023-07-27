package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;

import java.io.File;

public class UploadProfilePicTask extends FarmSantaBaseAsyncTask {

    private final File file;

    public UploadProfilePicTask(File file) {
        this.file = file;
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
    public String doInBackground(String... strings) {
        return new ServiceController(getContext()).uploadProfilePic(file);
    }
}
