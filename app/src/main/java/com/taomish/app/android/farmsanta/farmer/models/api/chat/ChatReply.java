package com.taomish.app.android.farmsanta.farmer.models.api.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatReply {
    @SerializedName("text")
    @Expose
    private List<String> text = null;
    @SerializedName("contexts")
    @Expose
    private List<Context> contexts = null;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }
}
