package com.taomish.app.android.farmsanta.farmer.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.taomish.app.android.farmsanta.farmer.controller.ServiceController;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener;
import com.taomish.app.android.farmsanta.farmer.libs.background.CustomCallable;
import com.taomish.app.android.farmsanta.farmer.libs.background.CustomHandlerThread;
import com.taomish.app.android.farmsanta.farmer.libs.background.CustomRunnable;
import com.taomish.app.android.farmsanta.farmer.libs.background.CustomThreadPoolManager;
import com.taomish.app.android.farmsanta.farmer.libs.background.interfaces.UiThreadCallback;
import com.taomish.app.android.farmsanta.farmer.libs.background.utils.ThreadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class UploadFilesTask implements UiThreadCallback {
    private static final String TAG = "skt";
    private final ArrayList<File> fileArrayList;
    private ArrayList<String> responseList;
    private final Context context;

    private static int completedTaskCount = 0;

    private CustomHandlerThread mHandlerThread;
    private CustomThreadPoolManager mCustomThreadPoolManager;

    private OnTaskCompletionListener onTaskCompletionListener;

    private ProgressDialog progressDialog;

    private UiHandler mUiHandler;

    public UploadFilesTask(ArrayList<File> fileArrayList, Context context) {
        this.fileArrayList = fileArrayList;
        this.context = context;
    }

    public OnTaskCompletionListener getOnTaskCompletionListener() {
        return onTaskCompletionListener;
    }

    public void setOnTaskCompletionListener(OnTaskCompletionListener onTaskCompletionListener) {
        this.onTaskCompletionListener = onTaskCompletionListener;
    }

    public void begin() {
        if (fileArrayList == null || fileArrayList.size() == 0) {
            end();
            return;
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading files");
        progressDialog.show();

        // Initialize the handler for UI thread to handle message from worker threads
        mUiHandler = new UiHandler(Looper.getMainLooper());

        // create and start a new HandlerThread worker thread
        mHandlerThread = new CustomHandlerThread("HandlerThread");
        mHandlerThread.setUiThreadCallback(this);
        mHandlerThread.start();

        // get the thread pool manager instance
        mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance();
        // CustomThreadPoolManager stores activity as a weak reference. No need to unregister.
        mCustomThreadPoolManager.setUiThreadCallback(this);

        responseList = new ArrayList<>();
        completedTaskCount = 0;

        sendTasksToThreadPool();
    }

    public void sendRunnableToHandlerThread(View view) {
        // send a runnable to run on the HandlerThread
        CustomRunnable runnable = new CustomRunnable();
        runnable.setUiThreadCallback(this);
        mHandlerThread.postRunnable(runnable);
    }

    // onClick handler for Send Message button
    public void sendMessageToHandlerThread() {
        // add a message to worker thread's message queue
        mHandlerThread.addMessage(1);
    }

    private void sendTasksToThreadPool() {
        int fileCount = fileArrayList != null ? fileArrayList.size() : 0;
        Log.d(TAG, "Count = " + fileCount);
        for (int i = 0; i < fileCount; i++) {
            File file = fileArrayList.get(i);
            Log.d(TAG, "filename = " + file.getName());
            UploadCallable callable = new UploadCallable(file, file.getName());
            callable.setCustomThreadPoolManager(mCustomThreadPoolManager);
            mCustomThreadPoolManager.addCallable(callable);
        }
    }

    // handler for Stop All Thread
    public void cancelAllTasksInThreadPool() {
        mCustomThreadPoolManager.cancelAllTasks();
    }

    // Send message from worker thread to the UI thread
    @Override
    public void publishToUiThread(Message message) {
        // add the message from worker thread to UI thread's message queue
        if (mUiHandler != null) {
            mUiHandler.sendMessage(message);
        }
    }

    private void end() {
        // clear the message queue of HandlerThread worker thread and stop the current task
        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread.interrupt();
        }

        if (progressDialog != null) {
            progressDialog.cancel();
        }

        if (getOnTaskCompletionListener() != null) {
            getOnTaskCompletionListener().onTaskSuccess(responseList);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class UiHandler extends Handler {

        public UiHandler(Looper looper) {
            super(looper);
        }

        // This method will run on UI thread
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // Our communication protocol for passing a string to the UI thread
            if (msg.what == ThreadUtil.MESSAGE_ID) {
                completedTaskCount++;

                Bundle bundle = msg.getData();
                String messageText = bundle.getString(ThreadUtil.MESSAGE_BODY, ThreadUtil.EMPTY_MESSAGE);

                if (messageText != null)
                    responseList.add(messageText);

                Log.d(TAG, "Handler message = " + messageText + ", Readable time = " + ThreadUtil.getReadableTime());
                int finalCount = fileArrayList != null ? fileArrayList.size() : 0;

                Log.d(TAG, "Count = " + completedTaskCount + ", max = " + finalCount);

                if (completedTaskCount == finalCount) {
                    end();
                }
            }
        }
    }

    private class UploadCallable extends CustomCallable {
        private final File file;
        private final String fileName;

        public UploadCallable(File file, String fileName) {
            this.file = file;
            this.fileName = fileName;
        }

        @SuppressWarnings("RedundantThrows")
        @Override
        public Object call() throws Exception {
            try {
                // check if thread is interrupted before lengthy operation
                if (Thread.interrupted()) throw new InterruptedException();

                ServiceController controller = new ServiceController(context);
                String[] res = controller.uploadUserDoc(file, fileName);
                Log.d(TAG, "Service response = " + Arrays.toString(res));

                // After work is finished, send a message to CustomThreadPoolManager
                Message message = ThreadUtil.createMessage(
                        ThreadUtil.MESSAGE_ID, (res != null && res.length > 0) ? res[0] : null);

                if (getCustomThreadPoolManagerWeakReference() != null
                        && getCustomThreadPoolManagerWeakReference().get() != null) {

                    getCustomThreadPoolManagerWeakReference().get().sendMessageToUiThread(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
