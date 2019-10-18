package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class UserLikeList {

    private Integer likes;
    private List<PersonResponse> users;

    public UserLikeList() {}

    public UserLikeList(Integer likes, List<PersonResponse> users) {
        this.likes = likes;
        this.users = users;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<PersonResponse> getUsers() {
        return users;
    }

    public void setUsers(List<PersonResponse> users) {
        this.users = users;
    }

}
