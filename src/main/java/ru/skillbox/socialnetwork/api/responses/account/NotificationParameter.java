package ru.skillbox.socialnetwork.api.responses.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationParameter {

    @JsonProperty("enable")
    private Boolean enable;

    @JsonProperty("type")
    private NotificationType type;

    public NotificationParameter() {}

    public NotificationParameter(NotificationType type, Boolean enable) {
        this.type = type;
        this.enable = enable;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
