package ru.skillbox.socialnetwork.entities;

import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="notification_settings")
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @NotNull
    @Column(name = "notification_type_id")
    private NotificationTypeCode notificationTypeCode;

    @Column(name = "enable")
    private Boolean enable;

    public NotificationSettings() {
    }

    public NotificationSettings(@NotNull Person person, @NotNull NotificationTypeCode notificationTypeCode, Boolean enable) {
        this.person = person;
        this.notificationTypeCode = notificationTypeCode;
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public NotificationTypeCode getNotificationTypeCode() {
        return notificationTypeCode;
    }

    public void setNotificationTypeCode(NotificationTypeCode notificationTypeCode) {
        this.notificationTypeCode = notificationTypeCode;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
