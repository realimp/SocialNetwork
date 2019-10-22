package ru.skillbox.socialnetwork.api.responses;

public class NotificationParameter {

    private Boolean enable;
    private NotificationTypeCode type;

    public NotificationParameter() {}

    public NotificationParameter(NotificationTypeCode type, Boolean enable) {
        this.type = type;
        this.enable = enable;
    }

    public NotificationTypeCode getType() {
        return type;
    }

    public void setType(NotificationTypeCode type) {
        this.type = type;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
