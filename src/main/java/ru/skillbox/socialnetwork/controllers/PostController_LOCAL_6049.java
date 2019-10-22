package ru.skillbox.socialnetwork.controllers;

import java.util.Date;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseList;

@RestController
@RequestMapping("/post")
public class PostController {

  @GetMapping("/") //Post search
  public ResponseList<PostResponse> postSearch(String text, Date dateFrom, Date dateTo, int offset, int itemPerPage) {
    return new ResponseList<>(new PostResponse());
  }

  @PostMapping("/") //Post creation
  public Response<PostResponse> postCreate(Date publishDate) { //@RequestBody "title": "string","post_text": "string","tags": ["tag1"]

    return new Response<>(new PostResponse());
  }

  @GetMapping("/{id}") //Getting post by ID
  public Response<PostResponse> postGetById(@PathVariable int id) {

    return new Response<>(new PostResponse());
  }

  @PutMapping("/{id}") //Editing a post by ID
  public Response<PostResponse> postEditById(@PathVariable int id, Date publishDate) { //@RequestBody "title": "string","post_text": "string","tags": ["tag1"]

    return new Response<>(new PostResponse());
  }

  @DeleteMapping("/{id}") //Delete post by ID
  public Response<PostResponse> postDeleteById(@PathVariable int id) {

    return new Response<>(new PostResponse());
  }

  @PutMapping("/{id}/recover") //Post recovery by ID
  public Response<PostResponse> postRecoverById(@PathVariable int id) {

    return new Response<>(new PostResponse());
  }

  @GetMapping("/{id}/comments") //Getting post comments
  public ResponseList<PostResponse>  postGetComments(@PathVariable int id, int offset, int itemPerPage) {
    return new ResponseList<>(new PostResponse());
  }

  @PostMapping("/{id}/comments") //Post comment
  public Response<PostResponse> postComments(@PathVariable int id) {//@RequestBody

    return new Response<>(new PostResponse());
  }

  @PutMapping("/{id}/comments/{comment_id}") //Editing a post comment
  public Response<PostResponse> postCommentsEdit(@PathVariable int id, @PathVariable int comment_id) {//@RequestBody
    return new Response<>(new PostResponse());
  }

  @DeleteMapping("/{id}/comments/{comment_id}") //Removing a post comment
  public Response<PostResponse> postCommentsDelete(@PathVariable int id, @PathVariable int comment_id) {
    return new Response<>(new PostResponse());
  }

  @PutMapping("/{id}/comments/{comment_id}/recover") //Comment recovery
  public Response<PostResponse> postCommentsRecovery(@PathVariable int id, @PathVariable int comment_id) {

    return new Response<>(new PostResponse());
  }

  @PostMapping("/{id}/report") //Post a complaint
  public Response postCommentComplain(@PathVariable int id) {

    return new Response("ok");
  }

  @PostMapping("/{id}/comments/{comment_id}/report") //File a complaint about post comments
  public Response postComplainToComment(@PathVariable int id, @PathVariable int comment_id) {
    return new Response("ok");
  }
}
