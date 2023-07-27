package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message;

public class SaveSocialMessageTask extends FarmSantaBaseAsyncTask {

    private final Message message;

    public SaveSocialMessageTask(Message message) {
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
    public String doInBackground(String... strings) {
        ServiceController controller = new ServiceController(getContext());
        boolean isMessageSaved = controller.saveUserPost(message);

        setData(isMessageSaved);
        setTaskSuccess(isMessageSaved);

        return String.valueOf(isMessageSaved);
    }
}
