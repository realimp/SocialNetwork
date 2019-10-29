package ru.skillbox.socialnetwork.api.responses;

public class InviteLink {

    private String link;

    public InviteLink() {}

    public InviteLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
