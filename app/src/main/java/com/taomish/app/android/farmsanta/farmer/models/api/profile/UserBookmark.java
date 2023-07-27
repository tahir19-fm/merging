package com.taomish.app.android.farmsanta.farmer.models.api.profile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark;

public class UserBookmark {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("userId")
    @Expose
    private Object userId;
    @SerializedName("lastUpdated")
    @Expose
    private Object lastUpdated;
    @SerializedName("createdOn")
    @Expose
    private Object createdOn;
    @SerializedName("bookmarks")
    @Expose
    private List<BookMark> bookmarks = null;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Object lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

    public List<BookMark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<BookMark> bookmarks) {
        this.bookmarks = bookmarks;
    }

}
