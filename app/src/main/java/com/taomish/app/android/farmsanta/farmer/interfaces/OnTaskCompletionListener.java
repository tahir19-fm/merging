package com.taomish.app.android.farmsanta.farmer.interfaces;

public interface OnTaskCompletionListener {
    void onTaskSuccess(Object data);
    void onTaskFailure(String reason, String errorMessage);
    default void onTaskComplete() { }
}
