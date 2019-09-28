package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Post")
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "time")
    private Date date;
    @Column(name = "author_id", nullable = false)
    private Integer authorId;
    @Size(max = 50)
    private String title;
    @Column(name = "post_text")
    @Size(max = 500)
    private String text;
    @Column(name = "is_blocked")
    private Integer isBlocked;
    @Column(name = "is_deleted")
    private Integer isDeleted;

    public Post() {}

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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer isBlocked() {
        return isBlocked;
    }

    public void setBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Integer isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,authorId);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", date=" + date +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", isBlocked=" + isBlocked +
                ", isDeleted=" + isDeleted +
                '}';
    }

}
