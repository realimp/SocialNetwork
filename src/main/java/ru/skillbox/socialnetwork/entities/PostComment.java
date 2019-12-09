package ru.skillbox.socialnetwork.entities;

import ru.skillbox.socialnetwork.api.responses.Comment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="post_comment")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "time")
    private Date date;
    @Column(name = "comment_text")
    @Size(max = 50)
    private String commentText;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PostComment parentComment;

    public PostComment() {}

    public PostComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(PostComment parentComment) {
        this.parentComment = parentComment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
