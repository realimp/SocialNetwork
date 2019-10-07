package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.skillbox.socialnetwork.entities.PostType;
import java.util.List;

public class PersonsWallPost {

    private Integer id;
    private Long time;
    private BasicPerson author;
    private String title;
    @JsonProperty("post_text")
    private String postText;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    private Integer likes;
    private List<String> tags;
    @JsonProperty("my_like") private Boolean myLike;
    private List<Comment> comments;
    private PostType type;

    public PersonsWallPost() {}

    public Boolean isBlocked() {
        return isBlocked;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public BasicPerson getAuthor() {
        return author;
    }

    public void setAuthor(BasicPerson author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getMyLike() {
        return myLike;
    }

    public void setMyLike(Boolean myLike) {
        this.myLike = myLike;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
