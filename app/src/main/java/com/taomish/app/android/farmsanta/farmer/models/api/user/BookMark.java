package com.taomish.app.android.farmsanta.farmer.models.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.constants.PopConstants;

public class BookMark {
    @SerializedName("bookmarkType")
    @Expose
    private String bookmarkType;
    @SerializedName("referenceUUID")
    @Expose
    private String referenceUUID;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("updatedBy")
    @Expose
    public String updatedBy;
    @SerializedName("createdTimestamp")
    @Expose
    public String createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    public String updatedTimestamp;
    @SerializedName("tenantId")
    @Expose
    public Object tenantId;
    @SerializedName("username")
    @Expose
    public Object username;

    public String getBookmarkType() {
        return bookmarkType;
    }

    public void setBookmarkType(String bookmarkType) {
        this.bookmarkType = bookmarkType;
    }

    public String getReferenceUUID() {
        return referenceUUID;
    }

    public void setReferenceUUID(String referenceUUID) {
        this.referenceUUID = referenceUUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}