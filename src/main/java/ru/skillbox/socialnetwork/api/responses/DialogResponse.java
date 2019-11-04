package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogResponse {

    private Integer id;
    @JsonProperty("unread_count")
    private Integer unreadCount;
    @JsonProperty("last_message")
    private DialogMessage lastMessage;

    private String inviteCode;

    public DialogResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public DialogMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(DialogMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
