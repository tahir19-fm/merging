package com.taomish.app.android.farmsanta.farmer.models.api.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subscribe {

    @SerializedName("tokens")
    @Expose
    private List<String> tokens = null;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("topics")
    @Expose
    private List<String> topics = null;

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

}
