package com.taomish.app.android.farmsanta.farmer.baseclass;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.utils.ExtensionsKt;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public abstract class FarmSantaBaseAsyncTask {

    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements

    private OnTaskCompletionListener onTaskCompletionListener;
    private boolean isTaskSuccess;
    private Object data;
    private String errorReason;
    private boolean isCancelable = true;
    private boolean showLoading = true;

    private ProgressDialog progressDialog;
    private Context taskContext;
    private String loadingMessage;
    private int responseCode;

    public void setOnTaskCompletionListener(@NonNull OnTaskCompletionListener onTaskCompletion) {
        this.onTaskCompletionListener = onTaskCompletion;
    }

    public OnTaskCompletionListener getOnTaskCompletion() {
        return onTaskCompletionListener;
    }

    public String getErrorReason() {
        return errorReason != null ? errorReason : taskContext.getString(R.string.something_went_wrong);
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public boolean isTaskSuccess() {
        return isTaskSuccess;
    }

    public void setTaskSuccess(boolean taskSuccess) {
        isTaskSuccess = taskSuccess;
    }

    public void setLoadingMessage(String message) {
        this.loadingMessage = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Context getContext() {
        return taskContext;
    }

    public void setContext(Context context) {
        taskContext = context;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
    }

    public abstract void onTaskSuccess(Object... obj);

    public abstract void onTaskFailure(String reason);

    public abstract Object doInBackground(String... string) throws IOException;

    @SuppressLint("MissingPermission")
    public void execute(String... strings) {
        if (getContext() != null) {
            AppCompatActivity activity = ExtensionsKt.findActivity(getContext());
            if (progressDialog == null) {
                if (activity != null && !activity.isFinishing()) {
                    ContextCompat.getMainExecutor(getContext()).execute(() -> {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage(loadingMessage != null && loadingMessage.length() > 0
                                ? loadingMessage : "Loading...");
                        progressDialog.setCancelable(isCancelable());
                        if (showLoading) progressDialog.show();
                    });
                }
            }
        }

        executor.execute(() -> {
            try {
                setData(doInBackground(strings));
                setTaskSuccess(getData() != null && getData() != Boolean.FALSE);
                onTaskComplete();
            } catch (Exception e) {
                setData(null);
                setErrorReason(e.getMessage());
                setTaskSuccess(false);
                onTaskComplete();
                FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
                Farmer farmer = DataHolder.getInstance().getSelectedFarmer();
                if (farmer != null) crashlytics.setUserId(farmer.getUserId());
                crashlytics.recordException(e);
            }
        });
    }

    private void onTaskComplete() {
        if (getContext() != null) {
            AppCompatActivity activity = ExtensionsKt.findActivity(getContext());
            ContextCompat.getMainExecutor(getContext()).execute(() -> {
                if (activity != null && !activity.isDestroyed() &&
                        progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                callbackOnResponse();
            });
        } else {
            callbackOnResponse();
        }
    }

    private void callbackOnResponse() {
        if (isTaskSuccess()) {
            onTaskSuccess(getData());
        } else {
            onTaskFailure(getErrorReason());
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }


    @Override
    protected void finalize() throws Throwable {
        String className = this.getClass().getSimpleName();
        Log.d("FarmSantaBaseAsyncTask", className + " : is being destructed");
        super.finalize();
    }
}
