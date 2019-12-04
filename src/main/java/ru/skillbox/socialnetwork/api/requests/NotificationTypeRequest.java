package ru.skillbox.socialnetwork.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;

public class NotificationTypeRequest {
    private Boolean enable;
    @JsonProperty("notification_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
