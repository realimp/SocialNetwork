package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class DataResponse {
    private List<FriendStatus> data;

    public DataResponse(List<FriendStatus> data) {
        this.data = data;
    }

    public List<FriendStatus> getData() {
        return data;
    }

    public void setData(List<FriendStatus> data) {
        this.data = data;
    }
}
