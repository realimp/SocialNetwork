package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class DialogMessageList {

    private List<DialogMessage> messageList;

    public DialogMessageList() {}

    public DialogMessageList(List<DialogMessage> messageList) {
        this.messageList = messageList;
    }

    public List<DialogMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<DialogMessage> messageList) {
        this.messageList = messageList;
    }

}