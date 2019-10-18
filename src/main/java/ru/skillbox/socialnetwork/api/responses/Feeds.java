package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class Feeds {

    private List<PostResponse> feeds;

    public Feeds() {
    }

    public Feeds(List<PostResponse> feeds) {
        this.feeds = feeds;
    }

    public List<PostResponse> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<PostResponse> feeds) {
        this.feeds = feeds;
    }

}
