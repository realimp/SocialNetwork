package ru.skillbox.socialnetwork.api.responses;

public class UserLike {

    private Boolean likes;

    public UserLike() {}

    public UserLike(Boolean likes) {
        this.likes = likes;
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

}
