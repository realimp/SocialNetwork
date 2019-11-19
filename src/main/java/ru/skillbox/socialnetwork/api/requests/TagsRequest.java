package ru.skillbox.socialnetwork.api.requests;

public class TagsRequest {
    private String[] tags;

    public TagsRequest() {
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
