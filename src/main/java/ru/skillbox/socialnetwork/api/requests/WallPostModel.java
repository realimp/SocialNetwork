package ru.skillbox.socialnetwork.api.requests;

import java.util.ArrayList;
import java.util.List;

public class WallPostModel {

  private String titel;
  private String postText;
  private List<String> tags;

  public WallPostModel() {
    tags = new ArrayList<>();
  }

  public WallPostModel(String titel, String postText, List<String> tags) {
    this.titel = titel;
    this.postText = postText;
    this.tags = tags;
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(String titel) {
    this.titel = titel;
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
