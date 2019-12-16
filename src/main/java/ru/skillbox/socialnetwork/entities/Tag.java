package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;

@Entity
@Table (name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tag;

    public Tag() {}

    public Tag(String text)
    {
        setText(text);
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return tag;
    }

    public void setText(String text) {
        this.tag = text;
    }
}
