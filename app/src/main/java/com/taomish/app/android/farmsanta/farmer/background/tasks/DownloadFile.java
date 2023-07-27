package com.taomish.app.android.farmsanta.farmer.background.tasks;

import android.net.Uri;

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask;
import com.taomish.app.android.farmsanta.farmer.utils.FileUtil;

import java.io.File;
import java.io.IOException;

public class DownloadFile extends FarmSantaBaseAsyncTask {

    @Override
    public void onTaskSuccess(Object... obj) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskSuccess(getData());
        }
    }

    @Override
    public void onTaskFailure(String reason) {
        if (getOnTaskCompletion() != null) {
            getOnTaskCompletion().onTaskFailure(reason, "Could not download pdf file");
        }
    }

    @Override
    public String doInBackground(String... string) {
        String fileUrl = string[0];
        String fileName = string[1];
        String downloadDirectory = getContext().getFilesDir().toString();
        File folder = new File(downloadDirectory, "pop");

        boolean mkdir = folder.mkdir();

        if (!folder.exists() && !mkdir)
            return null;

        try {
            File storageDirectory = getContext().getExternalMediaDirs()[0];
            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(storageDirectory, fileName + ".pdf");
            boolean newFile = pdfFile.createNewFile();
            if (newFile || pdfFile.exists()) {
                FileUtil fileUtil = new FileUtil();
                boolean downloadFile = fileUtil.downloadFile(fileUrl, pdfFile);
                if (downloadFile) {
                    fileUtil.copyFile(getContext(), Uri.fromFile(pdfFile));
                    return pdfFile.getAbsolutePath();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
