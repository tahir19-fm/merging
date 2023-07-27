package com.taomish.app.android.farmsanta.farmer.models.api.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("caption")
    @Expose
    private String caption;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
