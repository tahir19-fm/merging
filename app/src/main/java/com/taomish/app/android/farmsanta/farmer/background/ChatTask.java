package com.taomish.app.android.farmsanta.farmer.background;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.models.api.chat.ChatReply;
import com.taomish.app.android.farmsanta.farmer.models.api.chat.Query;

public class ChatTask extends FarmSantaBaseAsyncTask {

    private final Query query;

    public ChatTask(Query query) {
        this.query = query;
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
    public String doInBackground(String... strings) {
        ServiceController controller = new ServiceController(getContext());
        ChatReply chatReply = controller.chat(query);

        setData(chatReply);
        setTaskSuccess(chatReply != null);

        return null;
    }
}
