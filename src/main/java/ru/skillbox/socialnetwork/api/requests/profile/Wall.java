package ru.skillbox.socialnetwork.api.requests.profile;

import java.util.ArrayList;
import java.util.List;

public class Wall {

  private String title;
  private String postText;
  private List<String> tags;

  public Wall() {
    tags = new ArrayList<>();
  }

  public Wall(String title, String postText, List<String> tags) {
    this.title = title;
    this.postText = postText;
    this.tags = tags;
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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }
}
