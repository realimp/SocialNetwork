package ru.skillbox.socialnetwork.entities;

import ru.skillbox.socialnetwork.controllers.DialogController;

import java.util.Objects;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "time")
  private Date time;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private Person author;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "recipient_id")
  private Person recipient;

  @Column(name = "message_text")
  private String messageText;

  @Column(name = "read_status")
  private String readStatus;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "dialog_id", nullable = false)
  private Dialog dialog;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public String getReadStatus() {
    return readStatus;
  }

  public Boolean getDeleted() {
    return isDeleted;
  }

  public Dialog getDialog() {
    return dialog;
  }

  public void setDialog(Dialog dialog) {
    this.dialog = dialog;
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

//  @Override
//  public String toString() {
//    return "Message{" +
//        "id=" + id +
//        ", time=" + time +
//        ", author=" + author.getId() +
//        ", recipient=" + recipient.getId() +
//        ", messageText='" + messageText + '\'' +
//        ", readStatus='" + readStatus + '\'' +
//        ", dialogId=" + dialog.getId() +
//        ", isDeleted=" + isDeleted +
//        '}';
//  }
}
