package ru.skillbox.socialnetwork.entities;

import java.util.Objects;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "time")
  private Date time;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private Person author;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "recipient_id", nullable = false)
  private Person recipient;

  @Column(name = "message_text")
  private String messageText;

  @Column(name = "read_status")
  private String readStatus;

  @Column(name = "dialog_id")
  private int dialogId;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Person getAuthor() {
    return author;
  }

  public void setAuthor(Person author) {
    this.author = author;
  }

  public Person getRecipient() {
    return recipient;
  }

  public void setRecipient(Person recipient) {
    this.recipient = recipient;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public String isReadStatus() {
    return readStatus;
  }

  public void setReadStatus(String readStatus) {
    this.readStatus = readStatus;
  }

  public int getDialogId() {
    return dialogId;
  }

  public void setDialogId(int dialogId) {
    this.dialogId = dialogId;
  }

  public Boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return Objects.equals(id, message.id);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(id, author.getId(), recipient.getId());
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", time=" + time +
        ", author=" + author.getId() +
        ", recipient=" + recipient.getId() +
        ", messageText='" + messageText + '\'' +
        ", readStatus='" + readStatus + '\'' +
        ", dialogId=" + dialogId +
        ", isDeleted=" + isDeleted +
        '}';
  }
}
