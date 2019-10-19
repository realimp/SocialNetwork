package ru.skillbox.socialnetwork.controllers;

import java.util.Date;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

  @GetMapping("/") //Post search
  public String postSearch(String text, Date dateFrom, Date dateTo, Integer offset, Integer itemPerPage) {
    return "Post search";
  }

  @PostMapping("/") //Post creation
  public String postCreate(Date publishDate) { //@RequestBody "title": "string","post_text": "string","tags": ["tag1"]
    return "Post creation";
  }

  @GetMapping("/{id}") //Getting post by ID
  public String postGetById(@PathVariable Integer id) {
    return "Getting post by ID";
  }

  @PutMapping("/{id}") //Editing a post by ID
  public String postEditById(@PathVariable Integer id, Date publishDate) { //@RequestBody "title": "string","post_text": "string","tags": ["tag1"]
    return "Editing a post by ID";
  }

  @DeleteMapping("/{id}") //Delete post by ID
  public String postDeleteById(@PathVariable Integer id) {
    return "Delete post by ID";
  }

  @PutMapping("/{id}/recover") //Post recovery by ID
  public String postRecoverById(@PathVariable Integer id) {
    return "Post recovery by ID";
  }

  @GetMapping("/{id}/comments") //Getting post comments
  public String postGetComments(@PathVariable Integer id, Integer offset, Integer itemPerPage) {
    return "Getting post comments";
  }

  @PostMapping("/{id}/comments") //Post comment
  public String postComments(@PathVariable Integer id) {//@RequestBody
    return "Post comment";
  }

  @PutMapping("/{id}/comments/{comment_id}") //Editing a post comment
  public String postCommentsEdit(@PathVariable Integer id, @PathVariable Integer comment_id) {//@RequestBody
    return "Editing a post comment";
  }

  @DeleteMapping("/{id}/comments/{comment_id}") //Removing a post comment
  public String postCommentsDelete(@PathVariable Integer id, @PathVariable Integer comment_id) {
    return "Removing a post comment";
  }

  @PutMapping("/{id}/comments/{comment_id}/recover") //Comment recovery
  public String postCommentsRecovery(@PathVariable Integer id, @PathVariable Integer comment_id) {
    return "Comment recovery";
  }

  @PostMapping("/{id}/report") //Post a complaint
  public String postCommentComplain(@PathVariable Integer id) {
    return "Comment recovery";
  }

  @PostMapping("/{id}/comments/{comment_id}/report") //File a complaint about post comments
  public String postComplainToComment(@PathVariable Integer id, @PathVariable Integer comment_id) {
    return "File a complaint about post comments";
  }
}
