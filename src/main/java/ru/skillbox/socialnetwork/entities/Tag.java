package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;

@Entity(name = "Tag")
@Table (name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tag")
    private String text;

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
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
