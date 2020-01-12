package com.example.statyoursoccerteam.Data;

public class Event {
    private String playerName;
    private String eventName;
    private String eventMinute;

    public Event(String playerName, String eventName, String eventMinute) {
        this.playerName = playerName;
        this.eventName = eventName;
        this.eventMinute = eventMinute;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventMinute() {
        return eventMinute;
    }

    public void setEventMinute(String eventMinute) {
        this.eventMinute = eventMinute;
    }
}
