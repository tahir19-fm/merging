package com.taomish.app.android.farmsanta.farmer.models.api.cultivar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("photoId")
    @Expose
    private String photoId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

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

}
