package ru.skillbox.socialnetwork.api.responses;

public class PlatformLanguage {

    private Integer id;
    private String title;

    public PlatformLanguage() {}

    public PlatformLanguage(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
