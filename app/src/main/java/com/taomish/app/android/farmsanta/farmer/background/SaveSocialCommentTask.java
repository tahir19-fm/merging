package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment;

public class SaveSocialCommentTask extends FarmSantaBaseAsyncTask {

    private final Comment message;

    public SaveSocialCommentTask(Comment message) {
        this.message = message;
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
    public Boolean doInBackground(String... strings) {
        return new ServiceController(getContext()).saveUserComment(message);
    }
}
