package ru.skillbox.socialnetwork.api.responses;

import ru.skillbox.socialnetwork.api.responses.NotificationParameter;

import java.util.List;

public class NotificationSettingsResponse {

    private List<NotificationParameter> notificationSettings;

    public NotificationSettingsResponse() {}

    public NotificationSettingsResponse(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public List<NotificationParameter> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

}
