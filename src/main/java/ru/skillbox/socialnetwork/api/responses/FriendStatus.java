package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;

public class FriendStatus {

    @JsonProperty("user_id")
    private Integer userId;
    private FriendshipStatus status;

    public FriendStatus() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

}
