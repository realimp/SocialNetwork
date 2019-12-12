package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogMessage {

    private Integer id;
    private Long time;
    private BasicPerson author;
    @JsonProperty("recipient")
    private BasicPerson recipient;
    @JsonProperty("message_text")
    private String text;
    @JsonProperty("read_status")
    private ReadStatus readStatus;

    public DialogMessage() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public BasicPerson getAuthor() {
        return author;
    }

    public void setAuthor(BasicPerson author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReadStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public BasicPerson getRecipient() {
        return recipient;
    }

    public void setRecipient(BasicPerson recipient) {
        this.recipient = recipient;
    }
}
