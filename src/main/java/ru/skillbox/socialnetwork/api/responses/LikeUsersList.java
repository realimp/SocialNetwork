package ru.skillbox.socialnetwork.api.responses;

public class LikeUsersList {
    private int likes;
    private Integer[] users;

    public LikeUsersList() {
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Integer[] getUsers() {
        return users;
    }

    public void setUsers(Integer[] users) {
        this.users = users;
    }
}
