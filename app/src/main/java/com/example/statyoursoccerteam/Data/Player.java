package com.example.statyoursoccerteam.Data;

import com.google.gson.annotations.SerializedName;

public class Player {

    private String userId;
    private String id;
    private String Title;

    @SerializedName("body")
    private String text;

    public Player(String userId, String id, String title, String text) {
        this.userId = userId;
        this.id = id;
        Title = title;
        this.text = text;
    }


    public String getUserId() { return userId; }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return text;
    }
}
