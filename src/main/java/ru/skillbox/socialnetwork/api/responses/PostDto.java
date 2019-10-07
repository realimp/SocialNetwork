package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "time", "author", "title", "post_text", "is_blocked", "likes", "tags", "my_like", "comments"})
public class PostDto {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private Long time;
    @JsonProperty
    private BasicPersonDto author;
    @JsonProperty
    private String title;
    @JsonProperty("post_text")
    private String postText;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    @JsonProperty
    private Integer likes;
    @JsonProperty
    private List<String> tags;
    @JsonProperty("my_like")
    private Boolean myLike;
    @JsonProperty
    private List<CommentDto> comments;

    @JsonProperty(value = "is_blocked")
    public Boolean isBlocked() {
        return isBlocked;
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

    public BasicPersonDto getAuthor() {
        return author;
    }

    public void setAuthor(BasicPersonDto author) {
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

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
