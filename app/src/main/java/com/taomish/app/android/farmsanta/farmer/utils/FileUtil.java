package com.taomish.app.android.farmsanta.farmer.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.loader.content.CursorLoader;

import com.google.android.gms.common.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    public String copyFile(Context context, Uri selectedImage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                ParcelFileDescriptor descriptor = context.getContentResolver()
                        .openFileDescriptor(selectedImage, "r", null);
                FileInputStream fileInputStream = new FileInputStream(descriptor.getFileDescriptor());
                File file = new File(context.getCacheDir(), getFileName(context, selectedImage));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtils.copyStream(fileInputStream, fileOutputStream);

                return file.getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                return picturePath;
            }
        }
        return "";
    }

    public File getFileFromBitmap(Bitmap bitmap, Context context, String filename) throws IOException {
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), filename);
        boolean created = f.createNewFile();

        if (created) {
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }

        return f;
    }

    public boolean downloadFile(String fileUrl, File directory) {
        final int MEGABYTE = 1024 * 1024;
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    private String getFileName(Context context, Uri fileUri) {
        String name = "";
        Cursor returnCursor = context.getContentResolver()
                .query(fileUri, null,
                        null, null,
                        null);
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();

            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }

        return name;
    }
}
