package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendStatus {

    @JsonProperty("user_id")
    private Integer userId;
    private FriendStatusType status;

    public FriendStatus() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public FriendStatusType getStatus() {
        return status;
    }

    public void setStatus(FriendStatusType status) {
        this.status = status;
    }

}
