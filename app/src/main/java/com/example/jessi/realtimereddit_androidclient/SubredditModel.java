package com.example.jessi.realtimereddit_androidclient;

import com.google.gson.annotations.SerializedName;

public class SubredditModel {
    private String kind;
    private SubredditData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public SubredditData getSubredditData() {
        return data;
    }

    public void setSubredditData(SubredditData dataObject) {
        this.data = dataObject;
    }
}

class SubredditData {
    private Subreddits[] children;

    public Subreddits[] getChildren() {
        return children;
    }

    public void setChildren(Subreddits[] children) {
        this.children = children;
    }
}

class Subreddits {
    private Subreddit data;

    public Subreddit getData() {
        return data;
    }

    public void setData(Subreddit data) {
        this.data = data;
    }
}

class Subreddit {
    @SerializedName("display_name")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
