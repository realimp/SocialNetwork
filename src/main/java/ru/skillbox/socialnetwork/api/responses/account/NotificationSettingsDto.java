package ru.skillbox.socialnetwork.api.responses.account;

import java.util.List;

public class NotificationSettingsDto {

    private List<NotificationParameter> notificationSettings;

    public NotificationSettingsDto() {}

    public NotificationSettingsDto(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public List<NotificationParameter> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(List<NotificationParameter> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

}
