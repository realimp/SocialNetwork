package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Activity {

    private Boolean online;
    @JsonProperty("last_activity")
    private Long lastActivity;

    public Activity() {}

    public Activity(Boolean online, Long lastActivity) {
        this.online = online;
        this.lastActivity = lastActivity;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Long lastActivity) {
        this.lastActivity = lastActivity;
    }

}
