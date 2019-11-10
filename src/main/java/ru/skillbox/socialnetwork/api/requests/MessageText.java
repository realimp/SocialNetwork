package ru.skillbox.socialnetwork.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageText {
    @JsonProperty("message_text")
    private String text;

    public MessageText(String text) {
        this.text = text;
    }

    public MessageText() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
