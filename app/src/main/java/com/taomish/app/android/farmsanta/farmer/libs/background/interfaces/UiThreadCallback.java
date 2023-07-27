package com.taomish.app.android.farmsanta.farmer.libs.background.interfaces;

import android.os.Message;

public interface
UiThreadCallback {
    void publishToUiThread(Message message);
}
