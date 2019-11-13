package ru.skillbox.socialnetwork.api.requests;

public class DialogInvite {
    private String link;

    public DialogInvite(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
