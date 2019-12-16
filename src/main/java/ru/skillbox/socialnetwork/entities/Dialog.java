package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "dialog")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    private Integer id;

    @Column(name = "unread_count")
    private Integer unreadCount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "invite_code")
    private String inviteCode;

    @OneToMany(mappedBy = "dialog")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "person2dialog",
        joinColumns = {@JoinColumn(name = "dialog_id")},
        inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<Person> recipients;

    public Dialog() {
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        this.inviteCode = new String(array, Charset.forName("UTF-8"));
        //this.unreadCount = 0;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Person> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Person> recipients) {
        this.recipients = recipients;
    }
}
