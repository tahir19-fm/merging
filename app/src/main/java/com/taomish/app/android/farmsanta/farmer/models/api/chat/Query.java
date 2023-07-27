
package com.taomish.app.android.farmsanta.farmer.models.api.chat;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("contexts")
    @Expose
    private List<Context> contexts = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

}
