package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"parent_id", "comment_text", "id", "time", "author", "is_blocked", "post_id", "sub_comments"})
public class Comment {

    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("comment_text")
    private String commentText;
    private Integer id;
    private Long time;
    private BasicPerson author;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("sub_comments")
    private List<CommentModel> subComments;

    public Comment() {}

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

    public BasicPerson getAuthor() {
        return author;
    }

    public void setAuthor(BasicPerson author) {
        this.author = author;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<CommentModel> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<CommentModel> subComments) {
        this.subComments = subComments;
    }

}
