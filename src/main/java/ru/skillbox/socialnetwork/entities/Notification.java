package ru.skillbox.socialnetwork.entities;



import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="type_id")
    private NotificationTypeCode typeId;

    @Column(name="sent_time")
    @NotNull
    private Date sentDate;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person author;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Person recipient;

    @Column(name="entity_id")
    @NotNull
    private Integer entityId;

    @Column(name="contact")
    @Size(max=80)
    private String contact;

    @Column(name = "is_readed")
    private boolean isViewed;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Notification() {
        this.isViewed = false;
        this.isDeleted = false;
    }

    public Notification(NotificationTypeCode typeId, @NotNull Date sentDate,
                        @NotNull Person author, @NotNull Person recipient, @NotNull Integer entityId,
                        @Size(max = 80) String contact) {
        this.typeId = typeId;
        this.sentDate = sentDate;
        this.author = author;
        this.recipient = recipient;
        this.entityId = entityId;
        this.contact = contact;
        this.isViewed = false;
        this.isDeleted = false;
    }

    public Integer getId() {
        return id;
    }

    public NotificationTypeCode getTypeId() {
        return typeId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Person getAuthor() {
        return author;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public String getContact() {
        return contact;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTypeId(NotificationTypeCode typeId) {
        this.typeId = typeId;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }
}
