package ru.skillbox.socialnetwork.api.responses;

import java.util.List;

public class UserLikeList {

    private Integer likes;
    private List<BasicPerson> users;

    public UserLikeList() {}

    public UserLikeList(Integer likes, List<BasicPerson> users) {
        this.likes = likes;
        this.users = users;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<BasicPerson> getUsers() {
        return users;
    }

    public void setUsers(List<BasicPerson> users) {
        this.users = users;
    }

}
