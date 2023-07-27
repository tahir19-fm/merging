package com.taomish.app.android.farmsanta.farmer.models.api.chat;

public class Chat {
    private ChatReply reply;
    private Query query;

    public ChatReply getReply() {
        return reply;
    }

    public void setReply(ChatReply reply) {
        this.reply = reply;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
