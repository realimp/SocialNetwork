package ru.skillbox.socialnetwork.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeRequest {

    @JsonProperty("item_id")
    private Integer item_id;
    private String type;

    public LikeRequest() {
    }

    public LikeRequest(Integer item_id, String type) {
        this.item_id = item_id;
        this.type = type;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
