package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class FriendList {

    private List<PersonResponse> friends;

    public FriendList() {}

    public FriendList(List<PersonResponse> friends) {
        this.friends = friends;
    }

    public List<PersonResponse> getFriends() {
        return friends;
    }

    public void setFriends(List<PersonResponse> friends) {
        this.friends = friends;
    }

}
