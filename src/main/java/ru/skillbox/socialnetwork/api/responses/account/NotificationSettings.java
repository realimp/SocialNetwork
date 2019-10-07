package ru.skillbox.socialnetwork.api.responses.account;

import java.util.List;

public class NotificationSettings {

    private List<NotificationParameter> notificationSettings;

    public NotificationSettings() {}

    public NotificationSettings(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public List<NotificationParameter> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

}
