package ru.skillbox.socialnetwork.api.responses;

public class TagResponse {

    private int id;
    private String tag;

    public TagResponse() {
    }

    public TagResponse(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
