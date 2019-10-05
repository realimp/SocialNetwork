package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"parent_id", "comment_text", "id", "time", "author", "is_blocked"})
public class CommentModelDto {
    @JsonProperty("parent_id") private Integer parentId;
    @JsonProperty("comment_text") private String commentText;
    @JsonProperty private Integer id;
    @JsonProperty private Long time;
    @JsonProperty private BasicPersonDto author;
    @JsonProperty("is_blocked") private Boolean isBlocked;


    @JsonProperty(value="is_blocked")
    public Boolean isBlocked() {
        return isBlocked;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

}
