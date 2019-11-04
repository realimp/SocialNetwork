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

    @Column(name="entity_id")
    @NotNull
    private Integer entityId;

    @Column(name="contact")
    @Size(max=80)
    private String contact;

    @Column(name = "is_readed")
    private boolean isViewed;

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
}
